package com.example.cxz.gtthrift;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.ArrayList;
class ListingDetail extends APIResponder implements Serializable{
    private String title;
    private String date;
    private String description;
    private String fb_profile;
    private String seller_id;
    private String picture;
    private String sellerName;
    private String postID;
    public ListingDetail(int eCode, String eMessage){
        super(eCode,eMessage);
    }
    public ListingDetail(String title, String date, String description, String fb_profile, String seller_id, String picture, String sellerName, String postID){
        this.title = title;
        this.date=date;
        this.description = description;
        this.fb_profile = fb_profile;
        this.seller_id = seller_id;
        this.picture = picture;
        this.sellerName = sellerName;
        this.postID = postID;
     //   new ImageLoadTask(0).execute("https://graph.facebook.com/" + seller_id + "/picture?type=square");
     //   new ImageLoadTask(1).execute(picture);
    }
    public String getPostID(){
        return postID;
    }
    public String getTitle(){
        return title;
    }
    public String getDate(){
        return date;
    }
    public String getDescription(){
        return description;
    }
    public String getFbProfile(){
        return fb_profile;
    }
    public String getSellerId(){
        return seller_id;
    }
    public String getPics(){
        return picture;
    }
    public String getSellerName() { return sellerName; }

}