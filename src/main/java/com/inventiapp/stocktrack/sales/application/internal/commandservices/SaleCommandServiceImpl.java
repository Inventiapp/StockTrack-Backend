package com.inventiapp.stocktrack.sales.application.internal.commandservices;

import com.inventiapp.stocktrack.sales.application.outboundservices.acl.ExternalInventoryService;
import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.sales.domain.model.commands.SaleDetailItem;
import com.inventiapp.stocktrack.sales.domain.services.SaleCommandService;
import com.inventiapp.stocktrack.sales.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleCommandServiceImpl implements SaleCommandService {

    private final SaleRepository saleRepository;
    private final ExternalInventoryService externalInventoryService;

    public SaleCommandServiceImpl(SaleRepository saleRepository, ExternalInventoryService externalInventoryService) {
        this.saleRepository = saleRepository;
        this.externalInventoryService = externalInventoryService;
    }

    @Override
    @Transactional
    public Long handle(CreateSaleCommand command) {
        for (SaleDetailItem item : command.details()) {
            if (!externalInventoryService.checkProductExists(item.productId())) {
                throw new IllegalArgumentException("El producto con ID " + item.productId() + " no existe.");
            }
        }

        Sale sale = new Sale(command);

        saleRepository.save(sale);

        externalInventoryService.decreaseStockForSale(sale);

        return sale.getId();
    }
}
