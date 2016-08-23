package by.shelude.parser;

import by.shelude.constants.Attributes;
import by.shelude.constants.Parameters;
import by.shelude.constants.ParserParameters;
import by.shelude.constants.Values;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private HashMap<String, String> currentParameters;
    private List<String> allBuses;

    private static Parser instance;

    private Parser() {
    }

    public static Parser getInstance () {
        if (instance == null) {
            instance = new Parser();
        }
        return instance;
    }

    public void parse() {
        List<String> transpotList = Values.TRANSPORT.getValues();
        currentParameters = new HashMap<>();
        for (String transport: transpotList) {
            currentParameters.put(Values.TRANSPORT.toString(), transport);
            currentParameters.put(Values.DAY.toString(), "1");
            Document mainDoc = visitGet(currentParameters);
            parsePage(mainDoc);
        }
    }

    private Document visitGet(HashMap<String, String> parameters) {
        StringBuilder url = new StringBuilder(ParserParameters.PAGE + "?");
        Set<String> parametersSet = parameters.keySet();
        for (String parameter : parametersSet) {
            url.append(parameter + "=" + parameters.get(parameter) + "&");
        }
        Document document = URLHandler.getInstance().getRootDocument(url.toString());
        return document;
    }

    public String getParameterValue(String targetURL, String parameter) {
        String regExp = parameter + "=\\w+\\W";
        String fullParameter = usePattern(targetURL, regExp);
        String valRegExp = "=\\w+";
        String value = usePattern(fullParameter, valRegExp).substring(1);
        return value;
    }

    private void parsePage(Document rootDocument) {
        currentParameters.put(Values.ALL_ROUTES.toString(), Values.ALL_ROUTES.getValue());
        Document document = visitGet(currentParameters);
        Elements tranports = document.getElementsByAttributeValueContaining("href", "?" + Values.ROUTE_NUMBER.toString());
        for (Element element : tranports) {
            String elementURL = element.attr(Attributes.HREF);
            String text = element.text();
            String regExp = " [а-яА-Я][\\w\\W]+";
            String routeName = usePattern(text, regExp).trim();
            String routeNumber = getParameterValue(elementURL, Values.ROUTE_NUMBER.toString());
//            currentParameters.put(Parameters.ROUTE_NAME, routeName);
            currentParameters.put(Parameters.ROUTE_NUMBER, routeNumber);
            Document tranportStop = visitGet(currentParameters);
            Elements stopElements = tranportStop.getElementsByAttributeValueContaining("href", "?" + Values.ROUTE_NUMBER.toString());
            for (Element stopElement : stopElements) {
                String elementURL1 = stopElement.attr(Attributes.HREF);
                String stopId = getParameterValue(elementURL1, Values.STOP_ID.toString());
                String routeType = getParameterValue(elementURL, Values.ROUTE_TYPE.toString());
                currentParameters.put(Values.STOP_ID.toString(), stopId);
                currentParameters.put(Values.ROUTE_TYPE.toString(), routeType);
                Document table = visitGet(currentParameters);

                String regExp2 = "<b>[\\d]{2}</b>\\s*:\\s*[[\\d]{2}]+";

            }
            Elements elements = tranportStop.getAllElements();

        }

    }

    public String usePattern(String targetString, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(targetString);
        System.out.println(matcher.find());
        String result = matcher.group();
        return result;
    }




}
