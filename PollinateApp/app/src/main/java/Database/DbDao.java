package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import model.Event;
import model.User;

/**
 * Created by jhrebena on 11/10/15.
 */
public class DbDao {

    private static final String jl ="JEFF_DAO";

    private static DbDao SINGLETON;
    private final SQLiteDatabase mDB;

    private DbDao(Context c) {
        mDB = new DbHelper(c).getWritableDatabase();
    }


    public static DbDao get(Context c) {
        if(SINGLETON == null) {
            SINGLETON = new DbDao(c);
        }
        return SINGLETON;
    }


    public void addUser(User u) {
        mDB.insert(
                DbSchema.UserTable.NAME,
                null,
                getUserContentValues(u)
        );
    }

    public void addEvent(Event f) {
        mDB.insert(
                DbSchema.EventTable.NAME,
                null,
                getEventContentValues(f)
        );
    }

    public void addFavorites(String email) {

        ContentValues values = new ContentValues();
        values.put(DbSchema.Favorites.Cols.EMAIL, email);
        values.put(DbSchema.Favorites.Cols.FAVORITE_USERS, email + "");
        values.put(DbSchema.Favorites.Cols.FAVORITE_EVENTS, "");

        mDB.insert(
                DbSchema.Favorites.NAME,
                null,
                values
        );
    }

    public void addFavoriteUser(String email) {

        ContentValues values = new ContentValues();
        values.put(DbSchema.Favorites.Cols.EMAIL, email);
        values.put(DbSchema.Favorites.Cols.FAVORITE_USERS, email + "");

        mDB.insert(
                DbSchema.Favorites.NAME,
                null,
                values
        );
    }




    //get a user based on email and password (used for login)
    public User getUserLogin(String email, String password) {
        Cursor cursor = mDB.query(
                DbSchema.UserTable.NAME,
                null,
                "email=? AND password=?",
                new String[]{email, password},
                null,
                null,
                null
        );
        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);
        User u = null;
        if (wrapper.getCount() != 0) {
            wrapper.moveToFirst();
            u = wrapper.getUser();
        }
        wrapper.close();

        return u;
    }

    //get a particular user based on email
    public User getUser(String email) {
        Cursor cursor = mDB.query(
                DbSchema.UserTable.NAME,
                null,
                "email=?",
                new String[]{email},
                null,
                null,
                null
        );
        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);
        User u = null;
        if (wrapper.getCount() != 0) {
            wrapper.moveToFirst();
            u = wrapper.getUser();
        }
        wrapper.close();

        return u;
    }

    public Event getEvent(String name) {
        Cursor cursor = mDB.query(
                DbSchema.EventTable.NAME,
                null,
                "title=?",
                new String[]{name},
                null,
                null,
                null
        );
        EventCursorWrapper wrapper = new EventCursorWrapper(cursor);
        Event e = null;
        if (wrapper.getCount() != 0) {
            wrapper.moveToFirst();
            e = wrapper.getEvent();
        }
        wrapper.close();

        return e;
    }



 //   return arraylist of all users
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        UserCursorWrapper cursor = queryUsers(null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            User u = cursor.getUser();
            users.add(u);
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }

    //   return arraylist of all posts
    public ArrayList<Event> getAllEvents() {
        ArrayList<Event> Events = new ArrayList<Event>();
        EventCursorWrapper cursor = queryEvents(null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Event f = cursor.getEvent();
            Events.add(f);
            cursor.moveToNext();
        }
        cursor.close();
        return Events;
    }

    public void updateUser(User u) {
        String email = u.getEmail();
        ContentValues values = getUserContentValues(u);
        mDB.update(
                DbSchema.UserTable.NAME,
                values,
                DbSchema.UserTable.Cols.EMAIL + "=?",
                new String[]{email}
        );
    }

    public void updateFavoriteEvents(String email, ArrayList<String> favorite) {
        String parseString = "";
        for (String s : favorite) {
            parseString += s;
            parseString += ",";
        }
        Log.d(jl, parseString);

        ContentValues values = new ContentValues();
        values.put(DbSchema.Favorites.Cols.EMAIL, email);
        values.put(DbSchema.Favorites.Cols.FAVORITE_EVENTS, parseString);

        mDB.update(
                DbSchema.Favorites.NAME,
                values,
                DbSchema.Favorites.Cols.EMAIL + "=?",
                new String[]{email}
        );
    }

    public void updateFavoriteUsers(String email, ArrayList<String> favorite) {
        String parseString = "";
        for (String s : favorite) {
            parseString += s;
            parseString += ",";
        }
        Log.d(jl, parseString);

        ContentValues values = new ContentValues();
        values.put(DbSchema.Favorites.Cols.EMAIL, email);
        values.put(DbSchema.Favorites.Cols.FAVORITE_USERS, parseString);

        mDB.update(
                DbSchema.Favorites.NAME,
                values,
                DbSchema.Favorites.Cols.EMAIL + "=?",
                new String[]{email}
        );
    }

    public ArrayList<String> getFavoriteEvents(String email) {
        Cursor cursor = mDB.query(
                DbSchema.Favorites.NAME,
                null,
                "email=?",
                new String[]{email},
                null,
                null,
                null
        );
        FavoriteCursorWrapper wrapper = new FavoriteCursorWrapper(cursor);
        ArrayList<String> favList = new ArrayList<String>();
        Log.d("JEFF_WRAPPER_COUNT", Integer.toString(wrapper.getCount()));
        if (wrapper.getCount() != 0) {
            wrapper.moveToFirst();
            favList = wrapper.getFavoriteEvents();
        }
        else {
            addFavorites(email);
        }
        wrapper.close();

        return favList;
    }

    public ArrayList<String> getFavoriteUsers(String email) {
        Cursor cursor = mDB.query(
                DbSchema.Favorites.NAME,
                null,
                "email=?",
                new String[]{email},
                null,
                null,
                null
        );
        FavoriteCursorWrapper wrapper = new FavoriteCursorWrapper(cursor);
        ArrayList<String> favList = new ArrayList<String>();
        Log.d("JEFF_WRAPPER_COUNT", Integer.toString(wrapper.getCount()));
        if (wrapper.getCount() != 0) {
            wrapper.moveToFirst();
            favList = wrapper.getFavoriteUsers();
        }
        else {
            addFavorites(email);
        }
        wrapper.close();

        return favList;
    }




    private static ContentValues getUserContentValues(User u) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.UserTable.Cols.EMAIL, u.getEmail());
        values.put(DbSchema.UserTable.Cols.PHOTO, u.getPhoto());
        values.put(DbSchema.UserTable.Cols.PASSWORD, u.getPassword());
        values.put(DbSchema.UserTable.Cols.ORGANIZATION, u.getOrganization());
        values.put(DbSchema.UserTable.Cols.LOCATION, u.getLocation());
        values.put(DbSchema.UserTable.Cols.BIO, u.getBio());

        return values;
    }

    private static ContentValues getEventContentValues(Event f) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.EventTable.Cols.EMAIL, f.getEmail());
        values.put(DbSchema.EventTable.Cols.POST_DATE, f.getPostDate());
        values.put(DbSchema.EventTable.Cols.TITLE, f.getTitle());
        values.put(DbSchema.EventTable.Cols.EVENT_PICTURE, f.getEventPic());
        values.put(DbSchema.EventTable.Cols.DETAILS, f.getDetails());
        values.put(DbSchema.EventTable.Cols.START_TIME, f.getStartTime());
        values.put(DbSchema.EventTable.Cols.END_TIME, f.getEndTime());
        values.put(DbSchema.EventTable.Cols.START_DATE, f.getStartDate());
        values.put(DbSchema.EventTable.Cols.END_DATE, f.getEndDate());
        values.put(DbSchema.EventTable.Cols.TYPE, f.getType());
        values.put(DbSchema.EventTable.Cols.LOCATION, f.getLocation());

        return values;
    }



    private UserCursorWrapper queryUsers(String where, String[] args) {
        Cursor cursor = mDB.query(
                DbSchema.UserTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return new UserCursorWrapper(cursor);
    }


    public class UserCursorWrapper extends android.database.CursorWrapper {


        public UserCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public User getUser() {
            String email = getString(getColumnIndex(DbSchema.UserTable.Cols.EMAIL));
            String password = getString(getColumnIndex(DbSchema.UserTable.Cols.PASSWORD));
            byte[] photo = getBlob(getColumnIndex(DbSchema.UserTable.Cols.PHOTO));
            String organization = getString(getColumnIndex(DbSchema.UserTable.Cols.ORGANIZATION));
            String location = getString(getColumnIndex(DbSchema.UserTable.Cols.LOCATION));
            String bio = getString(getColumnIndex(DbSchema.UserTable.Cols.BIO));


            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoto(photo);
            user.setOrganization(organization);
            user.setLocation(location);
            user.setBio(bio);

            return user;
        }

    }


    private EventCursorWrapper queryEvents(String where, String[] args) {
        Cursor cursor = mDB.query(
                DbSchema.EventTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return new EventCursorWrapper(cursor);
    }


    public class EventCursorWrapper extends android.database.CursorWrapper {


        public EventCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Event getEvent() {
            String email = getString(getColumnIndex(DbSchema.EventTable.Cols.EMAIL));
            String postDate = getString(getColumnIndex(DbSchema.EventTable.Cols.POST_DATE));
            String title = getString(getColumnIndex(DbSchema.EventTable.Cols.TITLE));
            byte[] eventPic = getBlob(getColumnIndex(DbSchema.EventTable.Cols.EVENT_PICTURE));
            String details = getString(getColumnIndex(DbSchema.EventTable.Cols.DETAILS));
            String startTime = getString(getColumnIndex(DbSchema.EventTable.Cols.START_TIME));
            String endTime = getString(getColumnIndex(DbSchema.EventTable.Cols.END_TIME));
            String startDate = getString(getColumnIndex(DbSchema.EventTable.Cols.START_DATE));
            String endDate = getString(getColumnIndex(DbSchema.EventTable.Cols.END_DATE));
            String type = getString(getColumnIndex(DbSchema.EventTable.Cols.TYPE));
            String location = getString(getColumnIndex(DbSchema.EventTable.Cols.LOCATION));

            Event Event = new Event();

            Event.setEmail(email);
            Event.setPostDate(postDate);
            Event.setDetails(details);
            Event.setEventPic(eventPic);
            Event.setTitle(title);
            Event.setStartTime(startTime);
            Event.setEndTime(endTime);
            Event.setStartDate(startDate);
            Event.setEndDate(endDate);
            Event.setLocation(location);
            Event.setType(type);


            return Event;
        }

    }

    public class FavoriteCursorWrapper extends android.database.CursorWrapper {


        public FavoriteCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public ArrayList<String> getFavoriteEvents() {
            String email = getString(getColumnIndex(DbSchema.Favorites.Cols.EMAIL));
            String favorites = getString(getColumnIndex(DbSchema.Favorites.Cols.FAVORITE_EVENTS));

            String[] items = favorites.split(",");
            ArrayList<String> favList = new ArrayList<String>();

            Log.d("JEFF_ITEMS_IN_LIST", Integer.toString(items.length));
            for (int i = 0; i < items.length; i++) {
                favList.add(items[i]);

            }


            return favList;
        }

        public ArrayList<String> getFavoriteUsers() {
            String email = getString(getColumnIndex(DbSchema.Favorites.Cols.EMAIL));
            String favorites = getString(getColumnIndex(DbSchema.Favorites.Cols.FAVORITE_USERS));

            String[] items = favorites.split(",");
            ArrayList<String> favList = new ArrayList<String>();

            Log.d("JEFF_ITEMS_IN_LIST", Integer.toString(items.length));
            for (int i = 0; i < items.length; i++) {
                favList.add(items[i]);

            }


            return favList;
        }

    }

}
