package ro.andreidobrescu.emojilike;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by using on 7/4/2016.
 */
public class EmojiLikeView extends RelativeLayout
{
    ImageView emojiBackgroundView;

    LinearLayout emojiImagesContainer;

    List<ImageView> emojiImageViews;

    int selectedEmoji=0;

    EmojiConfig config;

    public EmojiLikeView(Context context)
    {
        super(context);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    public EmojiLikeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    public EmojiLikeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        this.setBackgroundColor(Color.TRANSPARENT);
    }

    //used for the initial setup
    public void configure (EmojiConfig config)
    {
        this.config=config;
        this.emojiImageViews=new LinkedList<>();
        this.selectedEmoji=-1;

        if (config.backgroundImage!=0)
        {
            //creating the background image view
            emojiBackgroundView=new ImageView(getContext());

            emojiBackgroundView.setImageResource(config.backgroundImage);
            emojiBackgroundView.setScaleType(ImageView.ScaleType.FIT_XY);

            RelativeLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, config.backgroundViewHeight);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            params.setMargins(config.emojiViewMarginLeft, 0, config.emojiViewMarginRight, config.backgroundViewMarginBottom);
            emojiBackgroundView.setLayoutParams(params);

            emojiBackgroundView.setVisibility(View.INVISIBLE);
            this.addView(emojiBackgroundView);
        }

        //creating the container for emoji imageviews
        this.emojiImagesContainer=new LinearLayout(getContext());
        RelativeLayout.LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, config.emojiImagesContainerHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.emojiImagesContainer.setLayoutParams(params);
        this.emojiImagesContainer.setVisibility(View.INVISIBLE);
        this.addView(emojiImagesContainer);

        for (int i=0; i<config.emojis.size(); i++)
        {
            //creating emoji imageviews
            Emoji emoji=config.emojis.get(i);

            ImageView emojiImage=new ImageView(getContext());
            emojiImage.setLayoutParams(getDefaultLayoutParams(i));
            emojiImage.setImageResource(emoji.getDrawable());

            this.emojiImagesContainer.addView(emojiImage);
            this.emojiImageViews.add(emojiImage);
        }

        this.bringToFront();
    }

    //default layoutparams for an emoji imageview
    private LinearLayout.LayoutParams getDefaultLayoutParams (int viewIndex)
    {
        int h=LinearLayout.LayoutParams.MATCH_PARENT;
        float weight=config.unselectedEmojiWeight;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, h, weight);

        int left=config.unselectedEmojiMarginLeft;
        int top=config.unselectedEmojiMarginTop;
        int bottom=config.unselectedEmojiMarginBottom;
        int right=config.unselectedEmojiMarginRight;
        if (viewIndex==0)
            params.setMargins(config.emojiViewMarginLeft + left, top, right, bottom);
        else if (viewIndex==config.emojis.size()-1)
            params.setMargins(left, top, right+config.emojiViewMarginRight, bottom);
        else
            params.setMargins(left, top, right, bottom);
        return params;
    }

    //used to show contents the view
    public void show()
    {
        if (this.emojiImagesContainer.getVisibility() == View.VISIBLE) return;

        if (config.emojiViewInAnimation!=null)
            this.startAnimation(config.emojiViewInAnimation);

        if (this.emojiBackgroundView!=null)
            this.emojiBackgroundView.setVisibility(View.VISIBLE);

        this.emojiImagesContainer.setVisibility(View.VISIBLE);
    }

    //used to hide contents of the view
    public void hide()
    {
        if (this.emojiImagesContainer.getVisibility() != View.VISIBLE) return;

        if (config.emojiViewOutAnimation!=null)
        {
            this.startAnimation(config.emojiViewOutAnimation);

            config.emojiViewOutAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (emojiBackgroundView!=null)
                        emojiBackgroundView.setVisibility(View.INVISIBLE);
                    emojiImagesContainer.setVisibility(View.INVISIBLE);

                    for (int i=0; i<emojiImageViews.size(); i++)
                        emojiImageViews.get(i).setLayoutParams(getDefaultLayoutParams(i));
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        else
        {
            if (this.emojiBackgroundView!=null)
                this.emojiBackgroundView.setVisibility(View.INVISIBLE);
            this.emojiImagesContainer.setVisibility(View.INVISIBLE);
        }
    }

    public void onTouchUp (float x, float y)
    {
        if (this.isShowed())
        {
            if (config.onEmojiSelectedListener!=null)
            {
                if (selectedEmoji >=0&& selectedEmoji <this.config.emojis.size())
                {
                    if (x!=-1&&y!=-1)
                    {
                        if (selectedEmoji!=-1)
                        {
                            config.onEmojiSelectedListener.onEmojiSelected(this.config.emojis.get(selectedEmoji));
                        }
                    }
                }
            }
        }
    }

    public void onTouchMove (float x, float y)
    {
        int maxX=getWidth();
        int minX=(int)getX();
        int index=(int)(((x-minX)/(float)maxX)*config.emojis.size());

        if (x<minX||x>maxX+minX)
        {
            //out of the emoji zone
            for (int i=0; i<config.emojis.size(); i++)
                setUnselectedEmoji(i);
            selectedEmoji=-1;
        }
        else
        {
            if (index<0) index=0;
            if (index>=config.emojis.size()) index=config.emojis.size()-1;
            setSelectedLikeFor(index);
        }
    }

    private void setSelectedLikeFor (int selected)
    {
        for (int i=0; i<selected; i++)
            setUnselectedEmoji(i);
        for (int i=selected+1; i<config.emojis.size(); i++)
            setUnselectedEmoji(i);
        setSelectedEmoji(selected);
    }

    public void onTouchDown (final float x, final float y)
    {
        onTouchMove(x, y);

        //hackish bugfix - you didn't saw this ;)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onTouchMove(x, y);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onTouchMove(x, y);
                    }
                }, 50);
            }
        }, 50);
    }

    private void setSelectedEmoji(int index)
    {
        if (index>=0&&index<emojiImageViews.size())
        {
            selectedEmoji=index;
            ImageView view=emojiImageViews.get(selectedEmoji);
            float w=getWeight (view);
            growView(view, index, w, 4, config.emojiAnimationSpeed, true);
        }
    }

    private void setUnselectedEmoji(int index)
    {
        if (index>=0&&index<emojiImageViews.size())
        {
            ImageView view = emojiImageViews.get(index);
            float w=getWeight (view);
            shrinkView(view, index, w, 1f, -config.emojiAnimationSpeed, false);
        }
    }

    private void growView (ImageView view, int index, float initWeight, float maxWeight, float step, boolean forSelected)
    {
        Animation a=new ViewWeightAnimation(getContext(), view, index, initWeight, maxWeight, step, forSelected, config);
        view.startAnimation(a);
    }

    private void shrinkView (ImageView view, int index, float initWeight, float maxWeight, float step, boolean forSelected)
    {
        growView(view, index, initWeight, maxWeight, step, forSelected);
    }

    public float getWeight (View view)
    {
        return ((LinearLayout.LayoutParams)view.getLayoutParams()).weight;
    }

    public boolean isShowed ()
    {
        return this.emojiImagesContainer.getVisibility()==View.VISIBLE;
    }
}
