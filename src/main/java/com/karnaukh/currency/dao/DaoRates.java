package com.karnaukh.currency.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.karnaukh.currency.entity.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DaoRates {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveBank(Bank bank) throws JsonProcessingException {
        MongoOperations mongoOperations = mongoTemplate;
        mongoOperations.insert(bank);
    }
}
