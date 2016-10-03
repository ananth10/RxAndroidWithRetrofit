
package com.ananth.rxandroidwithretrofit.model.gists;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Files {

    @SerializedName("CutLayout.java")
    @Expose
    private CutLayoutJava cutLayoutJava;

    /**
     * 
     * @return
     *     The cutLayoutJava
     */
    public CutLayoutJava getCutLayoutJava() {
        return cutLayoutJava;
    }

    /**
     * 
     * @param cutLayoutJava
     *     The CutLayout.java
     */
    public void setCutLayoutJava(CutLayoutJava cutLayoutJava) {
        this.cutLayoutJava = cutLayoutJava;
    }

}
