package ro.andreidobrescu.emojilike;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by using on 7/4/2016.
 * shrink/grow emoji animation using weight
 */
public class ViewWeightAnimation extends Animation
{
    private EmojiCellView view;
    private float initialWeight;
    private float currentWeight;
    private float targetWeight;
    private float step;
    private boolean shouldSelect;
    private int index;
    private EmojiConfig config;

    public ViewWeightAnimation(EmojiCellView view, int index, float initialWeight, float targetWeight, float step, boolean shouldSelect, EmojiConfig config)
    {
        this.view = view;
        this.initialWeight = initialWeight;
        currentWeight = initialWeight;
        this.targetWeight = targetWeight;
        this.step = step;
        this.index=index;
        this.shouldSelect = shouldSelect;
        this.config=config;
    }

    public void animate()
    {
        applyTransformation(0f, null);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t)
    {
        currentWeight+=step;
        if (step>0?(currentWeight<targetWeight):(currentWeight>targetWeight))
        {
            int height=shouldSelect?config.selectedEmojiHeight
                    :LinearLayout.LayoutParams.MATCH_PARENT;
            float weight=currentWeight;
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, height, weight);

            int left=shouldSelect?config.selectedEmojiMarginLeft:config.unselectedEmojiMarginLeft;
            int top=shouldSelect?config.selectedEmojiMarginTop:config.unselectedEmojiMarginTop;
            int bottom=shouldSelect?config.selectedEmojiMarginBottom:config.unselectedEmojiMarginBottom;
            int right=shouldSelect?config.selectedEmojiMarginRight:config.unselectedEmojiMarginRight;
            if (index==0)
                params.setMargins(config.emojiViewMarginLeft+left, top, right, bottom);
            else if (index==config.emojis.size()-1)
                params.setMargins(left, top, right+config.emojiViewMarginRight, bottom);
            else params.setMargins(left, top, right, bottom);

            view.setLayoutParams(params);

            if (shouldSelect&&step>0)
            {
                float normalizedCurrentWeight=IntervalConverter
                        .convertNumber(currentWeight)
                        .fromInterval(0, targetWeight)
                        .toInterval(initialWeight, targetWeight);

                float animationPercent=normalizedCurrentWeight/targetWeight;
                view.onWeightAnimated(animationPercent);
            }
            else view.onWeightAnimated(0);
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
