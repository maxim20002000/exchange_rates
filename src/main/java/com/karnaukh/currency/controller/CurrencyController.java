package com.karnaukh.currency.controller;

import com.karnaukh.currency.service.CommonBankService;
import com.karnaukh.currency.service.dto.DtoResponse;
import com.karnaukh.currency.service.dto.dtoConverter.BankDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;


@RestController
@RequestMapping("/rates")
public class CurrencyController {

    @Autowired
    private CommonBankService commonBankService;

    @Autowired
    private BankDtoConverter bankDtoConverter;

    @GetMapping(value = "/updateCurrencyRates")
    public ResponseEntity<DtoResponse> updateCurrencyRates() throws JAXBException, IOException, InterruptedException {
        commonBankService.updateCurrencyRates();
        return ResponseEntity.ok().body(new DtoResponse("rates updated"));
    }
}
