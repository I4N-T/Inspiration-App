package me.ianterry.inspiration;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    File file = null;
    ArrayList<String> wordList = new ArrayList<String>(500);
    ArrayList<String> keyList = new ArrayList<>();
    Fragment mFragment;
    List<Post> mPostsList;
    FragmentManager mFragManager;
    FragmentTransaction mFragTransaction;

    private Button mInspireButton;
    private TextView mWordTextView;
    private TextView mKeyTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyList.add("C");
        keyList.add("C# / D♭");
        keyList.add("D");
        keyList.add("D# / E♭");
        keyList.add("E");
        keyList.add("F");
        keyList.add("F# / G♭");
        keyList.add("G");
        keyList.add("G# / A♭");
        keyList.add("A");
        keyList.add("A# / B♭");
        keyList.add("B");



        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("wordBank.txt")));
            String line;
            Log.e("Reader Stuff",reader.readLine());
            while ((line = reader.readLine()) != null)
            {
                wordList.add(line);
            }
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        mKeyTextView = findViewById((R.id.key_textview));

        mWordTextView = findViewById(R.id.word_textView);

        mInspireButton = findViewById(R.id.inspire_button);
        mInspireButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int rand = new Random().nextInt(wordList.size());
                String wordToShow = "Theme: " + wordList.get(rand);
                mWordTextView.setText(wordToShow);

                rand = new Random().nextInt(keyList.size());
                String keyToShow = "Key: " + keyList.get(rand);
                mKeyTextView.setText(keyToShow);

                addFragment();
            }
        });
    }

    void addFragment()
    {

        if (mFragment == null)
        {
            mFragment = PostFragment.newInstance("art");
            mFragManager = getSupportFragmentManager();
            mFragTransaction = mFragManager.beginTransaction();
            mFragTransaction.add(R.id.image_view, mFragment).commit();
        }
        else if (mFragment != null)
        {
            mPostsList = PostFragment.posts;
            mFragment = PostFragment.newInstance("art", mPostsList);

            mFragManager = getSupportFragmentManager();
            mFragTransaction = mFragManager.beginTransaction();
            mFragTransaction.replace(R.id.image_view, mFragment).commit();
        }
    }
}
