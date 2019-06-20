package ro.andreidobrescu.sample.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.andreidobrescu.emojilike.Emoji;
import ro.andreidobrescu.emojilike.EmojiConfig;
import ro.andreidobrescu.emojilike.EmojiLikeView;
import ro.andreidobrescu.sample.R;

/**
 * Created by using on 7/4/2016.
 */
public class FragmentSample extends Fragment
{
    @BindView(R.id.likeButton)
    ImageView likeButton;

    @BindView(R.id.emojiView)
    EmojiLikeView emojiView;

    @BindView(R.id.likeButton2)
    ImageView likeButton2;

    @BindView(R.id.emojiView2)
    EmojiLikeView emojiView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_sample, container, false);

        ButterKnife.bind(this, view);

        EmojiConfig.with(getContext())
                .on(likeButton)
                .open(emojiView)
                .addEmoji(new Emoji(R.drawable.like, "Like"))
                .addEmoji(new Emoji(R.drawable.haha, "Haha"))
                .addEmoji(new Emoji(R.drawable.kiss, "Kiss"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.p, ":P"))
                .setOnEmojiSelectedListener(emoji ->
                {
                    Toast.makeText(getContext(), "Selected center " + emoji.getDescription(), Toast.LENGTH_SHORT).show();
                })
                .setup();

        EmojiConfig.with(getContext())
                .on(likeButton2)
                .open(emojiView2)
                .addEmoji(new Emoji(R.drawable.like, "Like"))
                .addEmoji(new Emoji(R.drawable.haha, "Haha"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.p, ":P"))
                .setOnEmojiSelectedListener(emoji ->
                {
                    Toast.makeText(getContext(), "Selected bottom " + emoji.getDescription(), Toast.LENGTH_SHORT).show();
                })
                .setup();

        return view;
    }

    private int dpToPx(int i)
    {
        Resources r = getContext ().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics());
        return (int)px;
    }
}
