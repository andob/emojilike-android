package ro.andreidobrescu.sample.fragments;

import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

import ro.andreidobrescu.emojilike.ActivityWithEmoji;
import ro.andreidobrescu.sample.R;

/**
 * Created by using on 7/4/2016.
 */
public class FragmentActivitySample extends ActivityWithEmoji
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_sample);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, new FragmentSample())
                .commit();
    }
}
