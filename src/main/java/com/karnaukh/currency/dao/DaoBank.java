package com.karnaukh.currency.dao;

import com.karnaukh.currency.entity.Bank;
import org.springframework.stereotype.Repository;

@Repository
public class DaoBank extends AbstractDao<Bank,Integer> implements IDaoBank {
}
