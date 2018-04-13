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
import android.widget.FrameLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> wordList = new ArrayList<String>(500);
    ArrayList<String> keyList = new ArrayList<>();
    Fragment mFragment;
    List<Post> mPostsList;
    FragmentManager mFragManager;
    FragmentTransaction mFragTransaction;
    ConstraintLayout mConstraintLayout;

    private FloatingActionButton mInspireButton;
    private TextView mWordTextView;
    private TextView mKeyTextView;

    private TextView mIntroTextView;
    private FrameLayout mFrameLayout;

    private boolean hasBeenPressed = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createKeyList();

        addFragment();  //begin grabbing images from reddit as soon as app boots

        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("wordBank.txt")));
            String line;
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

                firstPressOnly();

                addFragment();
            }
        });
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

    private void createKeyList()
    {
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
    }

    private void aboutPopUp()
    {
        DialogFragment dialogFrag = AboutDialogClass.newInstance();
        dialogFrag.show(getSupportFragmentManager(), "dialog");
    }

    private void firstPressOnly()
    {
        if (!hasBeenPressed)
        {
            mIntroTextView.setVisibility(View.INVISIBLE);  //hide intro text
            mFrameLayout.setVisibility(View.VISIBLE);  //make framelayout w/ webview visible

            //change button position
            ConstraintSet set = new ConstraintSet();
            set.clone(mConstraintLayout);
            set.centerVertically(R.id.fab, R.id.fragments_holder);
            set.setVerticalBias(R.id.fab, 0.985f);
            set.applyTo(mConstraintLayout);
            hasBeenPressed = true;
        }
    }
    private void addFragment()
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
