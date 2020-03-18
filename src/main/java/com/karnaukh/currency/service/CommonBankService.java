package com.karnaukh.currency.service;

import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import com.karnaukh.currency.repository.BankRepository;

import java.util.List;

public class CommonBankService implements ICommonBankService {
	@Override
	public void printCurrencyRates() {
		List<Bank> bankList = BankRepository.getBankRepositoryInstance().getBankList();
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
