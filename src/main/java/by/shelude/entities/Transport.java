package by.shelude.entities;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dkanarsky on 23.08.2016.
 */
public enum Transport {
    AUTOBUS, TROLLEYBUS, TRAMWAY;

    private long id;
    private String routeNumber;
    private String routeName;
    private List<String> stopList;
    private HashMap<String, Timetable> directRoute;
    private HashMap<String, Timetable> reverseRoute;

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

    public HashMap<String, Timetable> getReverseRoute() {
        return reverseRoute;
    }

    public void setReverseRoute(HashMap<String, Timetable> reverseRoute) {
        this.reverseRoute = reverseRoute;
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
}
