package com.example.cxz.gtthrift;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


public class PostListingFragment extends Fragment implements APICallBackInterface{
    View rootView;
    EditText descriptionEditView;
    EditText titleEditView;
    Spinner categorySpinner;
    Button replyButton;
    Context thisActivity;
    TextView userImageView;
    Bitmap bitmap;
    APICallBackInterface thisFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisFragment = this;
        thisActivity = (Context)getActivity();
        ((MainActivity) getActivity()).setActionBarTitle("Post Your Item");
        rootView = inflater.inflate(R.layout.post_listing_fragment, container, false);
        descriptionEditView = (EditText)rootView.findViewById(R.id.description);
        titleEditView = (EditText) rootView.findViewById(R.id.title);
        categorySpinner = (Spinner) rootView.findViewById(R.id.category);
        userImageView = (TextView) rootView.findViewById(R.id.userImage);
        userImageView.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(intent, 1);
                }
            }
        );
        replyButton = (Button) rootView.findViewById(R.id.replyButton);
        replyButton.setOnClickListener(
            new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    String title = titleEditView.getText().toString();
                    String description = descriptionEditView.getText().toString();
                    String category = categorySpinner.getSelectedItem().toString();
                    String api_param = "postlisting/";
                    if( ((MainActivity) getActivity()).okToPerformAPICall())
                        new APICall(thisActivity, thisFragment, 1).execute(api_param,title,description,category);

                }
            }
        );



        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
            try {
                // We need to recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = thisActivity.getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
                userImageView.setBackground(ob);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    public void respondToAPICall(APIResponder response) {

    }
}