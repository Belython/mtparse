package by.shelude.parser;

import by.shelude.constants.ParserParameters;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

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

    public Document getRootDocument(String url) {
        Document root = null;
        try {
            root = Jsoup.connect(url).userAgent(ParserParameters.USER_AGENT).timeout(30000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

}
