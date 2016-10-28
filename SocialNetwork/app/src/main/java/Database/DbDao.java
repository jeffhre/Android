package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import model.FeedItem;
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

    public void addFeedItem(FeedItem f) {
        mDB.insert(
                DbSchema.FeedItems.NAME,
                null,
                getFeedItemContentValues(f)
        );
    }

    public void addFavorite(String email) {

        ContentValues values = new ContentValues();
        values.put(DbSchema.Favorites.Cols.EMAIL, email);
        values.put(DbSchema.Favorites.Cols.FAVORITE, email + "");

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
    public ArrayList<FeedItem> getAllFeedItems() {
        ArrayList<FeedItem> feedItems = new ArrayList<FeedItem>();
        FeedItemCursorWrapper cursor = queryFeedItems(null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            FeedItem f = cursor.getFeedItem();
            feedItems.add(f);
            cursor.moveToNext();
        }
        cursor.close();
        return feedItems;
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

    public void updateFavorite(String email, ArrayList<String> favorite) {
        String parseString = "";
        for (String s : favorite) {
            parseString += s;
            parseString += ",";
        }
        Log.d(jl, parseString);

        ContentValues values = new ContentValues();
        values.put(DbSchema.Favorites.Cols.EMAIL, email);
        values.put(DbSchema.Favorites.Cols.FAVORITE, parseString);

        mDB.update(
                DbSchema.Favorites.NAME,
                values,
                DbSchema.Favorites.Cols.EMAIL + "=?",
                new String[]{email}
        );
    }

    public ArrayList<String> getFavorite(String email) {
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
            favList = wrapper.getFavoriteItem();
        }
        else {
            addFavorite(email);
        }
        wrapper.close();

        return favList;
    }




    private static ContentValues getUserContentValues(User u) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.UserTable.Cols.EMAIL, u.getEmail());
        values.put(DbSchema.UserTable.Cols.PHOTO, u.getPhoto());
        values.put(DbSchema.UserTable.Cols.PASSWORD, u.getPassword());
        values.put(DbSchema.UserTable.Cols.FIRST_NAME, u.getFirstName());
        values.put(DbSchema.UserTable.Cols.LAST_NAME, u.getLastName());
        values.put(DbSchema.UserTable.Cols.BIRTHDAY, u.getBirthday().toString());
        values.put(DbSchema.UserTable.Cols.HOMETOWN, u.getHometown());
        values.put(DbSchema.UserTable.Cols.BIO, u.getBio());

        return values;
    }

    private static ContentValues getFeedItemContentValues(FeedItem f) {
        ContentValues values = new ContentValues();

        values.put(DbSchema.FeedItems.Cols.EMAIL, f.getEmail());
        values.put(DbSchema.FeedItems.Cols.POST_DATE, f.getPostDate());
        values.put(DbSchema.FeedItems.Cols.CONTENT, f.getContent());
        values.put(DbSchema.FeedItems.Cols.POSTED_PICTURE, f.getPostedPic());


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
            String firstName = getString(getColumnIndex(DbSchema.UserTable.Cols.FIRST_NAME));
            String lastName = getString(getColumnIndex(DbSchema.UserTable.Cols.LAST_NAME));
            String birthday = getString(getColumnIndex(DbSchema.UserTable.Cols.BIRTHDAY));
            String hometown = getString(getColumnIndex(DbSchema.UserTable.Cols.HOMETOWN));
            String bio = getString(getColumnIndex(DbSchema.UserTable.Cols.BIO));


            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setPhoto(photo);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setBirthday(birthday);
            user.setHometown(hometown);
            user.setBio(bio);

            return user;
        }

    }


    private FeedItemCursorWrapper queryFeedItems(String where, String[] args) {
        Cursor cursor = mDB.query(
                DbSchema.FeedItems.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );
        return new FeedItemCursorWrapper(cursor);
    }


    public class FeedItemCursorWrapper extends android.database.CursorWrapper {


        public FeedItemCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public FeedItem getFeedItem() {
            String email = getString(getColumnIndex(DbSchema.FeedItems.Cols.EMAIL));
            String postDate = getString(getColumnIndex(DbSchema.FeedItems.Cols.POST_DATE));
            String content = getString(getColumnIndex(DbSchema.FeedItems.Cols.CONTENT));
            byte[] postedPhoto = getBlob(getColumnIndex(DbSchema.FeedItems.Cols.POSTED_PICTURE));

            FeedItem feeditem = new FeedItem();

            feeditem.setEmail(email);
            feeditem.setPostDate(postDate);
            feeditem.setContent(content);
            feeditem.setPostedPic(postedPhoto);


            return feeditem;
        }

    }

    public class FavoriteCursorWrapper extends android.database.CursorWrapper {


        public FavoriteCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public ArrayList<String> getFavoriteItem() {
            String email = getString(getColumnIndex(DbSchema.Favorites.Cols.EMAIL));
            String favorites = getString(getColumnIndex(DbSchema.Favorites.Cols.FAVORITE));

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
