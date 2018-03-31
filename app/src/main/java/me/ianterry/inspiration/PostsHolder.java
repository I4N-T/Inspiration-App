package me.ianterry.inspiration;

/**
 * Created by Ian on 3/21/2018.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class PostsHolder
{
    private final String URL_TEMPLATE = "https://www.reddit.com/r/SUBREDDIT_NAME/" + ".json" + "?after=AFTER";

    String subreddit;
    String url;
    String after;

    PostsHolder(String sr)
    {
        subreddit = sr;
        after = "";
        generateURL();
    }

    private void generateURL()
    {
        url = URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url = url.replace("AFTER", after);
        System.out.println("this: " + url);
    }

    List<Post> fetchPosts()
    {
        String raw = RemoteData.readContents(url);
        System.out.println(raw);
        List<Post> list = new ArrayList<Post>();
        try
        {
            JSONObject data = new JSONObject(raw).getJSONObject("data");
            JSONArray children = data.getJSONArray("children");

            after = data.getString("after");

            for (int i = 0; i < children.length(); i++)
            {
                JSONObject cur = children.getJSONObject(i).getJSONObject("data");
                JSONObject previewObj = cur.optJSONObject("preview");
                JSONArray imagesObj = previewObj.optJSONArray("images");
                JSONArray sourceObj = imagesObj.getJSONObject(0).optJSONArray("resolutions");
                JSONObject resolution = sourceObj.getJSONObject(2);

                Post p = new Post();
                p.title = cur.optString("title");
                p.url = cur.optString("url");
                p.numComments = cur.optInt("numComments");
                p.points = cur.optInt("score");
                p.author = cur.optString("author");
                p.subreddit = cur.optString("subreddit");
                p.permalink = cur.optString("permalink");
                p.domain = cur.optString("domain");
                p.id = cur.optString("id");
                //p.imageUrl = cur.optString("thumbnail");
                p.imageUrl = resolution.optString("url").replace("&amp;", "&");

                //URL imgUrl = new URL(p.imageUrl);

               // p.imageBmp = BitmapFactory.decodeStream(input);


                if(p.title != null)
                {
                    list.add(p);
                }
            }
        }
        catch (Exception e)
        {
            Log.e("fetchPosts()", e.toString());
        }
        return list;
    }

    List<Post> fetchMorePosts()
    {
        generateURL();
        return fetchPosts();
    }
}
