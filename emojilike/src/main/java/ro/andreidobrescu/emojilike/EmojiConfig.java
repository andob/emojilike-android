package ro.andreidobrescu.emojilike;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by using on 7/4/2016.
 * Use this class to configure the emoji like view
 */
public class EmojiConfig
{
    IActivityWithEmoji target;

    View triggerView;

    int touchDownDelay;

    int touchUpDelay;

    int emojiImagesContainerHeight;

    int backgroundViewHeight;

    int selectedEmojiHeight;

    int selectedEmojiWeight;

    int emojiViewMarginLeft;

    int emojiViewMarginRight;

    int selectedEmojiMarginBottom;

    int selectedEmojiMarginTop;

    int selectedEmojiMarginLeft;

    int selectedEmojiMarginRight;

    int unselectedEmojiMarginBottom;

    int unselectedEmojiMarginTop;

    int unselectedEmojiMarginLeft;

    int unselectedEmojiMarginRight;

    int unselectedEmojiWeight;

    Animation emojiViewInAnimation;

    Animation emojiViewOutAnimation;

    int initialSelectedEmoji;

    List <Emoji> emojis;

    OnEmojiSelectedListener onEmojiSelectedListener;

    EmojiLikeView emojiView;

    int backgroundImage;

    int backgroundViewMarginBottom;

    private EmojiConfig(IActivityWithEmoji target)
    {
        this.target = target;
        touchDownDelay=100;
        touchUpDelay=500;
        backgroundImage =0;
        initialSelectedEmoji=0;
        selectedEmojiHeight=dpToPx (85);
        selectedEmojiWeight=4;
        unselectedEmojiWeight=1;
        emojiViewMarginLeft=dpToPx(15);
        emojiViewMarginRight=dpToPx(15);
        selectedEmojiMarginBottom=dpToPx(15);
        selectedEmojiMarginTop=0;
        selectedEmojiMarginLeft=dpToPx(15);
        selectedEmojiMarginRight=dpToPx(15);
        unselectedEmojiMarginBottom=0;
        unselectedEmojiMarginTop=dpToPx(30);
        unselectedEmojiMarginLeft=dpToPx(15);
        unselectedEmojiMarginRight=dpToPx(15);
        emojiImagesContainerHeight=dpToPx(100);
        backgroundViewHeight=dpToPx(50);
        backgroundViewMarginBottom=dpToPx(10);
    }

    private Context getContext ()
    {
        if (target instanceof IActivityWithEmoji)
            return (Context)target;
        return null;
    }

    private int dpToPx(int i)
    {
        Resources r = getContext ().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, r.getDisplayMetrics());
        return (int)px;
    }

    public static EmojiConfig with (ActivityWithEmoji target)
    {
        return new EmojiConfig(target);
    }

    public static EmojiConfig with (Context target)
    {
        if (target instanceof IActivityWithEmoji)
            return new EmojiConfig((IActivityWithEmoji)target);
        throw new EmojiException("please implement IActivityWithEmoji on your activity");
    }

    public EmojiConfig on (View triggerView)
    {
        this.triggerView=triggerView;
        return this;
    }

    public EmojiConfig setEmojis (List<Emoji> emojis)
    {
        this.emojis=emojis;
        return this;
    }

    public EmojiConfig addEmoji(Emoji emoji)
    {
        if (this.emojis==null)
            this.emojis=new LinkedList<>();
        this.emojis.add(emoji);
        return this;
    }

    public void setOnEmojiSelectedListener (OnEmojiSelectedListener listener)
    {
        this.onEmojiSelectedListener=listener;
    }

    /**
     * set the time delay from the touch down event and the moment the emoji view is showed
     * */
    public void setTouchDownDelay(int touchDownDelay) {
        this.touchDownDelay = touchDownDelay;
    }

    /**
     * set the time delay between the touch up event and the moment the emoji view is hided
     * */
    public void setTouchUpDelay(int touchUpDelay) {
        this.touchUpDelay = touchUpDelay;
    }

    public void setInitialSelectedEmoji(int initialSelectedEmoji) {
        this.initialSelectedEmoji = initialSelectedEmoji;
    }

    public void setSelectedEmojiHeight(int selectedEmojiHeight) {
        this.selectedEmojiHeight = selectedEmojiHeight;
    }

    public void setSelectedEmojiWeight(int selectedEmojiWeight) {
        this.selectedEmojiWeight = selectedEmojiWeight;
    }

    public void setEmojiViewMarginLeft(int emojiViewMarginLeft) {
        this.emojiViewMarginLeft = emojiViewMarginLeft;
    }

    public void setEmojiViewMarginRight(int emojiViewMarginRight) {
        this.emojiViewMarginRight = emojiViewMarginRight;
    }

    public void setSelectedEmojiMarginBottom(int selectedEmojiMarginBottom) {
        this.selectedEmojiMarginBottom = selectedEmojiMarginBottom;
    }

    public void setSelectedEmojiMarginTop(int selectedEmojiMarginTop) {
        this.selectedEmojiMarginTop = selectedEmojiMarginTop;
    }

    public void setSelectedEmojiMarginLeft(int selectedEmojiMarginLeft) {
        this.selectedEmojiMarginLeft = selectedEmojiMarginLeft;
    }

    public void setSelectedEmojiMarginRight(int selectedEmojiMarginRight) {
        this.selectedEmojiMarginRight = selectedEmojiMarginRight;
    }

    public void setUnselectedEmojiMarginBottom(int unselectedEmojiMarginBottom) {
        this.unselectedEmojiMarginBottom = unselectedEmojiMarginBottom;
    }

    public void setUnselectedEmojiMarginTop(int unselectedEmojiMarginTop) {
        this.unselectedEmojiMarginTop = unselectedEmojiMarginTop;
    }

    public void setUnselectedEmojiMarginLeft(int unselectedEmojiMarginLeft) {
        this.unselectedEmojiMarginLeft = unselectedEmojiMarginLeft;
    }

    public void setUnselectedEmojiMarginRight(int unselectedEmojiMarginRight) {
        this.unselectedEmojiMarginRight = unselectedEmojiMarginRight;
    }

    public void setUnselectedEmojiWeight(int unselectedEmojiWeight) {
        this.unselectedEmojiWeight = unselectedEmojiWeight;
    }

    public void setEmojiImagesContainerHeight(int emojiImagesContainerHeight) {
        this.emojiImagesContainerHeight = emojiImagesContainerHeight;
    }

    public void setBackgroundViewHeight(int backgroundViewHeight) {
        this.backgroundViewHeight = backgroundViewHeight;
    }

    public void setBackgroundImage(int backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public void setBackgroundViewMarginBottom(int backgroundViewMarginBottom) {
        this.backgroundViewMarginBottom = backgroundViewMarginBottom;
    }

    public void setEmojiViewInAnimation(Animation emojiViewInAnimation) {
        this.emojiViewInAnimation = emojiViewInAnimation;
    }

    public void setEmojiViewOutAnimation(Animation emojiViewOutAnimation) {
        this.emojiViewOutAnimation = emojiViewOutAnimation;
    }

    public void setup (EmojiLikeView emojiView)
    {
        this.emojiView=emojiView;

        if (target==null)
            throw new EmojiException("Target not set. Set it with EmojiConfig.with(target)");
        else if (triggerView==null)
            throw new EmojiException("Trigger view not set. Do it with EmojiConfig.with(target).on(triggerView)");
        else if (emojis==null)
            throw new EmojiException("Emojis not set");
        else if (emojis.size()<=1)
            throw new EmojiException("Please add more emojis");
        else
        {
            target.configureEmojiLike(this);
            emojiView.configure(this);
        }
    }
}
