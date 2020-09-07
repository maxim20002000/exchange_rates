package com.karnaukh.currency.service.dto.dtoConverter;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.service.dto.DtoBank;
import org.springframework.stereotype.Service;

@Service
public class BankDtoConverter extends AbstractDtoConverter<Bank, DtoBank> {

    public BankDtoConverter() {
        super(Bank.class, DtoBank.class);
    }
}
