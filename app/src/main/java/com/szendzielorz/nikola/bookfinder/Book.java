package com.szendzielorz.nikola.bookfinder;

import android.text.TextUtils;


/**
 * Created by Nicole on 24.10.2017.
 */

public class Book {

    //private String mImageURL; //TODO: create an book image field (url in json).
    private String mTitle;
    private String[] mAuthors;
    private String mDate;

    public Book(String title, String[] authors,String date) //String imageURL,
    {
        //mImageURL = imageURL;
        mTitle = title;
        mAuthors = authors;
        mDate = date;
    }

    //public String getImageURL(){return mImageURL;}
    public String getTitle(){return mTitle;}
    public String getAutors(){
        String result = TextUtils.join(",\n", mAuthors);

        return result;
    }
    public String getYear(){
        String str[] = mDate.split("-");
        return str[0];
    }

}
