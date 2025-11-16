package com.inventiapp.stocktrack.sales.application.internal.queryservices;

import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.domain.model.queries.GetAllSalesQuery;
import com.inventiapp.stocktrack.sales.domain.model.queries.GetSaleByIdQuery;
import com.inventiapp.stocktrack.sales.domain.services.SaleQueryService;
import com.inventiapp.stocktrack.sales.infrastructure.persistence.jpa.repositories.SaleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleQueryServiceImpl implements SaleQueryService {

    private final SaleRepository saleRepository;

    public SaleQueryServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Optional<Sale> handle(GetSaleByIdQuery query) {
        return saleRepository.findById(query.saleId());
    }

    @Override
    public List<Sale> handle(GetAllSalesQuery query) {
        return saleRepository.findAll();
    }
}
