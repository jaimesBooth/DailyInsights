package com.example.dailyinsight;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * Connectivity proof of concept. Attempts to connect to Facebook via the the
 * devices network connection. The Android Virtual Device network can be toggled 
 * by pressing F8 function key.
 * http://developer.android.com/training/basics/network-ops/connecting.html
 * 
 * @author 16/10/14 Jaimes
 *
 */
public class ConnectivityTest
{
	private static final String DEBUG_TAG = "ConnectivityTest";
	private Context mainContext; // intended as a reference to the mainActivity

	/**
	 * Attempts to download the contents of the Facebook URL via the devices
	 * network connection.
	 * 
	 * @param context
	 *            The context of the activity which called this constructor.
	 */
	public ConnectivityTest(Context context)
	{
		// The context of the activity which called this constructor
		this.mainContext = context;

		// The URL to try to fetch.
		String stringUrl = "https://www.facebook.com";

		// Before attempting to fetch the URL, makes sure that there is a
		// network connection.
		// Check the network connection of the device by referring to the main
		// Activity
		ConnectivityManager connMgr = (ConnectivityManager) mainContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		// If network is available try to download the specified URL
		if (networkInfo != null && networkInfo.isConnected())
		{
			new DownloadWebpageTask().execute(stringUrl);
			// Otherwise notify that connection is not available
		} else
		{
			Toast.makeText(mainContext, "No network connection available.",
					Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Uses AsyncTask to create a task away from the main UI thread. This task
	 * takes a URL string and uses it to create an HttpUrlConnection. Once the
	 * connection has been established, the AsyncTask downloads the contents of
	 * the webpage as an InputStream. Finally, the InputStream is converted into
	 * a string, which is displayed in the UI by the AsyncTask's onPostExecute
	 * method.
	 * 
	 * @author jaimesbooth
	 *         http://developer.android.com/training/basics/network-ops
	 *         /connecting.html
	 */
	private class DownloadWebpageTask extends AsyncTask<String, Void, String>
	{
		@Override
		protected String doInBackground(String... urls)
		{

			// params comes from the execute() call: params[0] is the url.
			try
			{
				return downloadUrl(urls[0]);
			} catch (IOException e)
			{
				return "Unable to retrieve web page. URL may be invalid.";
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result)
		{
			Toast.makeText(mainContext, result, Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * Given a URL, establishes an HttpUrlConnection and retrieves the web page
	 * content as an InputStream, which it returns as a string.
	 * 
	 * @param myurl
	 *            The String representing the URL to download.
	 * @return The string representing the down-loaded content.
	 * @throws IOException
	 */
	private String downloadUrl(String myurl) throws IOException
	{
		InputStream is = null;
		// Only display the first 500 characters of the retrieved
		// web page content.
		int len = 500;

		try
		{
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(10000 /* milliseconds */);
			conn.setConnectTimeout(15000 /* milliseconds */);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			// Starts the query
			conn.connect();
			int response = conn.getResponseCode();
			Log.d(DEBUG_TAG, "The response is: " + response);
			is = conn.getInputStream();

			// Convert the InputStream into a string
			String contentAsString = readIt(is, len);
			return contentAsString;

			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally
		{
			if (is != null)
			{
				is.close();
			}
		}
	}

	/**
	 * Reads an InputStream and converts it to a String.
	 * 
	 * @param stream
	 *            The stream to read and convert.
	 * @param len
	 *            The number of characters of the stream to read.
	 * @return The string of the read stream.
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public String readIt(InputStream stream, int len) throws IOException,
			UnsupportedEncodingException
	{
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

}
