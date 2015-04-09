package com.example.cxz.gtthrift;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.JsonReader;
import android.util.JsonToken;
import android.widget.Toast;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class APICall extends AsyncTask<String, Void, APIResponder>{


        private Context mContext;
        private APICallBackInterface callingFragment;
        private String mAPICall = "http://2.genuine-amulet-864.appspot.com/";
        private APIResponder responder;
        private String token;
        private ProgressDialog progDialog;					//the "download in progress" animation
        private Handler handler;							//handler for the progress animation
        private Runnable progRunnable;						//runnable holding the configuration / execution code for progress animation
        private int getPostFlag;
        private String title;
        private String description;
        private String category;

        public APICall(Context context, APICallBackInterface callingFragment, int getPostFlag)
        {
            this.getPostFlag = getPostFlag;
            mContext = context;
            this.callingFragment = callingFragment;
            ((MainActivity) mContext).preventNextAPICall(); //acquire lock to prevent overlapping calls
        }

        protected void onPreExecute() {
            super.onPreExecute();

            //starts a new handler (thread) that posts a runnable holding the progress animation
            //posts the runnable to message queue (plays the progress animation) to be executed if APICall takes > 170ms
            handler = new Handler();
            progRunnable = new Runnable() {
                @Override
                public void run() {
                    progDialog = new ProgressDialog(((MainActivity) mContext));
                    progDialog.setMessage("Loading...");
                    progDialog.setIndeterminate(true);
                    progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progDialog.setCancelable(true);
                    progDialog.show();
                    ((MainActivity) mContext).progDialogIsRunning();
                }
            };
            handler.postDelayed(progRunnable, 0);
        }

        protected APIResponder doInBackground(String... param) {
            if (getPostFlag == 0) //get
                mAPICall += param[0]; //param[0] holds the string of the API call originating from the calling fragment
            else if (getPostFlag == 1){ //post

                mAPICall += param[0];
                title=param[1];
                description=param[2];
                category=param[3];
            }
            checkNetwork(); //starts the method chain with post condition: response from API call is completely parsed and stored in mResponse;
            return responder;
        }


        protected void onPostExecute(APIResponder responder) {
            //removes any non-executed progRunnable from message queue, if progRunnable is already running, dismiss.
            handler.removeCallbacks(progRunnable);
            if(  ((MainActivity) mContext).isProgDialogRunning()==true  ){
                progDialog.dismiss();
                ((MainActivity) mContext).progDialogIsNotRunning();
            }
            ((MainActivity) mContext).allowNextAPICall(); //release API call lock
            if(getPostFlag==0) {

                //error handling, any eCode other than 0 and 5 represent errors
                if (responder.getECode() != -1)
                    Toast.makeText(mContext, "error code: " + responder.getECode() + ": " + responder.getErrorMessage(), Toast.LENGTH_SHORT).show();
                else {
                    Log.d("","JJJJJJJJJJJDDD");

                    callingFragment.respondToAPICall(responder);
                }
            }
        }

        public void checkNetwork() {
            ConnectivityManager connMgr = (ConnectivityManager)
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                openHttpConnection();
            } else {
                responder = new ListingDetail(0, "no network connection");
            }
        }


        private void openHttpConnection() { //boiler plate code for opening HTTP connecting and passing stream to JSON Reader

            if(getPostFlag==1) {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(mAPICall);

                try {
                    // Add your data
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                    nameValuePairs.add(new BasicNameValuePair("title", title));
                    nameValuePairs.add(new BasicNameValuePair("message", description));
                    nameValuePairs.add(new BasicNameValuePair("category", category));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                }
            }
            if(getPostFlag==0){
                InputStream is = null;

                try {
                URL url = new URL(mAPICall);
                    Log.d("","JJJJJJJJJJJ" + mAPICall);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                is = conn.getInputStream();
                parseJsonRoot(is);
                is.close();
                } catch (IOException e) {
                e.printStackTrace();
                    responder = new APIResponder(0, "HTTP connection failed");
                }
            }
        }


        private void parseJsonRoot(InputStream stream) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
            parseListings(reader);
          /*  reader.beginArray();

            while (reader.hasNext()){
                token = reader.nextName();
            switch(token){
                case("listings"):
                    parseListings(reader);
                break;
                case("email"):
                    //parseSellerProfile(reader);
                break;
                case("error_code"): //errors
                    int eCode = reader.nextInt();
                    reader.nextName();
                    String eMessage = reader.nextString();
                    responder = new APIResponder(eCode, eMessage );
                break;
                default: 	//unknown API responses
                    responder = new APIResponder(0, "API response not recognized");
                }
            }
            reader.endObject();
         */   reader.close();
        }


        private void parseListings(JsonReader reader) throws IOException{
            ArrayList<ListingDetail> listings = new ArrayList<ListingDetail>();

            if(!reader.peek().equals(JsonToken.NULL)){ //if listings are available
                reader.beginArray();
                while (reader.hasNext())
                    listings.add(parseListingDetail(reader));
                reader.endArray();
                responder = new Listings(listings);
            }
            else{
                reader.nextNull();
                responder = new APIResponder(1, "no available listings");
            }
        }

        private ListingDetail parseListingDetail(JsonReader reader) throws IOException{
            ListingDetail listing;
            String title = "";
            String date = "";
            String description ="";
            String fb_profile ="";
            String seller_id="";
            String postID="";
            String picture="";
            String seller_name="";
            reader.beginObject();
            while (reader.hasNext()){
                token = reader.nextName();
                switch(token){
                   case("category"):
                        reader.nextString();
                        break;
                    case("picture"):
                        picture = reader.nextString();
                        Log.d("","UUUUUUUUUUUUUUUUUUUUUUUUUUUUZ"+picture);
                        break;
                    case("title"):
                        title = reader.nextString();
                        break;
                    case("author_name"):
                        seller_name = reader.nextString();
                        break;
                    case("post_id"):
                        postID = reader.nextString();
                        break;
                    case("link_to_post"):
                        fb_profile = reader.nextString();
                        break;
                    case("date"):
                        String nextString = reader.nextString();
                        if(nextString.length()>10)
                            date = nextString.substring(5,10);
                        break;
                    case("message"):
                        description = reader.nextString();
                        break;
                    case("author_id"):
                        seller_id = reader.nextString();
                        break;
                    default:
                }
            }
            reader.endObject();
            listing = new ListingDetail(title, date, description, fb_profile, seller_id, picture, seller_name, postID);
            return listing;
        }
}