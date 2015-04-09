package com.example.cxz.gtthrift;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;


//this class takes in picture urls from listings, loads the pictures in a separate thread and updates the calling fragment

public class BitMaps{
    HashMap<String,Bitmap> profileBitmaps = new HashMap<String,Bitmap>(); //for listings
    HashMap<String,Bitmap> itemBitmaps = new HashMap<String,Bitmap>(); //for listings
    Bitmap profileBitmap;//for single listing
    Bitmap itemBitmap;//for single listing
    ListingDetailFragment mFragment;
    ListingsFragment.ListingsAdapter mAdapter=null;


    public BitMaps(ListingDetail listing, ListingDetailFragment fragment) { //for single listing
        mFragment = fragment;
        new ImageLoadTask(2).execute("https://graph.facebook.com/" + listing.getSellerId() + "/picture?type=square");
        new ImageLoadTask(3).execute(listing.getPics());
    }

    public BitMaps(ArrayList<ListingDetail> listings, ListingsFragment.ListingsAdapter adapter) //for listings
    {
        mAdapter = adapter;
        for(int i = 0; i< listings.size(); i++){
            new ImageLoadTask(0,listings.get(i).getFbProfile()).execute("https://graph.facebook.com/" + listings.get(i).getSellerId() + "/picture?type=square");
            new ImageLoadTask(1,listings.get(i).getFbProfile()).execute(listings.get(i).getPics());
        }

    }


    public Bitmap getProfileBitmap(String postLink){
        return profileBitmaps.get(postLink);
    }
    public Bitmap getProfileBitmap(){
        return profileBitmap;
    }
    public Bitmap getItemBitmap(String postLink){
        return itemBitmaps.get(postLink);
    }
    public Bitmap getItemBitmap(){
        return itemBitmap;
    }




    // ASYNC TASK TO AVOID CHOKING UP UI THREAD

    private class ImageLoadTask extends AsyncTask<String, String, Bitmap> {
        int i; // 0 = load profile image for multiple listings,  1 = item pictures for multiple listings
        String postLink;
        public ImageLoadTask(int i, String postLink){
            this.i=i;
            this.postLink = postLink;
        }
        public ImageLoadTask(int i){
            this.i=i;
        }
        @Override
        protected void onPreExecute() {
            Log.i("ImageLoadTask", "Loading image...");
        }

        // PARAM[0] IS IMG URL
        protected Bitmap doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setInstanceFollowRedirects(true);  //you still need to handle redirect manully.
                Bitmap b = BitmapFactory.decodeStream(conn.getInputStream());
                return b;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onProgressUpdate(String... progress) {
            // NO OP
        }

        protected void onPostExecute(Bitmap ret) {
            if (ret != null) {
                if(i==0)
                    profileBitmaps.put(postLink,ret);
                else if(i==1)
                    itemBitmaps.put(postLink,ret);
                else if(i==2)
                    profileBitmap = ret;
                else if(i==3)
                    itemBitmap = ret;
                if (mAdapter != null) {
                    // WHEN IMAGE IS LOADED NOTIFY THE ADAPTER
                    mAdapter.notifyDataSetChanged();
                }
                if(i==2 || i == 3){
                    mFragment.updatePhotos();
                }
            } else {
            }
        }
    }


}