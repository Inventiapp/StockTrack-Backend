package com.inventiapp.stocktrack.sales.domain.services;

import com.inventiapp.stocktrack.sales.domain.model.aggregates.Sale;
import com.inventiapp.stocktrack.sales.domain.model.queries.GetAllSalesQuery;
import com.inventiapp.stocktrack.sales.domain.model.queries.GetSaleByIdQuery;

import java.util.List;
import java.util.Optional;

public interface SaleQueryService {

    Optional<Sale> handle(GetSaleByIdQuery getSaleByIdQuery);

    List<Sale> handle(GetAllSalesQuery getAllSalesQuery);
}
