package ro.andreidobrescu.emojilike;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import ro.andreidobrescu.emojilikelib.R;

public abstract class EmojiCellView extends FrameLayout
{
    public EmojiCellView(@NonNull Context context)
    {
        super(context);

        LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(getLayoutId(), this, true);
    }

    public abstract int getLayoutId();
    public abstract void setEmoji(Emoji emoji);
    public void onWeightAnimated(float animationPercent) {}

    public static class WithImage extends EmojiCellView
    {
        public WithImage(@NonNull Context context)
        {
            super(context);
        }

        @Override
        public int getLayoutId()
        {
            return R.layout.emoji_cell_with_image;
        }

        @Override
        public void setEmoji(Emoji emoji)
        {
            ImageView imageView=findViewById(R.id.imageView);
            imageView.setImageResource(emoji.getDrawable());
        }
    }

    public static class WithImageAndText extends EmojiCellView
    {
        public WithImageAndText(@NonNull Context context)
        {
            super(context);
        }

        @Override
        public int getLayoutId()
        {
            return R.layout.emoji_cell_with_image_and_text;
        }

        @Override
        public void setEmoji(Emoji emoji)
        {
            ImageView imageView=findViewById(R.id.imageView);
            TextView descriptionLabel=findViewById(R.id.descriptionLabel);

            imageView.setImageResource(emoji.getDrawable());
            descriptionLabel.setText(emoji.getDescription());
        }

        @Override
        public void onWeightAnimated(float animationPercent)
        {
            TextView descriptionLabel=findViewById(R.id.descriptionLabel);
            descriptionLabel.setAlpha(animationPercent);

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    (int)(animationPercent*getResources().getDimensionPixelSize(R.dimen.default_emoji_description_label_height)));
            params.bottomMargin=getResources().getDimensionPixelSize(R.dimen.default_emoji_description_label_bottom_margin);
            params.gravity=Gravity.CENTER_HORIZONTAL;
            descriptionLabel.setLayoutParams(params);
        }
    }
}
