package soundpool;

/**
 * Created by jhrebena on 11/21/15.
 */
public class Track {

    private final String mPath;
    private final String mName;
    private Integer mId;

    public Track(String p, String n) {
        mPath = p;
        mName = n;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getPath() {
        return mPath;
    }
    public String getName() {
        return mName;
    }

    public Integer getId() {
        return mId;
    }


}
