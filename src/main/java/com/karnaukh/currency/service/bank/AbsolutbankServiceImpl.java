package com.karnaukh.currency.service.bank;

import com.karnaukh.currency.bankAPI.absolutBank.BranchType;
import com.karnaukh.currency.bankAPI.absolutBank.BranchesType;
import com.karnaukh.currency.bankAPI.absolutBank.RateType;
import com.karnaukh.currency.dao.DaoRates;
import com.karnaukh.currency.entity.Bank;
import com.karnaukh.currency.entity.Currency;
import com.karnaukh.currency.entity.Department;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AbsolutbankServiceImpl implements BankService {

    @Autowired
    private Logger logger;

    @Autowired
    private DaoRates daoRates;

    @Autowired
    @Qualifier("absolutBankIdOffices")
    private Map<String, List<String>> idOffices;

    @Override
    public void updateCurrencyRate() {

        try {
            JAXBElement<BranchesType> unmarshalledObject = null;

            JAXBContext jaxbContext = JAXBContext.newInstance(com.karnaukh.currency.bankAPI.absolutBank.ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            URL url = new URL("https://absolutbank.by/exchange-rates.xml");
            unmarshalledObject = (JAXBElement<BranchesType>) unmarshaller.unmarshal(url);

            BranchesType branchType = unmarshalledObject.getValue();
            List<Department> departmentList = new ArrayList<>();
            Iterator<Map.Entry<String, List<String>>> iterator = idOffices.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, List<String>> entry = iterator.next();
                String city = entry.getKey();
                for (String idOffice : entry.getValue()) {

                    for (BranchType branch : branchType.getBranch()) {
                        if (branch.getId().equals(idOffice)) {
                            List<Currency> currencyList = new ArrayList<>();
                            List<RateType> newRateType = new ArrayList<>();
                            for (RateType rateType : branch.getRate()) {
                                if (rateType.getCurrency().length() < 4) {
                                    newRateType.add(rateType);
                                }
                            }
                            for (RateType rateType : newRateType) {
                                List<JAXBElement> listRate = new ArrayList<>((List) rateType.getContent());
                                Double sell = null;
                                Double buy = null;

                                listRate.remove(0);
                                listRate.remove(1);
                                listRate.remove(2);

                                for (JAXBElement rate : listRate) {
                                    if (rate.getName().toString().equals("sell")) {
                                        sell = Double.parseDouble(rate.getValue().toString());
                                    }
                                    if (rate.getName().toString().equals("buy")) {
                                        buy = Double.parseDouble(rate.getValue().toString());
                                    }
                                    if (buy != null && sell != null) {
                                        Currency currency = new Currency("BYN",
                                                rateType.getCurrency().toUpperCase(),
                                                buy, sell);
                                        currencyList.add(currency);
                                        buy = null;
                                        sell = null;
                                    }
                                }
                            }
                            departmentList.add(new Department(branch.getName(), new ArrayList<>(currencyList)));
                            currencyList.clear();
                            break;
                        }
                    }
                }
                logger.info("Absolutbank rates updated to DB");
                Bank absolutBank = new Bank("Absolutbank", city, departmentList);
                daoRates.saveBank(absolutBank);
                departmentList.clear();
            }
        } catch (JAXBException e) {
            System.out.println("--Error with absolutbank--");
            logger.error("JaxB exc", e);
        } catch (MalformedURLException e) {
            System.out.println("--Error with absolutbank--");
            logger.error("JaxB exc", e);
        } catch (NullPointerException e) {
            System.out.println("--Error with absolutbank--");
            logger.error("Null pointer", e);
        } catch (Exception e) {
            System.out.println("--Error with absolutbank--");
            logger.error("Other exc", e);
        }
    }
}
