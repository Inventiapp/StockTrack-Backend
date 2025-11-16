package com.inventiapp.stocktrack.sales.application.internal.commandservices;

import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;
import com.inventiapp.stocktrack.sales.domain.services.SaleCommandService;
import com.inventiapp.stocktrack.sales.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleCommandServiceImpl implements SaleCommandService {

    private final SaleRepository saleRepository;

    public SaleCommandServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Long handle(CreateSaleCommand command) {
        var sale = new Sale(command);
        saleRepository.save(sale);
        return sale.getId();
    }
}
