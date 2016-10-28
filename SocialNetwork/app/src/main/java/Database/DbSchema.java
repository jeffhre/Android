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
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String BIRTHDAY = "birthday";
            public static final String HOMETOWN = "hometown";
            public static final String BIO = "bio";
            public static final String PHOTO = "photo";
        }
    }

    public static class FeedItems {
        public static final String NAME = "feeditems";

        public static final class Cols {
            public static final String EMAIL = "email";
            public static final String POST_DATE = "postdate";
            public static final String CONTENT = "content";
            public static final String POSTED_PICTURE = "postedpicture";
        }
    }

    public static class Favorites {
        public static final String NAME = "favorites";

        public static final class Cols {
            public static final String EMAIL = "email";
            public static final String FAVORITE = "favorite";
        }
    }
}