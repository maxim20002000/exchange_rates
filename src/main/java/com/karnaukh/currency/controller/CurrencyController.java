package com.karnaukh.currency.controller;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.BestAndWorstCurrency;
import com.karnaukh.currency.service.CommonBankService;
import com.karnaukh.currency.service.dto.DtoBank;
import com.karnaukh.currency.service.dto.DtoBestAndWorstCurrency;
import com.karnaukh.currency.service.dto.DtoResponse;
import com.karnaukh.currency.service.dto.dtoConverter.BankDtoConverter;
import com.karnaukh.currency.service.dto.dtoConverter.BestAndWorstCurrencyDtoConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


@RestController
@RequestMapping("/rates")
public class CurrencyController {

    @Autowired
    private CommonBankService commonBankService;

    @Autowired
    private BankDtoConverter bankDtoConverter;

    @Autowired
    private BestAndWorstCurrencyDtoConverter bestAndWorstCurrencyDtoConverter;

    private static Logger logger = LogManager.getLogger(CurrencyController.class);

    @GetMapping(value = "/updateCurrencyRates")
    public ResponseEntity<DtoResponse> updateCurrencyRates() throws JAXBException, IOException {
        commonBankService.updateCurrencyRates();
        logger.info("Rates was updated by URL request");
        return ResponseEntity.ok().body(new DtoResponse("Rates was updated"));
    }

    @GetMapping(value = "/getCurrencyRatesForCity/{city}")
    public ResponseEntity<List<DtoBank>> getCurrencyRatesForCity(@PathVariable String city) {
        List<Bank> bankList = commonBankService.getCurrencyRates(city);
        return ResponseEntity.ok().body(bankDtoConverter.convertListToDto(bankList));
    }

    @GetMapping(value = "/getBestAndWorstRatesPerWeekForBank/{bankName}")
    public ResponseEntity<List<DtoBestAndWorstCurrency>> getBestAndWorstRatesPerWeekForBank(@PathVariable String bankName) {
        Bank bank = new Bank();
        bank.setNameBank(bankName);
        TreeSet<BestAndWorstCurrency> bestAndWorstCurrencyTreeSet = commonBankService.getBestAndWorstRatesPerWeekForBank(bank);
        return ResponseEntity.ok().body(bestAndWorstCurrencyDtoConverter.convertListToDto(new ArrayList<>(bestAndWorstCurrencyTreeSet)));
    }
}
