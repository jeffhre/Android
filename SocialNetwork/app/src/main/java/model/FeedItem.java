package model;


/**
 * Created by jhrebena on 11/10/15.
 */
public class FeedItem extends FeedItemComparator {

    private String mEmail;
    private String mPostDate;
    private String mContent;
    private byte[] mPostedPic;


    public FeedItem() {

    }

    public String getEmail() {
        return mEmail;
    }
    public void setEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPostDate() {
        return mPostDate;
    }
    public void setPostDate(String pd) {
        this.mPostDate = pd;
    }

    public String getContent() {
        return mContent;
    }
    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public byte[] getPostedPic() {
        return mPostedPic;
    }
    public void setPostedPic(byte[] mPostedPic) {
        this.mPostedPic = mPostedPic;
    }



}
