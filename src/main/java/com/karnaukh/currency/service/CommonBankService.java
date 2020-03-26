package com.karnaukh.currency.service;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonBankService implements ICommonBankService {

	@Autowired
	private BankRepository bankRepository;

	@Override
	public void printCurrencyRates() {
		List<Bank> bankList = bankRepository.getBankList();
		for (Bank bank : bankList) {
			System.out.println(bank.toString());
			List<Department> departmentList = bank.getDepartmentList();
			for (Department department : departmentList) {
				System.out.println(department.toString());
				List<Currency> currencyList = department.getCurrencyList();
				for (Currency currency : currencyList) {
					System.out.println(currency.toString());
				}
				System.out.println("");
			}
		}
	}
}
