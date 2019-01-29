package uk.co.beevorwhite.soiree.Utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import uk.co.beevorwhite.soiree.model.Keys;

import static uk.co.beevorwhite.soiree.model.Keys.API_ID;
import static uk.co.beevorwhite.soiree.model.Keys.API_KEY;
import static uk.co.beevorwhite.soiree.model.Keys.APP_ID;
import static uk.co.beevorwhite.soiree.model.Keys.APP_KEY;
import static uk.co.beevorwhite.soiree.model.Keys.SEARCH_QUERY;

public class NetworkUtils {

    public static URL queryUrl(String query) {

        String combinedUri = Keys.BASE_URL + SEARCH_QUERY + query + APP_ID + API_ID + APP_KEY + API_KEY;
        Uri builtUri = Uri.parse(combinedUri).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {

            InputStream inputStream = urlConnection.getInputStream();
            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
