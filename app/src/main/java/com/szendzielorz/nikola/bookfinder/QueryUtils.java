package com.szendzielorz.nikola.bookfinder;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Nicole on 25.10.2017.
 */

public final class QueryUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    // Base URI for the Books API
    //private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM ="q"; //parameter for the search string
    private static final String MAX_RESULTS ="maxResults"; //parameter that limits search results
    //private static final String PRINT_TYPE ="printType"; //Parameter to filter by print type



    private QueryUtils(){}

    public static ArrayList<Book> fetchBookData(String requestUrl, String searchKeyword){

        // For progress bar testing purposes.
        /*try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //Log.i(LOG_TAG, "fetchBookData method");
        // Create URL object
        URL url = createUrl(requestUrl, searchKeyword);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<Book> books = extractBooks(jsonResponse);

        // Return the {@link Event}
        return books;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl, String searchKeyword) {
        URL url = null;
        try {
            //url = new URL(stringUrl);
            // Build up query URI, limiting results to 15 items
            Uri buildUri = Uri.parse(stringUrl).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,searchKeyword)
                    .appendQueryParameter(MAX_RESULTS,"15")
                    .build();

            url = new URL(buildUri.toString());

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException{

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /*Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
    */
    public static ArrayList<Book> extractBooks(String bookJSON){

        // Create an empty ArrayList that we can start adding books to
        ArrayList<Book> books = new ArrayList<>();

        // Try to parse the JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject root = new JSONObject(bookJSON);
            JSONArray items = root.getJSONArray("items");

            for (int i=0; i<items.length();i++){
                //  Parse the response given by the JSON_RESPONSE string and
                // build up a list of Book objects with the corresponding data.

                JSONObject item = items.getJSONObject(i);
                JSONObject volumeInfo = item.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");

                String[] authorNames = new String[]{"Anonymous/No data"};
                JSONArray authors = volumeInfo.getJSONArray("authors");
                int length = authors.length();
                if(length>0){
                    authorNames = new String[length];
                    for(int j =0;j<length;j++){
                        authorNames[j] = authors.getString(j);
                    }
                }

                String publishedDate="";
                if (volumeInfo.has("publishedDate")) {

                    publishedDate = volumeInfo.getString("publishedDate");
                    //TODO: Find also image of book.

                }

                Book book = new Book(title,authorNames,publishedDate);
                books.add(book);

            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }


        return books;
    }

}
