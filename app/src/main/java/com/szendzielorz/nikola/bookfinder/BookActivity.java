package com.szendzielorz.nikola.bookfinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    //Fake authors for test
    private String[] authors0 = {"Cheese", "Pepperoni"};
    private String[] authors1 = {"Michael Peterstug", "Nikola Szendzielorz", "Black Olives"};
    private String[] authors2 = {"Mike Anderson", "Black Holygranade"};
    private String[] authors3 = {"Cheese Adam"};
    private String[] authors4 = {"Pepperoni Anna", "Oliver Houston"};
    private String[] authors5 = {"Cheese", "Pepperoni", "Black Olives","Anna Kowalska"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);


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

    }
}
