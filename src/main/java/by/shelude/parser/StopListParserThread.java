package by.shelude.parser;

import by.shelude.constants.Attributes;
import by.shelude.constants.RequestParameters;
import by.shelude.constants.RequestParametersValues;
import by.shelude.entities.Timetable;
import by.shelude.entities.Transport;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Дмитрий on 26.08.2016.
 */
public class StopListParserThread implements Runnable {

    private Thread thread;
    private static int counter;
    private  Transport currentTransport;
    private HashMap<String, String> requeastParameters;

    public StopListParserThread(Transport currentTransport, HashMap<String, String> requeastParameters) {
        counter++;
        String name = "stopListParser " + counter;
        this.currentTransport = currentTransport;
        this.requeastParameters = (HashMap<String, String>) requeastParameters.clone();
        thread = new Thread(this, name);
        this.thread.start();
    }

    @Override
    public void run() {
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
            List<String> timetable = Parser.parseTimetable(requeastParameters);
            if (transportRouteType.equals(RequestParametersValues.ROUTE_TYPE_DIRECT)) {
                directTimetable.put(transportStopName, timetable);
            } else {
                revetseTimetable.put(transportStopName, timetable);
            }
        }
        String dayOfWeek = requeastParameters.get(RequestParameters.DAY);
        synchronized (currentTransport) {
            currentTransport.addToDirectRoute(dayOfWeek, directTimetable);
            currentTransport.addToReverseRoute(dayOfWeek, revetseTimetable);
        }
    }
}
