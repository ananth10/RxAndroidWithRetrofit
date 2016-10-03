
package com.ananth.rxandroidwithretrofit.model.gists;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeStatus {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("additions")
    @Expose
    private Integer additions;
    @SerializedName("deletions")
    @Expose
    private Integer deletions;

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The additions
     */
    public Integer getAdditions() {
        return additions;
    }

    /**
     * 
     * @param additions
     *     The additions
     */
    public void setAdditions(Integer additions) {
        this.additions = additions;
    }

    /**
     * 
     * @return
     *     The deletions
     */
    public Integer getDeletions() {
        return deletions;
    }

    /**
     * 
     * @param deletions
     *     The deletions
     */
    public void setDeletions(Integer deletions) {
        this.deletions = deletions;
    }

}
