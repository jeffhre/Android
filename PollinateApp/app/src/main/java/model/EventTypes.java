package model;

import java.util.ArrayList;

/**
 * Created by jhrebena on 12/8/15.
 */
public class EventTypes {

    private ArrayList<String> mAllTypes;

    public EventTypes () {
        mAllTypes = new ArrayList<String>();
        mAllTypes.add("Art");
        mAllTypes.add("Charity");
        mAllTypes.add("Community Service");
        mAllTypes.add("Dance");
        mAllTypes.add("Dining");
        mAllTypes.add("Educational");
        mAllTypes.add("Film");
        mAllTypes.add("Karaoke");
        mAllTypes.add("Music");
        mAllTypes.add("Other");
        mAllTypes.add("Outdoors");
        mAllTypes.add("Peaceful Activism");
        mAllTypes.add("Social");
        mAllTypes.add("Sports");
        mAllTypes.add("Tours");
        mAllTypes.add("Trivia");

    }

    public ArrayList<String> getEventTypes() {
        return mAllTypes;
    }

    public String[] getEventTypeArray() {
        String[] arr = new String[mAllTypes.size()];
        int i = 0;
        for (String s : mAllTypes) {
            arr[i] = s;
            i++;
        }
        return arr;
    }
}
