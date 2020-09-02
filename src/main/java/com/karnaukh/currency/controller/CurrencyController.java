package com.karnaukh.currency.controller;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.service.CommonBankService;
import com.karnaukh.currency.service.dto.DtoBank;
import com.karnaukh.currency.service.dto.DtoResponse;
import com.karnaukh.currency.service.dto.dtoConverter.BankDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/rates")
public class CurrencyController {

    @Autowired
    private CommonBankService commonBankService;

    @Autowired
    private BankDtoConverter bankDtoConverter;

    @GetMapping(value = "/updateCurrencyRates")
    public ResponseEntity<DtoResponse> updateCurrencyRates() throws JAXBException, IOException {
        commonBankService.updateCurrencyRates();
        return ResponseEntity.ok().body(new DtoResponse("rates updated"));
    }

    @GetMapping(value = "/getCurrencyRates/{city}")
    public ResponseEntity<List<DtoBank>> getCurrencyRatesForCity(@PathVariable String city) throws JAXBException, IOException, InterruptedException {
        List<Bank> bankList = commonBankService.getCurrencyRates(city);
        return ResponseEntity.ok().body(bankDtoConverter.convertListToDto(bankList));
    }

    @GetMapping(value = "/getCurrencyForBank/{bankName}")
    public ResponseEntity<List<DtoBank>> getCurrencyForBank(@PathVariable String bankName) throws JAXBException, IOException, InterruptedException {
        Bank bank = new Bank();
        bank.setNameBank(bankName);
        List<Bank> bankList = commonBankService.getCurrencyForBank(bank);
        return ResponseEntity.ok().body(bankDtoConverter.convertListToDto(bankList));
    }

    @GetMapping(value = "/setBestCurrencyForCurrentBank/{bankName}")
    public ResponseEntity<DtoResponse> getBestCurrencyForCurrentBank(@PathVariable String bankName) throws JAXBException, IOException, InterruptedException {
        Bank bank = new Bank();
        bank.setNameBank(bankName);
        commonBankService.setBestCurrencyForCurrentBank(bank);
        return ResponseEntity.ok().body(new DtoResponse("ff"));
    }
}
