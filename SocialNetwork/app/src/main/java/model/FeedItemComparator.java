package model;
import java.util.Comparator;


public class FeedItemComparator implements Comparator<FeedItem> {


    @Override
    public int compare(FeedItem f1, FeedItem f2) {
        if (f1.getPostDate().compareTo(f2.getPostDate()) > 0)
            return -1;
        else
            return 1;
    }
}