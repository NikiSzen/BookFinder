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

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    private static final String BOOK_REQUEST_URL= "https://www.googleapis.com/books/v1/volumes?q=search+terms";

    private BookArrayAdapter mAdapter;

    // Hard coded search value:
    String mySearchKeyword ="Android for Work";

    /*
    //Fake authors for test
    private String[] authors0 = {"Cheese", "Pepperoni"};
    private String[] authors1 = {"Michael Peterstug", "Nikola Szendzielorz", "Black Olives"};
    private String[] authors2 = {"Mike Anderson", "Black Holygranade"};
    private String[] authors3 = {"Cheese Adam"};
    private String[] authors4 = {"Pepperoni Anna", "Oliver Houston"};
    private String[] authors5 = {"Cheese", "Pepperoni", "Black Olives","Anna Kowalska"};
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

/*
        // Create a fake list of books.
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book("San Francisco",authors0,"2000-12-05"));
        books.add(new Book("San Francisco",authors1,"2000-12-05"));
        books.add(new Book("San Francisco",authors2,"2000-12-05"));
        books.add(new Book("San Francisco",authors3,"2000-12-05"));
        books.add(new Book("San Francisco",authors4,"2000-12-05"));
        books.add(new Book("San Francisco",authors1,"2000-12-05"));
        books.add(new Book("San Francisco",authors5,"2000-12-05"));

        // Find a reference to the {@link ListView} in the layout
        ListView booksListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of books
        BookArrayAdapter bookAdapter = new BookArrayAdapter(this, books);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booksListView.setAdapter(bookAdapter);
*/

        // Network version:
        // Check if there is internet connectivity.
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Find a reference to the {@link ListView} in the layout
        ListView booksListView = (ListView) findViewById(R.id.list);

        if(isConnected) {

            mAdapter = new BookArrayAdapter(this, new ArrayList<Book>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            booksListView.setAdapter(mAdapter);


            // Perform a network request and update UI //old version used no loader
            //new FetchEarthquakeDataTask().execute(USGS_REQUEST_URL);

            // Get a reference to the LoaderManager, in order to interact with loaders. // for other library
            //LoaderManager loaderManager = getLoaderManager();

            //Log.i(LOG_TAG, "Start of initLoader");
            getSupportLoaderManager().initLoader(0, null, this);

        }


    }

    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int id, Bundle args) {
        //Log.i(LOG_TAG, "onCreateLoader callback");
        return new BookLoader(BookActivity.this,BOOK_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> data) {
        //Log.i(LOG_TAG, "onLoadFinished callback");
        //TODO: Progress bar and empty state.
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
