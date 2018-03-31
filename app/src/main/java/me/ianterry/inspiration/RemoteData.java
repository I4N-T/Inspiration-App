package me.ianterry.inspiration;

/**
 * Created by Ian on 3/19/2018.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;

public class RemoteData
{
    public static HttpURLConnection getConnection(String url)
    {
        System.out.println("URL: " + url);
        HttpURLConnection hcon1 = null;
        try
        {
            URL u = new URL(url);
            hcon1 = (HttpURLConnection)u.openConnection();
            hcon1.setReadTimeout(30000);
            //hcon.setRequestProperty("User-Agent", "Alien V1.0");
        }
        catch (MalformedURLException e)
        {
            Log.e("getConnection()", "Invalid URL: " + e.toString());
        }
        catch (IOException e)
        {
            Log.e("getConnection()", "Could not connect: " + e.toString());
        }
        return hcon1;
    }

    public static String readContents(String url)
    {
        HttpURLConnection hcon = getConnection(url);

        if (hcon == null)
        {
            return null;
        }
        try
        {
            System.out.println(hcon.getResponseCode());
            StringBuffer sb = new StringBuffer(8192);
            //StringBuilder sb = new StringBuilder(8192);
            String tmp = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(hcon.getInputStream()));
            while ((tmp = br.readLine()) != null)
            {
                sb.append(tmp).append("\n");
            }
            //System.out.println("OK THEN" + sb.toString());
            br.close();
            return sb.toString();
        }
        catch (IOException e)
        {
            Log.d("READ FAILED", e.toString());
            return null;
        }
    }
}
