import XMLHandler.MTBankXMLHandler;
import controller.Controller;
import controller.ControllerImpl;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;


public class Starter {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        Controller controller = new ControllerImpl();
        controller.getMTBankCurrency("Гомель");

    }
}
