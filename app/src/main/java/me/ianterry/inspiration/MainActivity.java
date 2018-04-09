package me.ianterry.inspiration;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
    ConstraintLayout mConstraintLayout;

   // private Button mInspireButton;
    private FloatingActionButton mInspireButton;
    private TextView mWordTextView;
    private TextView mKeyTextView;

    private TextView mIntroTextView;
    private FrameLayout mFrameLayout;



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

        mIntroTextView = findViewById(R.id.intro_textview);

        mFrameLayout = findViewById(R.id.image_view);

        mConstraintLayout = findViewById(R.id.fragments_holder);

        mInspireButton = findViewById(R.id.fab);
        mInspireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                int rand = new Random().nextInt(wordList.size());
                String wordToShow = "Theme: " + wordList.get(rand);
                mWordTextView.setText(wordToShow);

                rand = new Random().nextInt(keyList.size());
                String keyToShow = "Key: " + keyList.get(rand);
                mKeyTextView.setText(keyToShow);

                mIntroTextView.setVisibility(View.INVISIBLE); //make this so it only happens on first button press
                mFrameLayout.setVisibility(View.VISIBLE);
                addFragment();
            }
        });
        /*mInspireButton = findViewById(R.id.inspire_button);
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
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                aboutPopUp();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void aboutPopUp()
    {
        DialogFragment dialogFrag = AboutDialogClass.newInstance();
        dialogFrag.show(getSupportFragmentManager(), "dialog");
    }

    void addFragment()
    {

        if (mFragment == null)
        {
            //change button position
            ConstraintSet set = new ConstraintSet();
            set.clone(mConstraintLayout);
            set.centerVertically(R.id.fab, R.id.fragments_holder);
            set.setVerticalBias(R.id.fab, 0.985f);
            set.applyTo(mConstraintLayout);

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
