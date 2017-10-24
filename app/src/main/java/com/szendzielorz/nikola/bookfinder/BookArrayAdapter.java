package com.szendzielorz.nikola.bookfinder;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nicole on 24.10.2017.
 */

public class BookArrayAdapter extends ArrayAdapter<Book> {


    public BookArrayAdapter(Activity context, ArrayList<Book> books) {
        super(context,0, books);
    }


    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view RECYCLING :)
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        final Book currentBook = getItem(position);

        //ImageView imageView = (ImageView) listItemView.findViewById(R.id.bookImageView);
        //imageView.setImageDrawable() //(currentBook.getImageURL());

        TextView titleTextView = (TextView) listItemView.findViewById(R.id.titleTextView);
        titleTextView.setText(currentBook.getTitle());

        TextView authorsTextView = (TextView) listItemView.findViewById(R.id.authorsTextView);
        authorsTextView.setText(currentBook.getAutors());

        TextView yearTextView = (TextView) listItemView.findViewById(R.id.yearTextView);
        yearTextView.setText(currentBook.getYear());


        //TextView magTextView = (TextView) listItemView.findViewById(R.id.magnitude_textView);
        //DecimalFormat formatter = new DecimalFormat("0.0");
        //String output = formatter.format(currentEarthquake.getMagnitude());
        //magTextView.setText(output);

        return listItemView;
    }
}
