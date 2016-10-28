package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by jhrebena on 11/9/15.
 */
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, DbSchema.DATABASE_NAME, null, DbSchema.VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbSchema.UserTable.NAME
                + "(_id integer primary key autoincrement, "
                + DbSchema.UserTable.Cols.EMAIL + ", "
                + DbSchema.UserTable.Cols.PASSWORD + ", "
                + DbSchema.UserTable.Cols.ORGANIZATION + ", "
                + DbSchema.UserTable.Cols.LOCATION + ", "
                + DbSchema.UserTable.Cols.BIO + ", "
                + DbSchema.UserTable.Cols.PHOTO + " BLOB)");


        db.execSQL("create table " + DbSchema.EventTable.NAME
                + "(_id integer primary key autoincrement, "
                + DbSchema.EventTable.Cols.EMAIL + ", "
                + DbSchema.EventTable.Cols.POST_DATE + ", "
                + DbSchema.EventTable.Cols.TITLE + ", "
                + DbSchema.EventTable.Cols.DETAILS + ", "
                + DbSchema.EventTable.Cols.START_TIME + ", "
                + DbSchema.EventTable.Cols.END_TIME + ", "
                + DbSchema.EventTable.Cols.START_DATE + ", "
                + DbSchema.EventTable.Cols.END_DATE + ", "
                + DbSchema.EventTable.Cols.TYPE + ", "
                + DbSchema.EventTable.Cols.LOCATION + ", "
                + DbSchema.EventTable.Cols.EVENT_PICTURE + " BLOB)");

        db.execSQL("create table " + DbSchema.Favorites.NAME
                + "(_id integer primary key autoincrement, "
                + DbSchema.Favorites.Cols.EMAIL + ", "
                + DbSchema.Favorites.Cols.FAVORITE_EVENTS + ", "
                + DbSchema.Favorites.Cols.FAVORITE_USERS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
