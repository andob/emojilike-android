package ro.andreidobrescu.sample.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.andreidobrescu.emojilike.Emoji;
import ro.andreidobrescu.emojilike.EmojiConfig;
import ro.andreidobrescu.emojilike.EmojiLikeView;
import ro.andreidobrescu.emojilike.OnEmojiSelectedListener;
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

        EmojiConfig config=EmojiConfig.with(getContext())
                .on(likeButton)
                .addEmoji(new Emoji(R.drawable.like, "Like"))
                .addEmoji(new Emoji(R.drawable.haha, "Haha"))
                .addEmoji(new Emoji(R.drawable.kiss, "Kiss"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.t, ":P"));

        config.setOnEmojiSelectedListener(new OnEmojiSelectedListener() {
            @Override
            public void onEmojiSelected(Emoji emoji) {
                Toast.makeText(getContext(), "Selected center " + emoji.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });

        config.setEmojiViewInAnimation((AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.in_animation));
        config.setEmojiViewOutAnimation((AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.out_animation));
        config.setBackgroundImage(R.drawable.background_drawable);
        config.setInitialSelectedEmoji(4);
        config.setup(emojiView);

        EmojiConfig config2=EmojiConfig.with(getContext())
                .on(likeButton2)
                .addEmoji(new Emoji(R.drawable.like, "Like"))
                .addEmoji(new Emoji(R.drawable.haha, "Haha"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.sad, "Sad"))
                .addEmoji(new Emoji(R.drawable.t, ":P"));

        config2.setOnEmojiSelectedListener(new OnEmojiSelectedListener() {
            @Override
            public void onEmojiSelected(Emoji emoji) {
                Toast.makeText(getContext(), "Selected bottom " + emoji.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });

        config2.setEmojiViewInAnimation((AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.in_animation));
        config2.setEmojiViewOutAnimation((AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.out_animation));
        config2.setBackgroundImage(R.drawable.background_drawable);
        config2.setup(emojiView2);

        return view;
    }
}
