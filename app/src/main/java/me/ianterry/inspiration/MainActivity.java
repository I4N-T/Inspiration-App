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
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    File file = null;
    ArrayList<String> wordList = new ArrayList<String>(500);
    Fragment mFragment;
    FragmentManager mFragManager;
    FragmentTransaction mFragTransaction;

    private Button mInspireButton;
    private TextView mWordTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //addFragment();

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

        mWordTextView = findViewById(R.id.word_textView);

        mInspireButton = findViewById(R.id.inspire_button);
        mInspireButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int rand = new Random().nextInt(wordList.size());
                String wordToShow = wordList.get(rand);
                mWordTextView.setText(wordToShow);

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
            //getSupportFragmentManager().beginTransaction().add(R.id.image_view, mFragment).commit();
        }
        else
        {
            mFragment = PostFragment.newInstance("art");
            mFragManager = getSupportFragmentManager();
            mFragTransaction = mFragManager.beginTransaction();
            mFragTransaction.replace(R.id.image_view, mFragment).commit();
        }
    }
}
