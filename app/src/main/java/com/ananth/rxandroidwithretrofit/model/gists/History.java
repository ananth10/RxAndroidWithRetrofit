
package com.ananth.rxandroidwithretrofit.model.gists;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class History {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("committed_at")
    @Expose
    private String committedAt;
    @SerializedName("change_status")
    @Expose
    private ChangeStatus changeStatus;
    @SerializedName("url")
    @Expose
    private String url;

    /**
     * 
     * @return
     *     The user
     */
    public User getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 
     * @return
     *     The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * 
     * @param version
     *     The version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 
     * @return
     *     The committedAt
     */
    public String getCommittedAt() {
        return committedAt;
    }

    /**
     * 
     * @param committedAt
     *     The committed_at
     */
    public void setCommittedAt(String committedAt) {
        this.committedAt = committedAt;
    }

    /**
     * 
     * @return
     *     The changeStatus
     */
    public ChangeStatus getChangeStatus() {
        return changeStatus;
    }

    /**
     * 
     * @param changeStatus
     *     The change_status
     */
    public void setChangeStatus(ChangeStatus changeStatus) {
        this.changeStatus = changeStatus;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
