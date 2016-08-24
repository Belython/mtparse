package by.shelude.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Дмитрий on 23.08.2016.
 */
public class Timetable {

    private HashMap<String, List<String>> timeTable = new HashMap<>();

    public HashMap<String, List<String>> getTimeTable() {
        return timeTable;
    }

    public void setTimeTable(HashMap<String, List<String>> timeTable) {
        this.timeTable = timeTable;
    }

    public void putInTimeTable(String transportStop, List<String> time) {
        timeTable.put(transportStop, time);
    }


    public void addTableToDay(List<String> table, String dayOfWeek) {
        timeTable.put(dayOfWeek, table);
    }
}
