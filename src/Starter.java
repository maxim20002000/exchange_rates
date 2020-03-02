import controller.Controller;
import controller.ControllerImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


public class Starter {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new ControllerImpl();
        controller.getMTBankCurrency("Гомель");
        controller.getBelarusBankCurrency("Гродно");
        controller.getAlfabankCurrency();
        controller.getAbsolutbankCurrency();
        controller.getVTBbankCurrency();

    }
}
