package by.shelude.entities;

import java.util.HashMap;
import java.util.List;

/**
 * Created by dkanarsky on 22.08.2016.
 */
public class Autobus {
    private long id;
    private String name;
    private HashMap<String, Timetable> directRoute;
    private HashMap<String, Timetable> reverseRoute;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
