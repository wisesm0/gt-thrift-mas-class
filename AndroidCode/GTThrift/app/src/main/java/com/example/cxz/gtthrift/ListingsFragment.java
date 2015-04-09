package com.example.cxz.gtthrift;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ListingsFragment extends Fragment implements APICallBackInterface {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SEARCH_STRING = "searchstring";
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PAGENUM = "pagenum";
    private String category;
    private String pageNum;
    private Context mContext;
    private Button prevButton;
    private Button nextButton;

    public ListingsFragment(){}
    View rootView;
    ListView listView;
    int sectionNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        category = ((MainActivity) getActivity()).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
        pageNum = getArguments().getString(ARG_PAGENUM);
        rootView = inflater.inflate(R.layout.listings_fragment, container, false);
        mContext = (Context) getActivity();
        sectionNum = getArguments().getInt(ARG_SECTION_NUMBER);

        // Get ListView object from xml
        listView = (ListView) rootView.findViewById(R.id.list);
        //footer stuff
        View footerView =  ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);
        listView.addFooterView(footerView);
        prevButton = (Button)footerView.findViewById(R.id.prevbutton);
        if(Integer.parseInt(pageNum)==1){
            ((ViewManager)prevButton.getParent()).removeView(prevButton);
        }
        nextButton = (Button)footerView.findViewById(R.id.nextbutton);
        prevButton.setOnClickListener(new ChangePageListener());
        nextButton.setOnClickListener(new ChangePageListener());
        String apiCall;
        if(sectionNum==-1){
            apiCall = "search/" + getArguments().getString(ARG_SEARCH_STRING) + "/1";
            ((MainActivity) getActivity()).setActionBarTitle(getArguments().getString(ARG_SEARCH_STRING));
        }
        else{
            ((MainActivity) getActivity()).setActionBarTitle(category);
            apiCall = "getlistings/"+category.toLowerCase()+ "/" + pageNum;
        }
        if( ((MainActivity) getActivity()).okToPerformAPICall())
            new APICall(getActivity(), this, 0).execute(apiCall);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    public void respondToAPICall(APIResponder response) {
        ArrayList<ListingDetail> listings = ((Listings) response).getListings();

        //initialize adapter to include listings
        ListingsAdapter adapter = new ListingsAdapter(getActivity(),
                R.layout.listings_item_fragment, listings);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        adapter.notifyDataSetInvalidated();
        adapter.notifyDataSetChanged();
        // ListView Item Click Listener

   }



    private class ChangePageListener implements View.OnClickListener {

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.prevbutton:
                    if ((Integer.parseInt(pageNum) - 1) >= 1)
                        ((MainActivity) mContext).flipPage(Integer.parseInt(pageNum) - 1);
                    break;
                case R.id.nextbutton:
                    if ((Integer.parseInt(pageNum) + 1) <= 10)
                        ((MainActivity) mContext).flipPage(Integer.parseInt(pageNum) + 1);
                    break;
            }
        }
    }





    public class ListingsAdapter extends ArrayAdapter<ListingDetail> {
        Context context;
        int layoutResourceId;
        ArrayList<ListingDetail> listings;
        BitMaps bitMaps;
        public ListingsAdapter(Context context, int layoutResourceId, ArrayList<ListingDetail> listings) {
            super(context, layoutResourceId, listings);
            this.layoutResourceId = layoutResourceId;
            this.context = context;
            this.listings = listings;
            bitMaps = new BitMaps(listings, this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            RowHolder holder = null;
            if(convertView!=null){
                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) mContext).startDetailsFragment(listings.get(position));
                    }
                });
             }

            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);
                holder = new RowHolder();
                holder.profilePic =(ImageView)row.findViewById(R.id.profilePic);
                holder.description= (TextView)row.findViewById(R.id.description);
                holder.title= (TextView)row.findViewById(R.id.title);
                holder.date= (TextView)row.findViewById(R.id.postDate);
                holder.mainPic =(ImageView)row.findViewById(R.id.itemPic);
                holder.posterName = (TextView)row.findViewById(R.id.posterName);
                row.setTag(holder);
            } else {
                holder = (RowHolder) row.getTag();
            }

            ListingDetail listing = listings.get(position);

            holder.posterName.setText(listing.getSellerName());
            holder.description.setText(listing.getDescription());
            holder.title.setText(listing.getTitle());
            holder.date.setText("Posted on " + listing.getDate());
            if(listing.getFbProfile().isEmpty() || bitMaps.getProfileBitmap(listing.getFbProfile())==null){
                holder.profilePic.setImageResource(R.drawable.profile);
            }
            else{
                holder.profilePic.setImageBitmap(bitMaps.getProfileBitmap(listing.getFbProfile()));
            }

            if(listing.getPics().isEmpty() || bitMaps.getItemBitmap(listing.getFbProfile())==null) {
                holder.mainPic.setAlpha( (float)0.6);
                holder.mainPic.setImageResource(R.drawable.buzz);
            }
            else {
                holder.mainPic.setAlpha( (float)1.0);
                holder.mainPic.setImageBitmap(bitMaps.getItemBitmap(listing.getFbProfile()));
            }
            return row;
        }

        class RowHolder {
            ImageView profilePic;
            TextView title;
            ImageView mainPic;
            TextView description;
            TextView date;
            TextView posterName;
        }
    }
}