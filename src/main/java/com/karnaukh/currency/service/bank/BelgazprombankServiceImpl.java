package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.bankAPI.belgazpromBank.BranchesType;
import com.karnaukh.currency.bankAPI.belgazpromBank.RangeType;
import com.karnaukh.currency.dao.DaoRates;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class BelgazprombankServiceImpl implements BankService {

    @Autowired
    private DaoRates daoRates;

    @Autowired
    private Logger logger;

    @Override
    public void updateCurrencyRate() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.belgazpromBank.ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            URL url = new URL("https://belgazprombank.by/upload/courses.xml");
            JAXBElement<BranchesType> unmarshalledObject =
                    (JAXBElement<BranchesType>) unmarshaller.unmarshal(url);
            BranchesType branchesType = unmarshalledObject.getValue();
            List<Department> departmentList = new ArrayList<>();
            //for (com.karnaukh.currency.bankAPI.belgazpromBank.BranchType branch : branchesType.getBranch().) {
            com.karnaukh.currency.bankAPI.belgazpromBank.BranchType branch = branchesType.getBranch().get(0);
            List<com.karnaukh.currency.bankAPI.belgazpromBank.RateType> rateList = branch.getRate();
            List<Currency> currencyList = new ArrayList<>();
            for (com.karnaukh.currency.bankAPI.belgazpromBank.RateType rate : rateList) {
                List<JAXBElement> jaxbElements = new ArrayList<>((List) rate.getContent());
                if (jaxbElements.size() == 2) {
                    Currency currency = new Currency(rate.getCurrency().toUpperCase(), "BYN",
                            Double.parseDouble((String) jaxbElements.get(0).getValue()),
                            Double.parseDouble((String) jaxbElements.get(1).getValue()));
                    currencyList.add(currency);

                } else {
                    if (jaxbElements.size() > 1) {
                        JAXBElement jaxbElement = jaxbElements.get(0);
                        RangeType rangeType = (RangeType) jaxbElement.getValue();
                        Currency currency = new Currency("BYN",
                                rate.getCurrency().toUpperCase(),
                                Double.parseDouble(rangeType.getBuy()),
                                Double.parseDouble(rangeType.getSell()));
                        currencyList.add(currency);
                    }
                }
            }
            departmentList.add(new Department("ALL departments", new ArrayList<>(currencyList)));
            currencyList.clear();

            logger.info("Belgazprombank rates updated in DB");
            Bank belgazpromBank = new Bank("Belgazprombank","ALL", departmentList);
            daoRates.saveBank(belgazpromBank);
        } catch (JAXBException e) {
            System.out.println("--Error with Belgazprombank--");
            logger.error("Jaxb exc", e);
        } catch (MalformedURLException e) {
            System.out.println("--Error with Belgazprombank (Check URL)--");
            logger.error("Malformed URL exc", e);
        } catch (NumberFormatException e) {
            System.out.println("--Error with Belgazprombank--");
            logger.error("Number format exc", e);
        } catch (Exception e) {
            System.out.println("--Error with Belgazprombank--");
            logger.error("Other exc", e);
        }

    }
}
