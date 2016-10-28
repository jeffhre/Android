package model;


/**
 * Created by jhrebena on 11/9/15.
 */
public class User {

    private String mEmail;
    private String mPassword;
    private String mOrganization;
    private String mLocation;
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

    public String getOrganization() {
        return mOrganization;
    }
    public void setOrganization(String org) {
        mOrganization = org;
    }

    public String getLocation() {
        return mLocation;
    }
    public void setLocation(String ht) {
        mLocation = ht;
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

}
