package model;
import java.util.Comparator;


public class EventComparator implements Comparator<Event> {


    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getPostDate().compareTo(e2.getPostDate()) > 0)
            return -1;
        else
            return 1;
    }
}