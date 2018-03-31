package me.ianterry.inspiration;

/**
 * Created by Ian on 3/21/2018.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PostFragment extends Fragment
{
    View postView;
    ArrayAdapter<Post> adapter;
    Handler handler;

    String subreddit;
    List<Post> posts;
    PostsHolder postsHolder;

    public PostFragment()
    {
        handler = new Handler();
        posts = new ArrayList<Post>();
    }

    public static Fragment newInstance (String subreddit)
    {
        PostFragment pf = new PostFragment();
        pf.subreddit = subreddit;
        pf.postsHolder = new PostsHolder(pf.subreddit);
        return pf;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.posts, container, false);
        //postView = v.findViewById(R.id.posts_view_object);
        postView = v;
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize()
    {
        if (posts.size() == 0)
        {
            new Thread()
            {
                public void run()
                {
                    posts.addAll(postsHolder.fetchPosts());
                    posts.addAll(postsHolder.fetchMorePosts());
                    posts.addAll(postsHolder.fetchMorePosts());

                    handler.post(new Runnable(){
                        public void run(){
                            createAdapter();  //get post
                        }
                    });
                }
            }.start();
        }
        else
        {
            createAdapter();  //get post
        }
    }

    private void createAdapter()
    {
        if (getActivity() == null)
        {
            return;
        }


        //View convertView = getActivity().getLayoutInflater().inflate(R.layout.posts, null);

        /*ImageView img;
        img = postView.findViewById(R.id.post_image);*/

        WebView imageWebView;
        imageWebView = postView.findViewById(R.id.post_image);

       /* TextView postTitle;
        postTitle = postView.findViewById(R.id.post_title);

        TextView postDetails;
        postDetails = postView.findViewById(R.id.post_details);

        TextView postScore;
        postScore = postView.findViewById(R.id.post_score);*/

        //int rand = new Random().nextInt(posts.size());
        int rand = new Random().nextInt(posts.size());

        /*postTitle.setText(posts.get(rand).getTitle());
        postDetails.setText(posts.get(rand).getImageUrl());
        postScore.setText(posts.get(rand).getScore());*/
        imageWebView.loadUrl(posts.get(rand).getImageUrl());
        //img.setImageBitmap(posts.get(rand).getImageBmp());

        /*adapter = new ArrayAdapter<Post>(getActivity(), R.layout.posts, posts)
        {
            @Override
            public View getView(int position, View convertView, ViewGroup parent)
            {
                if (convertView == null)
                {
                    convertView = getActivity().getLayoutInflater().inflate(R.layout.posts, null);
                }

                TextView postTitle;
                postTitle = convertView.findViewById(R.id.post_title);

                TextView postDetails;
                postDetails = convertView.findViewById(R.id.post_details);

                TextView postScore;
                postScore = convertView.findViewById(R.id.post_score);

                postTitle.setText(posts.get(position).title);
                postDetails.setText(posts.get(position).getDetails());
                postScore.setText(posts.get(position).getScore());
                return convertView;
            }
        };*/
        //int rand = new Random().nextInt(adapter.getCount()); PROBABLY DOESN"T BELONG HERE

        //postView.setAdapter(adapter);
    }


}
