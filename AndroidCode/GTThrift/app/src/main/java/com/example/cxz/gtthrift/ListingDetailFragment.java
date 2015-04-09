package com.example.cxz.gtthrift;
import java.io.IOException;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cxz.gtthrift.APICallBackInterface;
import com.example.cxz.gtthrift.R;

public class ListingDetailFragment extends Fragment {


    ImageView img;
    BitMaps bitmaps;
    View rootView;
    ListingDetail listing;
    ImageView itemImageView;
    ImageView profileImageView;
    Button replyButton;
    private static final String ARG_LISTING = "listing";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.listing_detail_fragment, container, false);
        listing = (ListingDetail) getArguments().getSerializable(ARG_LISTING);

        bitmaps = new BitMaps(listing,this);

        TextView sellerNameView = (TextView) rootView.findViewById(R.id.postedByText);
        TextView titleView = (TextView) rootView.findViewById(R.id.title);
        TextView postingDateView = (TextView) rootView.findViewById(R.id.postingDate);
        TextView descriptionView = (TextView) rootView.findViewById(R.id.description);
        itemImageView = (ImageView) rootView.findViewById(R.id.imageView1);
        profileImageView = (ImageView) rootView.findViewById(R.id.imageProfile);
        replyButton = (Button) rootView.findViewById(R.id.replyButton);


        sellerNameView.setText("Posted by " + listing.getSellerName());
        titleView.setText(listing.getTitle());
        postingDateView.setText("Posted on " + listing.getDate());
        descriptionView.setText(listing.getDescription());
        if(listing.getFbProfile().isEmpty())
            profileImageView.setImageResource(R.drawable.profile);
        else
            updatePhotos();
        if(listing.getPics().isEmpty()) {
            itemImageView.setAlpha( (float) 0.6);
            itemImageView.setImageResource(R.drawable.buzz);
        }
        else
            updatePhotos();
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //onCLick response: open seller's fb profile
                String url = "https://www.facebook.com/" + listing.getSellerId();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        return rootView;

    }
    public void updatePhotos(){
        if(bitmaps.getItemBitmap()!=null){
            itemImageView.setAlpha( (float) 1.0);
            itemImageView.setImageBitmap(bitmaps.getItemBitmap());}
        if(bitmaps.getProfileBitmap()!=null)
            profileImageView.setImageBitmap(bitmaps.getProfileBitmap());
    }

}