package com.karnaukh.currency.service.dto.dtoConverter;

import com.karnaukh.currency.entity.BestAndWorstCurrency;
import com.karnaukh.currency.service.dto.DtoBestAndWorstCurrency;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class BestAndWorstCurrencyDtoConverter extends AbstractDtoConverter<BestAndWorstCurrency, DtoBestAndWorstCurrency> {
    public BestAndWorstCurrencyDtoConverter() {
        super(BestAndWorstCurrency.class, DtoBestAndWorstCurrency.class);
    }

    @Override
    public DtoBestAndWorstCurrency convertToDto(BestAndWorstCurrency entity) {
        DtoBestAndWorstCurrency dtoBestAndWorstCurrency = super.convertToDto(entity);
        dtoBestAndWorstCurrency.setDate(Date.from(entity.getDate()));
        return dtoBestAndWorstCurrency;
    }
}
