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
                + DbSchema.UserTable.Cols.FIRST_NAME + ", "
                + DbSchema.UserTable.Cols.LAST_NAME + ", "
                + DbSchema.UserTable.Cols.BIRTHDAY + ", "
                + DbSchema.UserTable.Cols.HOMETOWN + ", "
                + DbSchema.UserTable.Cols.BIO + ", "
                + DbSchema.UserTable.Cols.PHOTO + " BLOB)");


        db.execSQL("create table " + DbSchema.FeedItems.NAME
                + "(_id integer primary key autoincrement, "
                + DbSchema.FeedItems.Cols.EMAIL + ", "
                + DbSchema.FeedItems.Cols.POST_DATE + ", "
                + DbSchema.FeedItems.Cols.CONTENT + ", "
                + DbSchema.FeedItems.Cols.POSTED_PICTURE + ")");

        db.execSQL("create table " + DbSchema.Favorites.NAME
                + "(_id integer primary key autoincrement, "
                + DbSchema.Favorites.Cols.EMAIL + ", "
                + DbSchema.Favorites.Cols.FAVORITE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
