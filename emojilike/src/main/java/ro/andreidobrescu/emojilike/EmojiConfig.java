package ro.andreidobrescu.emojilike;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.LinkedList;
import java.util.List;

import ro.andreidobrescu.emojilikelib.R;

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

    Animation emojiViewOpenedAnimation;

    Animation emojiViewClosedAnimation;

    List <Emoji> emojis;

    OnEmojiSelectedListener onEmojiSelectedListener;

    EmojiLikeView emojiView;

    int backgroundImageResource;

    int backgroundViewMarginBottom;

    float emojiAnimationSpeed;

    IEmojiCellViewFactory cellViewFactory;

    private EmojiConfig(IActivityWithEmoji target)
    {
        this.target = target;
        touchDownDelay=100;
        touchUpDelay=500;
        backgroundImageResource =0;
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
        emojiImagesContainerHeight=dpToPx(130);
        backgroundViewHeight=dpToPx(50);
        backgroundViewMarginBottom=dpToPx(10);
        emojiAnimationSpeed=0.2f;
        emojiViewOpenedAnimation =AnimationUtils.loadAnimation(getContext(), R.anim.emoji_default_in_animation);
        emojiViewClosedAnimation =AnimationUtils.loadAnimation(getContext(), R.anim.emoji_default_out_animation);
        backgroundImageResource=R.drawable.emoji_default_background_drawable;
        cellViewFactory=EmojiCellView.WithImage::new;
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

    public EmojiConfig setOnEmojiSelectedListener (OnEmojiSelectedListener listener)
    {
        this.onEmojiSelectedListener=listener;
        return this;
    }

    /**
     * set the time delay from the touch down event and the moment the emoji view is showed
     * */
    public EmojiConfig setTouchDownDelay(int touchDownDelay) {
        this.touchDownDelay = touchDownDelay;
        return this;
    }

    /**
     * set the time delay between the touch up event and the moment the emoji view is hided
     * */
    public EmojiConfig setTouchUpDelay(int touchUpDelay) {
        this.touchUpDelay = touchUpDelay;
        return this;
    }

    public EmojiConfig setSelectedEmojiHeight(int selectedEmojiHeight) {
        this.selectedEmojiHeight = selectedEmojiHeight;
        return this;
    }

    public EmojiConfig setSelectedEmojiWeight(int selectedEmojiWeight) {
        this.selectedEmojiWeight = selectedEmojiWeight;
        return this;
    }

    public EmojiConfig setEmojiViewMarginLeft(int emojiViewMarginLeft) {
        this.emojiViewMarginLeft = emojiViewMarginLeft;
        return this;
    }

    public EmojiConfig setEmojiViewMarginRight(int emojiViewMarginRight) {
        this.emojiViewMarginRight = emojiViewMarginRight;
        return this;
    }

    public EmojiConfig setSelectedEmojiMarginBottom(int selectedEmojiMarginBottom) {
        this.selectedEmojiMarginBottom = selectedEmojiMarginBottom;
        return this;
    }

    public EmojiConfig setSelectedEmojiMarginTop(int selectedEmojiMarginTop) {
        this.selectedEmojiMarginTop = selectedEmojiMarginTop;
        return this;
    }

    public EmojiConfig setSelectedEmojiMarginLeft(int selectedEmojiMarginLeft) {
        this.selectedEmojiMarginLeft = selectedEmojiMarginLeft;
        return this;
    }

    public EmojiConfig setSelectedEmojiMarginRight(int selectedEmojiMarginRight) {
        this.selectedEmojiMarginRight = selectedEmojiMarginRight;
        return this;
    }

    public EmojiConfig setUnselectedEmojiMarginBottom(int unselectedEmojiMarginBottom) {
        this.unselectedEmojiMarginBottom = unselectedEmojiMarginBottom;
        return this;
    }

    public EmojiConfig setUnselectedEmojiMarginTop(int unselectedEmojiMarginTop) {
        this.unselectedEmojiMarginTop = unselectedEmojiMarginTop;
        return this;
    }

    public EmojiConfig setUnselectedEmojiMarginLeft(int unselectedEmojiMarginLeft) {
        this.unselectedEmojiMarginLeft = unselectedEmojiMarginLeft;
        return this;
    }

    public EmojiConfig setUnselectedEmojiMarginRight(int unselectedEmojiMarginRight) {
        this.unselectedEmojiMarginRight = unselectedEmojiMarginRight;
        return this;
    }

    public EmojiConfig setUnselectedEmojiWeight(int unselectedEmojiWeight) {
        this.unselectedEmojiWeight = unselectedEmojiWeight;
        return this;
    }

    public EmojiConfig setEmojiImagesContainerHeight(int emojiImagesContainerHeight) {
        this.emojiImagesContainerHeight = emojiImagesContainerHeight;
        return this;
    }

    public EmojiConfig setEmojiAnimationSpeed(float emojiAnimationSpeed) {
        this.emojiAnimationSpeed = emojiAnimationSpeed;
        return this;
    }

    public EmojiConfig setBackgroundViewHeight(int backgroundViewHeight) {
        this.backgroundViewHeight = backgroundViewHeight;
        return this;
    }

    public EmojiConfig setBackgroundImageResource(int backgroundImageResource) {
        this.backgroundImageResource = backgroundImageResource;
        return this;
    }

    public EmojiConfig setBackgroundViewMarginBottom(int backgroundViewMarginBottom) {
        this.backgroundViewMarginBottom = backgroundViewMarginBottom;
        return this;
    }

    public EmojiConfig setEmojiViewOpenedAnimation(Animation emojiViewOpenedAnimation) {
        this.emojiViewOpenedAnimation = emojiViewOpenedAnimation;
        return this;
    }

    public EmojiConfig setEmojiViewClosedAnimation(Animation emojiViewClosedAnimation) {
        this.emojiViewClosedAnimation = emojiViewClosedAnimation;
        return this;
    }

    public EmojiConfig setEmojiCellViewFactory(IEmojiCellViewFactory factory)
    {
        this.cellViewFactory=factory;
        return this;
    }

    public EmojiConfig open (EmojiLikeView emojiView)
    {
        this.emojiView=emojiView;
        return this;
    }

    public void setup ()
    {
        if (target==null)
            throw new EmojiException("Target not set. Set it with EmojiConfig.with(target)");
        else if (triggerView==null)
            throw new EmojiException("Trigger view not set. Do it with EmojiConfig.with(target).on(triggerView)");
        else if (emojis==null)
            throw new EmojiException("Emojis not set");
        else if (emojis.size()<=1)
            throw new EmojiException("Please add more emojis");
        else if (emojiView==null)
            throw new EmojiException("EmojiLikeView not set. Use open method.");
        else
        {
            target.configureEmojiLike(this);
            emojiView.configure(this);
        }
    }
}
