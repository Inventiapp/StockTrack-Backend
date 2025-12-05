package com.inventiapp.stocktrack.reports.application.internal;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Product;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllProductsQuery;
import com.inventiapp.stocktrack.inventory.domain.services.BatchQueryService;
import com.inventiapp.stocktrack.inventory.domain.services.ProductQueryService;
import com.inventiapp.stocktrack.reports.application.DashboardService;
import com.inventiapp.stocktrack.reports.interfaces.rest.resources.*;
import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.domain.model.queries.GetAllSalesQuery;
import com.inventiapp.stocktrack.sales.domain.services.SaleQueryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of DashboardService that aggregates data from multiple bounded contexts.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    private final ProductQueryService productQueryService;
    private final BatchQueryService batchQueryService;
    private final SaleQueryService saleQueryService;

    public DashboardServiceImpl(
            ProductQueryService productQueryService,
            BatchQueryService batchQueryService,
            SaleQueryService saleQueryService) {
        this.productQueryService = productQueryService;
        this.batchQueryService = batchQueryService;
        this.saleQueryService = saleQueryService;
    }

    @Override
    public DashboardResource getDashboardData() {
        List<Product> products = productQueryService.handle(new GetAllProductsQuery());
        List<Batch> batches = batchQueryService.handle(new GetAllBatchesQuery());
        List<Sale> sales = saleQueryService.handle(new GetAllSalesQuery());

        DashboardStatsResource stats = calculateStats(products, batches, sales);
        List<MonthlyIncomeResource> monthlyIncome = calculateMonthlyIncome(sales);
        List<ProductSalesResource> productSales = calculateProductSales(sales, products);
        List<NotificationResource> notifications = generateNotifications(products, batches);

        return new DashboardResource(stats, monthlyIncome, productSales, notifications);
    }

    private DashboardStatsResource calculateStats(List<Product> products, List<Batch> batches, List<Sale> sales) {
        int productsInInventory = (int) products.stream().filter(Product::getIsActive).count();
        
        // Calculate stock per product
        Map<Long, Integer> stockByProduct = batches.stream()
                .collect(Collectors.groupingBy(
                        Batch::getProductId,
                        Collectors.summingInt(Batch::getQuantity)
                ));

        // Products with alerts (stock < minStock)
        int productsWithAlerts = (int) products.stream()
                .filter(Product::getIsActive)
                .filter(p -> {
                    int currentStock = stockByProduct.getOrDefault(p.getId(), 0);
                    return currentStock < p.getMinStock();
                })
                .count();

        // Sales and income this month
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();
        int currentYear = now.getYear();

        List<Sale> salesThisMonth = sales.stream()
                .filter(s -> {
                    LocalDate saleDate = s.getCreatedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return saleDate.getMonthValue() == currentMonth && saleDate.getYear() == currentYear;
                })
                .toList();

        double monthlyIncome = salesThisMonth.stream()
                .mapToDouble(Sale::getTotalAmount)
                .sum();

        return new DashboardStatsResource(
                productsInInventory,
                monthlyIncome,
                salesThisMonth.size(),
                productsWithAlerts
        );
    }

    private List<MonthlyIncomeResource> calculateMonthlyIncome(List<Sale> sales) {
        LocalDate now = LocalDate.now();
        int currentYear = now.getYear();

        // Group sales by month for the current year
        Map<Integer, Double> incomeByMonth = sales.stream()
                .filter(s -> {
                    LocalDate saleDate = s.getCreatedAt().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return saleDate.getYear() == currentYear;
                })
                .collect(Collectors.groupingBy(
                        s -> s.getCreatedAt().toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                                .getMonthValue(),
                        Collectors.summingDouble(Sale::getTotalAmount)
                ));

        // Generate all 12 months
        List<MonthlyIncomeResource> result = new ArrayList<>();
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                          "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        
        for (int i = 1; i <= 12; i++) {
            result.add(new MonthlyIncomeResource(
                    months[i - 1],
                    incomeByMonth.getOrDefault(i, 0.0)
            ));
        }

        return result;
    }

    private List<ProductSalesResource> calculateProductSales(List<Sale> sales, List<Product> products) {
        // Calculate total revenue per product from sale details
        Map<Long, Double> revenueByProduct = new HashMap<>();
        double totalRevenue = 0;

        for (Sale sale : sales) {
            for (var detail : sale.getDetails()) {
                Long productId = detail.getProductId().id();
                double detailTotal = detail.getTotalPrice();
                revenueByProduct.merge(productId, detailTotal, Double::sum);
                totalRevenue += detailTotal;
            }
        }

        if (totalRevenue == 0) {
            return Collections.emptyList();
        }

        // Create product name map
        Map<Long, String> productNames = products.stream()
                .collect(Collectors.toMap(Product::getId, Product::getName));

        // Calculate percentages and sort by percentage descending
        final double finalTotalRevenue = totalRevenue;
        return revenueByProduct.entrySet().stream()
                .map(entry -> new ProductSalesResource(
                        productNames.getOrDefault(entry.getKey(), "Unknown Product"),
                        Math.round((entry.getValue() / finalTotalRevenue) * 1000.0) / 10.0 // Round to 1 decimal
                ))
                .sorted((a, b) -> Double.compare(b.percentage(), a.percentage()))
                .limit(10) // Top 10 products
                .toList();
    }

    private List<NotificationResource> generateNotifications(List<Product> products, List<Batch> batches) {
        List<NotificationResource> notifications = new ArrayList<>();

        // Create product map for quick lookup
        Map<Long, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        // Calculate stock per product
        Map<Long, Integer> stockByProduct = batches.stream()
                .collect(Collectors.groupingBy(
                        Batch::getProductId,
                        Collectors.summingInt(Batch::getQuantity)
                ));

        // Low stock notifications
        products.stream()
                .filter(Product::getIsActive)
                .filter(p -> {
                    int currentStock = stockByProduct.getOrDefault(p.getId(), 0);
                    return currentStock < p.getMinStock();
                })
                .limit(5)
                .forEach(p -> {
                    int currentStock = stockByProduct.getOrDefault(p.getId(), 0);
                    notifications.add(new NotificationResource(
                            "low-stock-" + p.getId(),
                            "alert",
                            "lowStock",
                            "",
                            Map.of("product", p.getName(), "quantity", currentStock)
                    ));
                });

        // Expiring products notifications (next 30 days)
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        batches.stream()
                .filter(b -> b.getExpirationDate() != null)
                .filter(b -> {
                    LocalDate expDate = b.getExpirationDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return expDate.isBefore(thirtyDaysFromNow) && expDate.isAfter(LocalDate.now());
                })
                .limit(5)
                .forEach(b -> {
                    LocalDate expDate = b.getExpirationDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    Product product = productMap.get(b.getProductId());
                    String productName = product != null ? product.getName() : "Unknown Product";
                    notifications.add(new NotificationResource(
                            "expiring-" + b.getId(),
                            "warning",
                            "expiringProduct",
                            "",
                            Map.of("product", productName, "date", expDate.toString())
                    ));
                });

        return notifications;
    }
}

