package me.ianterry.inspiration;

import android.graphics.Bitmap;

/**
 * Created by Ian on 3/21/2018.
 */

public class Post
{
    String subreddit;
    String title;
    String author;
    int points;
    int numComments;
    String permalink;
    String url;
    String domain;
    String id;
    String imageUrl;
    Bitmap imageBmp;

    String getDetails()
    {
        String details = author + " posted this and got " + numComments + " replies.";
        return details;
    }

    String getTitle()
    {
        return title;
    }

    String getScore()
    {
        return Integer.toString(points);
    }

    Bitmap getImageBmp()
    {
        return imageBmp;
    }

    String getImageUrl()
    {
        return imageUrl;
    }
}
