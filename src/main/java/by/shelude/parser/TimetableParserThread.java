package by.shelude.parser;

import by.shelude.constants.Regexp;
import by.shelude.constants.RequestParameters;
import by.shelude.constants.RequestParametersValues;
import by.shelude.entities.Timetable;
import by.shelude.entities.Transport;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Дмитрий on 26.08.2016.
 */
public class TimetableParserThread implements Runnable {

    private Thread thread;
    private static int counter;
    private Timetable directTimetable;
    private Timetable reverseTimetable;
    private String transportStopName;
    private HashMap<String, String> requeastParameters;

    public TimetableParserThread(Timetable directTimetable, Timetable reverseTimetable,
                                 HashMap<String, String> requeastParameters, String transportStopName) {
        counter++;
        String name = "timetableParser " + counter;
        this.directTimetable = directTimetable;
        this.reverseTimetable = reverseTimetable;
        this.transportStopName = transportStopName;
        this.requeastParameters = requeastParameters;
        thread = new Thread(this, name);
        this.thread.start();
    }


    @Override
    public void run() {
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
        String transportRouteType = requeastParameters.get(RequestParameters.ROUTE_TYPE);
        if (transportRouteType.equals(RequestParametersValues.ROUTE_TYPE_DIRECT)) {
            synchronized (directTimetable) {
                directTimetable.put(transportStopName, timetable);
            }
        } else {
            synchronized (reverseTimetable) {
                reverseTimetable.put(transportStopName, timetable);
            }
        }
    }
}
