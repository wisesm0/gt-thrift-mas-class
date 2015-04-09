package com.example.cxz.gtthrift;
import java.io.Serializable;
import java.util.ArrayList;

class Listings extends APIResponder implements Serializable{
    ArrayList<ListingDetail> listings = new ArrayList<ListingDetail>();
    public Listings(int eCode, String eMessage){
        super(eCode,eMessage);
    }
    Listings(ArrayList<ListingDetail> listings){
        this.listings = listings;
    }

    public ArrayList<ListingDetail> getListings(){
        return listings;
    }

}
