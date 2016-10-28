package Database;

/**
 * Created by jhrebena on 11/9/15.
 */
public class DbSchema {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "users.db";

    public static final class UserTable {
        public static final String NAME = "users";

        public static final class Cols {
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String ORGANIZATION = "organization";
            public static final String LOCATION = "location";
            public static final String BIO = "bio";
            public static final String PHOTO = "photo";
        }
    }

    public static class EventTable {
        public static final String NAME = "events";

        public static final class Cols {
            public static final String EMAIL = "email";
            public static final String POST_DATE = "postdate";
            public static final String TITLE = "title";
            public static final String EVENT_PICTURE = "postedpicture";
            public static final String DETAILS = "details";
            public static final String START_TIME = "starttime";
            public static final String END_TIME = "endtime";
            public static final String START_DATE = "startdate";
            public static final String END_DATE = "enddate";
            public static final String TYPE = "type";
            public static final String LOCATION = "location";

        }
    }

    public static class Favorites {
        public static final String NAME = "favorites";

        public static final class Cols {
            public static final String EMAIL = "email";
            public static final String FAVORITE_EVENTS = "favoriteevents";
            public static final String FAVORITE_USERS = "favoriteusers";
        }
    }
}