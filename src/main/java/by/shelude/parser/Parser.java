package by.shelude.parser;

import by.shelude.constants.*;
import by.shelude.entities.Timetable;
import by.shelude.entities.Transport;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {


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
        for (String transportType: RequestParametersValues.TRANSPORT_LIST) {
            HashMap<String, String> requeastParameters = new HashMap<>();
            requeastParameters.put(RequestParameters.TRANSPORT, transportType);
            requeastParameters.put(RequestParameters.ALL_ROUTES, RequestParametersValues.ALL_ROUTES);
            requeastParameters.put(RequestParameters.DAY, "1");
            Transport currentTransport = Transport.valueOf(transportType.toUpperCase());
            parseTrasport(currentTransport, requeastParameters);
        }
    }

    private void parseTrasport(Transport currentTransport, HashMap<String, String> requeastParameters) {
        Document transportListDocument = URLHandler.getInstance().visitGet(requeastParameters);
        Elements tranportRouteElements = transportListDocument.getElementsByAttributeValueContaining(Attributes.HREF, "?" + RequestParameters.ROUTE_NUMBER);
        for (Element transportRouteElement: tranportRouteElements) {
            String transportRouteURL = transportRouteElement.attr(Attributes.HREF);
            String routeNumberAndName = transportRouteElement.text();
            String routeName = StringHandler.extractString(routeNumberAndName, Regexp.ROUTE_NAME).trim();
            String routeNumber = StringHandler.getParameterValue(transportRouteURL, RequestParameters.ROUTE_NUMBER);
            requeastParameters.put(RequestParameters.ROUTE_NUMBER, routeNumber);
            currentTransport.setRouteName(routeName);
            currentTransport.setRouteNumber(routeNumber);
            for (String dayOfWeek: RequestParametersValues.DAY_LIST) {
                requeastParameters.put(RequestParameters.DAY, dayOfWeek);
                List<Timetable> timetableList = parseStopList(requeastParameters);
                currentTransport.addToDirectRoute(dayOfWeek, timetableList.get(0));
                currentTransport.addToReverseRoute(dayOfWeek, timetableList.get(1));
                System.out.println(currentTransport.getRouteNumber() + " " + currentTransport.getRouteName() + " зделан частично");
            }
            System.out.println(currentTransport.getRouteNumber() + " " + currentTransport.getRouteName() + " зделан полностью");
        }
        requeastParameters.remove(RequestParameters.ROUTE_NUMBER);
    }

    private List<Timetable> parseStopList(HashMap<String, String> requeastParameters) {
        Document tranportStopsDocument = URLHandler.getInstance().visitGet(requeastParameters);
        Elements tramnsportStopsElements = tranportStopsDocument.getElementsByAttributeValueContaining("href", RequestParameters.STOP_ID);
        Timetable directTimetable = new Timetable();
        Timetable revetseTimetable = new Timetable();
        for (Element transportStopElement: tramnsportStopsElements) {
            String transportStopURL = transportStopElement.attr(Attributes.HREF);
            String transportStopId = StringHandler.getParameterValue(transportStopURL, RequestParameters.STOP_ID);
            String transportRouteType = StringHandler.getParameterValue(transportStopURL, RequestParameters.ROUTE_TYPE);
            String transportStopName = transportStopElement.text();
            requeastParameters.put(RequestParameters.STOP_ID, transportStopId);
            requeastParameters.put(RequestParameters.ROUTE_TYPE, transportRouteType);
            List<String> timetable = parseTimetable(requeastParameters);
            if (transportRouteType.equals(RequestParametersValues.ROUTE_TYPE_DIRECT)) {
                directTimetable.put(transportStopName, timetable);
            } else {
                revetseTimetable.put(transportStopName, timetable);
            }
        }
        List<Timetable> timetableList = Arrays.asList(directTimetable, revetseTimetable);
        requeastParameters.remove(RequestParameters.STOP_ID);
        requeastParameters.remove(RequestParameters.ROUTE_TYPE);
        return timetableList;
    }

    public static List<String> parseTimetable(HashMap<String, String> requeastParameters) {
        Document timetableDocument = URLHandler.getInstance().visitGet(requeastParameters);
        String numRegExp = Regexp.NUMBER;
        Elements tableElements = timetableDocument.getElementsMatchingText(Regexp.NUMBER).select("b:matchesOwn(" + Regexp.NUMBER + ")");
        List<String> timetable = new ArrayList<>();
        for (Element hourElement: tableElements) {
            String hour = hourElement.text();
            Node minuteNode = hourElement.nextSibling();
            String minutes = minuteNode.toString();
            Pattern pattern = Pattern.compile(numRegExp);
            Matcher matcher = pattern.matcher(minutes);
            while (matcher.find()) {
                String minute = matcher.group();
                timetable.add(hour + " : " + minute);
            }
            Element nextSibling = hourElement.nextElementSibling();
            while (nextSibling.tagName().equals("u")) {
                String minute = nextSibling.text();
                timetable.add(hour + " : " + minute);
                nextSibling = nextSibling.nextElementSibling();
            }
        }
        return timetable;
    }







}
