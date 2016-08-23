package by.shelude.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Дмитрий on 22.08.2016.
 */
public enum Values {
    TRANSPORT, DAY, ALL_ROUTES, ROUTE_NUMBER, STOP_ID, ROUTE_TYPE;

    private List<String> daysList = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
    private List<String> transportsList = Arrays.asList("Autobus", "Trolleybus", "Tramway");
    private String route = "1";

    public List<String> getValues() {
        switch (this) {
            case TRANSPORT: {
                return transportsList;
            }
            case DAY: {
                return daysList;
            }
            default: {
                return null;
            }
        }
    }

    public String getValue() {
        switch (this) {
            case ALL_ROUTES: {
                return "1";
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case TRANSPORT: {
                return "Transport";
            }
            case DAY: {
                return "day";
            }
            case ALL_ROUTES: {
                return "m";
            }
            case ROUTE_NUMBER: {
                return "RouteNum";
            }
            case STOP_ID:{
                return "StopID";
            }
            case ROUTE_TYPE: {
                return "RouteType";
            }
            default: {
                return null;
            }
        }
    }
}
