package com.szendzielorz.nikola.bookfinder;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by Nicole on 25.10.2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        //Log.i("Book Loader", "onStartLoading method");
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        //Log.i("Book Loader", "loadInBackground method");
        if(mUrl == null){
            return null;
        }

        // Perform the HTTP request for book data and process the response.
        ArrayList<Book> bookList = QueryUtils.fetchBookData(mUrl);
        return bookList;
    }
}
