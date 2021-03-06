package com.szendzielorz.nikola.bookfinder;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    private static final String BOOK_REQUEST_URL= "https://www.googleapis.com/books/v1/volumes?";

    private BookArrayAdapter mAdapter;
    private ProgressBar mLoadingSpinner;
    private TextView mEmptyListTextView;

    // Hard coded search value:
    String mySearchKeyword ="tea";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        mySearchKeyword = intent.getExtras().getString("searchKeyword");

        // Check if there is internet connectivity.
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        mLoadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);

        // Find a reference to the {@link ListView} in the layout
        ListView booksListView = (ListView) findViewById(R.id.list);
        mEmptyListTextView = (TextView) findViewById(R.id.emptyList);
        booksListView.setEmptyView(mEmptyListTextView);


        if(isConnected) {

            mAdapter = new BookArrayAdapter(this, new ArrayList<Book>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            booksListView.setAdapter(mAdapter);

            //Log.i(LOG_TAG, "Start of initLoader");
            getSupportLoaderManager().initLoader(0, null, this);

        }else{
            mLoadingSpinner.setVisibility(View.GONE);
            mEmptyListTextView.setText("No internet connection.");
        }


    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        //Log.i(LOG_TAG, "onCreateLoader callback");
        return new BookLoader(BookActivity.this,BOOK_REQUEST_URL, mySearchKeyword);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        //Log.i(LOG_TAG, "onLoadFinished callback");
        mLoadingSpinner.setVisibility(View.GONE);
        mEmptyListTextView.setText(R.string.no_books);
        // Clear adapter from all the previous data.
        mAdapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data); //comment to check if empty state works
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        //Log.i(LOG_TAG, "onLoaderReset callback");
        mAdapter.clear();
    }
}
