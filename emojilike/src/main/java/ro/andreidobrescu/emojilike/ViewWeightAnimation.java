package ro.andreidobrescu.emojilike;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by using on 7/4/2016.
 * shrink/grow emoji animation using weight
 */
public class ViewWeightAnimation extends Animation
{
    ImageView view;
    float initWeight;
    float currWeight;
    float maxWeight;
    float step;
    boolean isSelected;
    Context context;
    int index;
    EmojiConfig config;

    public ViewWeightAnimation(Context context, ImageView view, int index, float initWeight, float maxWeight, float step, boolean forSelected, EmojiConfig config)
    {
        this.context=context;
        this.view = view;
        this.initWeight = initWeight;
        currWeight=initWeight;
        this.maxWeight = maxWeight;
        this.step = step;
        this.index=index;
        this.isSelected = forSelected;
        this.config=config;
    }

    public void doIt  ()
    {
        applyTransformation(0f, null);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        currWeight+=step;
        if (step>0?(currWeight<maxWeight):(currWeight>maxWeight))
        {
            int h=isSelected?config.selectedEmojiHeight
                    :LinearLayout.LayoutParams.MATCH_PARENT;
            float weight=currWeight;
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, h, weight);

            int left=isSelected?config.selectedEmojiMarginLeft:config.unselectedEmojiMarginLeft;
            int top=isSelected?config.selectedEmojiMarginTop:config.unselectedEmojiMarginTop;
            int bottom=isSelected?config.selectedEmojiMarginBottom:config.unselectedEmojiMarginBottom;
            int right=isSelected?config.selectedEmojiMarginRight:config.unselectedEmojiMarginRight;
            if (index==0)
                params.setMargins(config.emojiViewMarginLeft+left, top, right, bottom);
            else if (index==config.emojis.size()-1)
                params.setMargins(left, top, right+config.emojiViewMarginRight, bottom);
            else
                params.setMargins(left, top, right, bottom);

            view.setLayoutParams(params);
        }
        else
        {
            cancel();
        }
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}
