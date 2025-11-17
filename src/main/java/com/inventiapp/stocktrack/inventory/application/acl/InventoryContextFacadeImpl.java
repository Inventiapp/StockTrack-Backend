package com.inventiapp.stocktrack.inventory.application.acl;

import com.inventiapp.stocktrack.inventory.domain.model.aggregates.Batch;
import com.inventiapp.stocktrack.inventory.domain.model.commands.UpdateBatchCommand;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetAllBatchesByProductIdQuery;
import com.inventiapp.stocktrack.inventory.domain.model.queries.GetProductByIdQuery;
import com.inventiapp.stocktrack.inventory.domain.services.BatchCommandService;
import com.inventiapp.stocktrack.inventory.domain.services.BatchQueryService;
import com.inventiapp.stocktrack.inventory.domain.services.ProductCommandService;
import com.inventiapp.stocktrack.inventory.domain.services.ProductQueryService;
import com.inventiapp.stocktrack.inventory.interfaces.acl.InventoryContextFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryContextFacadeImpl implements InventoryContextFacade {

    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;
    private final BatchQueryService batchQueryService;
    private final BatchCommandService batchCommandService;


    public InventoryContextFacadeImpl(ProductQueryService productQueryService, ProductCommandService productCommandService, BatchQueryService batchQueryService, BatchCommandService batchCommandService) {
        this.productQueryService = productQueryService;
        this.productCommandService = productCommandService;
        this.batchQueryService = batchQueryService;
        this.batchCommandService = batchCommandService;
    }


    @Override
    public Long getProductById(Long productId) {
        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var result = productQueryService.handle(getProductByIdQuery);
        return result.map(product -> product.getId()).orElse(null);

    }

    @Override
    public Boolean checkProductStockAvailability(Long productId, Integer requiredQuantity) {
        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var result = productQueryService.handle(getProductByIdQuery);
        return result.map(product -> product.getMinStock() >= requiredQuantity).orElse(false);
    }

    @Override
    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("productId inválido");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity inválida");
        }

        var getAllBatchesByProductIdQuery = new GetAllBatchesByProductIdQuery(productId);
        Optional<Batch> batches = batchQueryService.handle(getAllBatchesByProductIdQuery);

        // Orden FEFO: primero expiran antes
        List<Batch> sorted = batches.stream()
                .sorted(Comparator.comparing(Batch::getExpirationDate))
                .toList();

        int remaining = quantity;

        for (Batch batch : sorted) {
            if (remaining <= 0) break;
            int available = batch.getQuantity();
            if (available <= 0) continue;

            int toReduce = Math.min(available, remaining);
            int newQuantity = available - toReduce;

            var updateCommand = new UpdateBatchCommand(batch.getId(), newQuantity);
            var updated = batchCommandService.handle(updateCommand);
            if (updated == null || updated.isEmpty()) {
                throw new IllegalStateException("No se pudo actualizar el batch " + batch.getId());
            }

            remaining -= toReduce;
        }

        if (remaining > 0) {
            throw new IllegalStateException("Stock insuficiente. Faltan " + remaining + " unidades.");
        }
    }

    @Override
    public Double getProductUnitPrice(Long productId) {
        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var result = productQueryService.handle(getProductByIdQuery);
        return result.map(product -> product.getUnitPrice()).orElse(null);
    }

//    @Override
//    public boolean existsProductById(Long productId) {
//        var getProductByIdQuery = new GetProductByIdQuery(productId);
//        var result = productQueryService.handle(getProductByIdQuery);
//        return result.isPresent();
//    }
}
