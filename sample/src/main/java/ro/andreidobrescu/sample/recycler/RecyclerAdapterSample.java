package ro.andreidobrescu.sample.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ro.andreidobrescu.emojilike.Emoji;
import ro.andreidobrescu.emojilike.EmojiConfig;
import ro.andreidobrescu.emojilike.EmojiLikeView;
import ro.andreidobrescu.emojilike.OnEmojiSelectedListener;
import ro.andreidobrescu.sample.R;

/**
 * Created by using on 7/4/2016.
 */
public class RecyclerAdapterSample extends RecyclerView.Adapter <RecyclerAdapterSample.RecyclerHolderSample>
{
    Context context;

    public RecyclerAdapterSample(Context context)
    {
        this.context = context;
    }

    public Context getContext()
    {
        return context;
    }

    @Override
    public int getItemCount()
    {
        return 100;
    }

    @Override
    public RecyclerHolderSample onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_recycler_sample, parent, false);
        return new RecyclerHolderSample(v);
    }

    @Override
    public void onBindViewHolder(RecyclerHolderSample holder, int position)
    {
        holder.textView.setText("nice card "+position);

        EmojiConfig config=EmojiConfig.with(getContext())
                .on(holder.likeButton)
                .addEmoji(new Emoji(R.drawable.like, "Like", position))
                .addEmoji(new Emoji(R.drawable.haha, "Haha", position))
                .addEmoji(new Emoji(R.drawable.kiss, "Kiss", position))
                .addEmoji(new Emoji(R.drawable.sad, "Sad", position))
                .addEmoji(new Emoji(R.drawable.t, ":P", position));

        config.setOnEmojiSelectedListener(new OnEmojiSelectedListener() {
            @Override
            public void onEmojiSelected(Emoji emoji) {
                Toast.makeText(getContext(), emoji.getDescription()+" "+emoji.getTag().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        config.setEmojiViewInAnimation((AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.in_animation));
        config.setEmojiViewOutAnimation((AnimationSet) AnimationUtils.loadAnimation(getContext(), R.anim.out_animation));
        config.setBackgroundImage(R.drawable.background_drawable);
        config.setup(holder.emojiView);
    }

    public static class RecyclerHolderSample extends RecyclerView.ViewHolder
    {
        public TextView textView;
        public ImageView likeButton;
        public EmojiLikeView emojiView;

        public RecyclerHolderSample(View itemView)
        {
            super(itemView);

            textView=(TextView)itemView.findViewById(R.id.textView);
            likeButton=(ImageView)itemView.findViewById(R.id.likeButton);
            emojiView=(EmojiLikeView)itemView.findViewById(R.id.emojiView);
        }
    }
}
