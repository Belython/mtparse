package by.shelude.parser;

import by.shelude.constants.ParserParameters;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class URLHandler {

    private static URLHandler instance;

    private URLHandler() {
    }

    public static URLHandler getInstance () {
        if (instance == null) {
            instance = new URLHandler();
        }
        return instance;
    }

    private Document getRootDocument(String url) {
        Document root = null;
        try {
            root = Jsoup.connect(url).userAgent(ParserParameters.USER_AGENT).timeout(100000).get();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e1) {
           root = getRootDocument(url);
        }
        return root;
    }

    public Document visitGet(HashMap<String, String> parameters) {
        StringBuilder url = new StringBuilder(ParserParameters.PAGE + "?");
        Set<String> parametersSet = parameters.keySet();
        for (String parameter : parametersSet) {
            url.append(parameter + "=" + parameters.get(parameter) + "&");
        }
        Document document = getRootDocument(url.toString());
        return document;
    }


}
