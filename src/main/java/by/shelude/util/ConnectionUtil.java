package by.shelude.util;

import by.shelude.constants.ParserParameters;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class ConnectionUtil {

    public static Document get(String url) {
        Connection connection = Jsoup.connect(url).userAgent(ParserParameters.USER_AGENT).timeout(60000);
        Document document = null;
        try {
            document = connection.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }


    
}
