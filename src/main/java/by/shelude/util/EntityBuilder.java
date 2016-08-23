package by.shelude.util;

import by.shelude.entities.Timetable;
import by.shelude.entities.Transport;

import java.util.HashMap;

public class EntityBuilder {

    private long id;
    private String name;
    private HashMap<String, Timetable> directRoute;
    private HashMap<String, Timetable> reverseRoute;


    public static Transport buildTransport (String transportType, String routeNumber, String routeName,
                                     HashMap<String, Timetable> directRoute, HashMap<String, Timetable> reverseRoute) {
        Transport transport = Transport.valueOf(transportType.toUpperCase());
        transport.setRouteName(routeName);
        transport.setRouteNumber(routeNumber);
        transport.setDirectRoute(directRoute);
        transport.setReverseRoute(reverseRoute);
        return transport;
    }

}
