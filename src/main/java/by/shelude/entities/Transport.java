package by.shelude.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by dkanarsky on 23.08.2016.
 */
public enum Transport {
    AUTOBUS, TROLLEYBUS, TRAMWAY;

    private long id;
    private String routeNumber;
    private String routeName;
    private List<String> stopList;
    private HashMap<String, Timetable> directRoute = new HashMap<>();
    private HashMap<String, Timetable> reverseRoute = new HashMap<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public List<String> getStopList() {
        return stopList;
    }

    public void setStopList(List<String> stopList) {
        this.stopList = stopList;
    }

    public HashMap<String, Timetable> getDirectRoute() {
        return directRoute;
    }

    public void setDirectRoute(HashMap<String, Timetable> directRoute) {
        this.directRoute = directRoute;
    }

    public void addToDirectRoute(String dayOfWeek, Timetable timetable) {
        directRoute.put(dayOfWeek, timetable);
    }

    public HashMap<String, Timetable> getReverseRoute() {
        return reverseRoute;
    }

    public void setReverseRoute(HashMap<String, Timetable> reverseRoute) {
        this.reverseRoute = reverseRoute;
    }

    public void addToReverseRoute(String dayOfWeek, Timetable timetable) {
        reverseRoute.put(dayOfWeek, timetable);
    }

    @Override
    public String toString() {
        switch (this) {
            case AUTOBUS:
                return "Autobus";
            case TROLLEYBUS:
                return "Trolleybus";
            case TRAMWAY:
                return "Tramway";
            default:
                return null;
        }
    }

    public String getInfo() {
        String info = routeName + "\n" +
                routeNumber + "\n";
        Set<String> stops = directRoute.keySet();
        System.out.println("Маршрут: " + info);
        System.out.println("становки:");
        Set<String> s = directRoute.get("1").keySet();
        for (String s1: s) {
            System.out.println(s1);
        }
        System.out.println();
        return null;
    }
}
