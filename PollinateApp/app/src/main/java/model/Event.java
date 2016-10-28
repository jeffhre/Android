package model;


/**
 * Created by jhrebena on 11/10/15.
 */
public class Event extends EventComparator {

    private String mEmail;
    private String mOrganization;
    private String mPostDate;
    private byte[] mEventPic;
    private String mTitle;
    private String mDetails;
    private String mLocation;
    private String mType;

    private String mStartTime;
    private String mStartDate;
    private String mEndTime;
    private String mEndDate;



    public Event() {

    }



    public String getEmail() {
        return mEmail;
    }
    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getOrganization() {
        return mOrganization;
    }

    public void setOrganization(String mOrganization) {
        this.mOrganization = mOrganization;
    }

    public String getPostDate() {
        return mPostDate;
    }
    public void setPostDate(String pd) {
        this.mPostDate = pd;
    }

    public String getDetails() {
        return mDetails;
    }
    public void setDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String mTime) {
        mStartTime = mTime;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String mTime) {
        mEndTime = mTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public byte[] getEventPic() {
        return mEventPic;
    }

    public void setEventPic(byte[] mEventPic) {
        this.mEventPic = mEventPic;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public void setStartDate(String StartDate) {
        mStartDate = StartDate;
    }
    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String EndDate) {
        mEndDate = EndDate;
    }



}
