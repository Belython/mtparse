package by.shelude.main;

import by.shelude.constants.Values;
import by.shelude.parser.Parser;

/**
 * Created by Дмитрий on 22.08.2016.
 */
public class Main {

    public static void main(String[] args) {
        Parser parser = Parser.getInstance();
        parser.parse();
    }

}
