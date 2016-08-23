package by.shelude.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Дмитрий on 23.08.2016.
 */
public class Timetable {

    private HashMap<String, List<String>> table = new HashMap<>();

    public HashMap<String, List<String>> getTable() {
        return table;
    }

    public void setTable(HashMap<String, List<String>> table) {
        this.table = table;
    }
}
