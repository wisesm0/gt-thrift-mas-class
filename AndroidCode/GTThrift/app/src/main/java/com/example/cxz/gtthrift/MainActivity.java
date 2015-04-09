package com.example.cxz.gtthrift;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SearchView.OnQueryTextListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    MenuItem searchItem;
    private SearchView mSearchView;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_PAGENUM = "pagenum";
    private static final String ARG_SEARCH_STRING = "searchstring";
    private static final String ARG_LISTING = "listing";
    private boolean okToPerformAPICall = true;
    private int mPosition;
    private boolean isProgDialogRunning = false;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    /*

     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mTitle = getTitle();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mPosition=position;
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        //api call to category matching position
    //   ListingFragment fragment = new ListingFragment();
        ListingsFragment fragment = new ListingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position + 1);
        args.putString(ARG_PAGENUM,"1");
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void flipPage(int pageNum){
        FragmentManager fragmentManager = getSupportFragmentManager();
        //api call to category matching position
        //   ListingFragment fragment = new ListingFragment();
        ListingsFragment fragment = new ListingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, mPosition + 1);
        args.putString(ARG_PAGENUM,Integer.toString(pageNum));
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();


    }
    public String onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                return getString(R.string.title_section1);
            case 2:
                mTitle = getString(R.string.title_section2);
                return getString(R.string.title_section2);
            case 3:
                mTitle = getString(R.string.title_section3);
                return getString(R.string.title_section3);
            case 4:
                mTitle = getString(R.string.title_section4);
                return getString(R.string.title_section4);
            case 5:
                mTitle = getString(R.string.title_section5);
                return getString(R.string.title_section5);
            case 6:
                mTitle = getString(R.string.title_section6);
                return getString(R.string.title_section6);
            case 7:
                mTitle = getString(R.string.title_section7);
                return getString(R.string.title_section7);
            case 8:
                mTitle = getString(R.string.title_section8);
                return getString(R.string.title_section8);
        }
        return "";
    }
    public void setActionBarTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);

    }
    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            searchItem = menu.findItem(R.id.action_search);
            mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            mSearchView.setOnQueryTextListener(this);

            restoreActionBar();

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        isProgDialogRunning=false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_search) {
            mSearchView.setIconified(false);
            return true;
        }
        if(id==R.id.action_add){
            FragmentManager fragmentManager = getSupportFragmentManager();
            PostListingFragment fragment = new PostListingFragment();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null)
                    .commit();
        }
        return super.onOptionsItemSelected(item);
    }



    /*search stuff */

    @Override
    public boolean onQueryTextSubmit(String s) {
        searchItem.collapseActionView();
        mSearchView.setQuery("", false);
        setActionBarTitle(s);
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        //api call to category matching position
        //   ListingFragment fragment = new ListingFragment();
        ListingsFragment fragment = new ListingsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, -1); //-1 is search, -2 is dashboard
        args.putString(ARG_SEARCH_STRING, s);
        args.putString(ARG_PAGENUM,"1");
        fragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
    /*search stuff */



    /*   API CALL STUFF */

    public void preventNextAPICall() { //acquire lock
        Log.d("", "locking");
        okToPerformAPICall = false;
    }
    public void allowNextAPICall() { //release lock
        Log.d("", "release");
        okToPerformAPICall = true;
    }
    public boolean okToPerformAPICall() { //check lock status
        return okToPerformAPICall;
    }

    public void progDialogIsRunning(){
        isProgDialogRunning = true;
    }

    public void progDialogIsNotRunning(){
        isProgDialogRunning = false;
    }

    public boolean isProgDialogRunning(){
        return isProgDialogRunning;
    }
    /* API CALL STUFF */

    public void startDetailsFragment(ListingDetail listing){
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        //api call to category matching position
        //   ListingFragment fragment = new ListingFragment();
        ListingDetailFragment fragment = new ListingDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LISTING, listing);
        fragment.setArguments(args);

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

}
