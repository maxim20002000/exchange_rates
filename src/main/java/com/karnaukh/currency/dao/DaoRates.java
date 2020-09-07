package com.karnaukh.currency.dao;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.BestAndWorstCurrency;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.service.utils.BankDateComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class DaoRates {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveBank(Bank bank) {
        MongoOperations mongoOperations = mongoTemplate;
        mongoOperations.insert(bank);
    }

    public List<Bank> getBankList(String city) {
        MongoOperations mongoOperations = mongoTemplate;
        Query query = new Query();
        Date date = new Date();
        date.toInstant().minusSeconds(60 * 60);

        Criteria criteria = new Criteria();
        criteria.orOperator(Criteria.where("city").is(city), Criteria.where("city").is("ALL"));
        query.addCriteria(criteria);
        query.addCriteria(Criteria.where("date").gt(Instant.now().minus(30, ChronoUnit.MINUTES)));
        List<Bank> bankList = mongoOperations.find(query, Bank.class);
        List<String> bankNames = new ArrayList<>();

        for (Bank bank : bankList) {
            boolean flag = true;
            for (String bankName : bankNames) {
                if (bankName.equals(bank.getNameBank())) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                bankNames.add(bank.getNameBank());
            }
        }
        List<Bank> newBankList = new ArrayList<>();
        for (String bankName : bankNames) {
            Bank sortedBank = bankList.stream().filter(bank -> bank.getNameBank().equals(bankName)).sorted(new BankDateComparator()).findAny().get();
            newBankList.add(sortedBank);
        }
        return newBankList;
    }

    public List<Currency> getCurrencyForBank(Bank bank, Instant dateFrom, Instant dateTo) {
        MongoOperations mongoOperations = mongoTemplate;
        Query query = new Query();
        query.addCriteria(Criteria.where("nameBank").is(bank.getNameBank()).and("date").gte(dateFrom).lte(dateTo));
        query.fields().include("nameCurrency").include("nameCurrencyTo");

        List<Bank> obj = mongoOperations.find(query, Bank.class);
        return null;
    }

    public List<BestAndWorstCurrency> getBestAndWorstRatesForThePeriod(Bank bank, Instant from, Instant until) {
        MongoOperations mongoOperations = mongoTemplate;
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.andOperator(Criteria.where("date").gte(from), Criteria.where("date").lte(until));
        query.addCriteria(Criteria.where("bankName").is(bank.getNameBank()));
        query.addCriteria(criteria);
        List<BestAndWorstCurrency> bestAndWorstCurrencyList = mongoOperations.find(query, BestAndWorstCurrency.class);
        return bestAndWorstCurrencyList;
    }

    public void setBestCurrencyForCurrentBank(Bank currentBank) {
        MongoOperations mongoOperations = mongoTemplate;
        Query query = new Query();
        query.addCriteria(Criteria.where("nameBank").is(currentBank.getNameBank()).and("date").
                gte(Instant.now().minus(30, ChronoUnit.MINUTES)).lte(Instant.now()));
        List<Bank> bankList = mongoOperations.find(query, Bank.class);

        Double bestUsdPurchase = 0D;
        Double bestUsdSale = 1000D;
        Double bestEurPurchase = 0D;
        Double bestEurSale = 1000D;
        Double bestRubPurchase = 0D;
        Double bestRubSale = 1000D;

        Double worstUsdPurchase = 1000D;
        Double worstUsdSale = 0D;
        Double worstEurPurchase = 1000D;
        Double worstEurSale = 0D;
        Double worstRubPurchase = 1000D;
        Double worstRubSale = 0D;

        for (Bank bank : bankList) {
            List<Department> departmentList = bank.getDepartmentList();
            for (Department department : departmentList) {
                List<Currency> currencyList = department.getCurrencyList();
                for (Currency currency : currencyList) {
                    if (currency.getNameCurrencyTo().equals("USD") && currency.getNameCurrency().equals("BYN")) {
                        if (currency.getPurchasePrice() > bestUsdPurchase) {
                            bestUsdPurchase = currency.getPurchasePrice();
                        }
                        if (currency.getSalePrice() < bestUsdSale) {
                            bestUsdSale = currency.getSalePrice();
                        }
                        if (currency.getPurchasePrice() < worstUsdPurchase) {
                            worstUsdPurchase = currency.getPurchasePrice();
                        }
                        if (currency.getSalePrice() > worstUsdSale) {
                            worstUsdSale = currency.getSalePrice();
                        }
                    }
                    if (currency.getNameCurrencyTo().equals("EUR") && currency.getNameCurrency().equals("BYN")) {
                        if (currency.getPurchasePrice() > bestEurPurchase) {
                            bestEurPurchase = currency.getPurchasePrice();
                        }
                        if (currency.getSalePrice() < bestEurSale) {
                            bestEurSale = currency.getSalePrice();
                        }
                        if (currency.getPurchasePrice() < worstEurPurchase) {
                            worstEurPurchase = currency.getPurchasePrice();
                        }
                        if (currency.getSalePrice() > worstEurSale) {
                            worstEurSale = currency.getSalePrice();
                        }
                    }
                    if (currency.getNameCurrencyTo().equals("RUB") && currency.getNameCurrency().equals("BYN")) {
                        if (currency.getPurchasePrice() > bestRubPurchase) {
                            bestRubPurchase = currency.getPurchasePrice();
                        }
                        if (currency.getSalePrice() < bestRubSale) {
                            bestRubSale = currency.getSalePrice();
                        }
                        if (currency.getPurchasePrice() < worstRubPurchase) {
                            worstRubPurchase = currency.getPurchasePrice();
                        }
                        if (currency.getSalePrice() > worstRubSale) {
                            worstRubSale = currency.getSalePrice();
                        }
                    }

                }
            }
        }
        BestAndWorstCurrency bestAndWorstCurrency = new BestAndWorstCurrency(currentBank.getNameBank(),
                bestUsdPurchase, bestUsdSale, bestEurPurchase, bestEurSale, bestRubPurchase, bestRubSale,
                worstUsdPurchase, worstUsdSale, worstEurPurchase, worstEurSale, worstRubPurchase, worstRubSale);

        mongoOperations.insert(bestAndWorstCurrency);
    }
}
