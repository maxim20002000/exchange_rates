package XMLHandler;

import entity.Currency;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class VTBBankXMLHandler extends DefaultHandler {
	private String buy = "";
	private String sell = "";
	private String codeTo = "";
	private String lastElementName;
	private boolean flag = false;
	private List<Currency> currencyList = new ArrayList<Currency>();


	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if (qName.equals("card")) {
			flag = true;
		}
		lastElementName = qName;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		String information = new String(ch, start, length);

		information = information.replace("\n", "").trim();
		if (!information.isEmpty()) {
			if (lastElementName.equals("code"))
				codeTo = information;
			if (lastElementName.equals("buy"))
				buy = information;
			if (lastElementName.equals("sell"))
				sell = information;
		}


	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if (!flag
				&& (buy != null && !buy.isEmpty())
				&& (codeTo != null && !codeTo.isEmpty())
				&& (sell != null && !sell.isEmpty())) {
			Currency currency = new Currency("BYN", codeTo.toUpperCase(),
					Double.parseDouble(buy), Double.parseDouble(sell));
			currencyList.add(currency);
			buy = "";
			codeTo = "";
			sell = "";
		}

	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}
}
