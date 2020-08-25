package com.karnaukh.currency.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karnaukh.currency.entity.Bank;
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

    public void saveBank(Bank bank) throws JsonProcessingException {
        MongoOperations mongoOperations = mongoTemplate;
        mongoOperations.insert(bank);
    }

    public List<Bank> getBankList(String city) {
        MongoOperations mongoOperations = mongoTemplate;
        Query query = new Query();
        Date date = new Date();
        date.toInstant().minusSeconds(60 * 60);

        query.addCriteria(Criteria.where("city").is(city));
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
}
