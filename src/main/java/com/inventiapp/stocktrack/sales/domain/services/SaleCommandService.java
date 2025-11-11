package com.inventiapp.stocktrack.sales.domain.services;

import com.inventiapp.stocktrack.sales.domain.model.commands.CreateSaleCommand;

public interface SaleCommandService {
    Long handle(CreateSaleCommand command);
}
