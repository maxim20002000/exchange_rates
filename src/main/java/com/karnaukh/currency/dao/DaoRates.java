package com.karnaukh.currency.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.karnaukh.currency.entity.Bank;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DaoRates {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveBanks(List<Bank> bankList) throws JsonProcessingException {

        MongoOperations mongoOperations = mongoTemplate;
        mongoOperations.insert(bankList, Bank.class);
    }

    public void saveBank(Bank bank) throws JsonProcessingException {
        MongoOperations mongoOperations = mongoTemplate;
        mongoOperations.insert(bank);
    }
}
