package model;


/**
 * Created by jhrebena on 11/9/15.
 */
public class User {

    private String mEmail;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mBirthday;
    private String mHometown;
    private String mBio;
    private byte[] mPhoto;


    public User() {

    }

    public String getEmail() {
        return mEmail;
    }
    public void setEmail(String e) {
        mEmail = e;
    }

    public String getPassword() {
        return mPassword;
    }
    public void setPassword(String p) {
        mPassword = p;
    }

    public String getFirstName() {
        return mFirstName;
    }
    public void setFirstName(String fn) {
        mFirstName = fn;
    }

    public String getLastName() {
        return mLastName;
    }
    public void setLastName(String ln) {
        mLastName = ln;
    }

    public String getBirthday() {
        return mBirthday;
    }
    public void setBirthday(String bd) {
        mBirthday = bd;
    }

    public String getHometown() {
        return mHometown;
    }
    public void setHometown(String ht) {
        mHometown = ht;
    }

    public String getBio() {
        return mBio;
    }
    public void setBio(String bio) {
        mBio = bio;
    }

    public byte[] getPhoto() {
        return mPhoto;
    }
    public void setPhoto(byte[] mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }



    @Override
    public String toString() {
        return "User[name=" + getFullName()
                + ", email=" + getEmail()
                + ", birthday=" + getBirthday()
                + ", hometown=" + getHometown()
                + "]";
    }

}
