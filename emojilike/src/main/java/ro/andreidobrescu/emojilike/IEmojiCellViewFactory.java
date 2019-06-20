package ro.andreidobrescu.emojilike;

import android.content.Context;

public interface IEmojiCellViewFactory
{
    public EmojiCellView newInstance(Context context);
}
