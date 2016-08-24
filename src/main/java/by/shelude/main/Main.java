package by.shelude.main;

import by.shelude.constants.ParserParameters;
import by.shelude.parser.Parser;
import by.shelude.util.ConnectionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

/**
 * Created by Дмитрий on 22.08.2016.
 */
public class Main {

    public static void main(String[] args) {
        Parser parser = Parser.getInstance();
        parser.parse();


    }

}
