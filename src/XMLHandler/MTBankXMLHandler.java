package XMLHandler;

import entity.Currency;
import entity.Department;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


public class MTBankXMLHandler extends DefaultHandler {
    private String code;
    private String codeTo;
    private String purchase;
    private String sale;
    private String lastElementName;
    private boolean isCurrentCity = false;
    private List<Currency> currencyList = new ArrayList<>();
    private List<Department> departmentList = new ArrayList<>();
    private String label;

    public void setCity(String city) {
        this.city = city;
    }

    private String city;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (qName.equals("department")) {
            if (attributes.getValue("city").equals(city)) {
                isCurrentCity = true;
                label=attributes.getValue("label");
                if (!currencyList.isEmpty()){
                    departmentList.add(new Department(label,currencyList));
                    currencyList.clear();
                }
            } else {
                isCurrentCity = false;
            }
        }
        lastElementName = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String information = new String(ch, start, length);

        information = information.replace("\n", "").trim();
        if (!information.isEmpty() && isCurrentCity == true) {
            if (lastElementName.equals("code"))
                code = information;
            if (lastElementName.equals("codeTo"))
                codeTo = information;
            if (lastElementName.equals("purchase"))
                purchase = information;
            if (lastElementName.equals("sale"))
                sale = information;
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (
                (code != null && !code.isEmpty())
                        && (codeTo != null && !codeTo.isEmpty())
                        && (purchase != null && !purchase.isEmpty())
                        && (sale != null && !sale.isEmpty())) {
            Currency currency = new Currency(code, codeTo, Double.parseDouble(purchase), Double.parseDouble(sale));
            currencyList.add(currency);
            code = "";
            codeTo = "";
            purchase = "";
            sale = "";
        }
    }


    public List<Department> getList(){
        return departmentList;
    }


}
