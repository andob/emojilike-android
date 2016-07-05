package ro.andreidobrescu.emojilike;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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

    private Point cachedScreenSize;

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
        this.selectedEmoji=config.initialSelectedEmoji;

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
            emojiImage.setLayoutParams(getDefaultLayoutParams (i));
            emojiImage.setImageResource(emoji.getDrawable());

            this.emojiImagesContainer.addView(emojiImage);
            this.emojiImageViews.add(emojiImage);
        }

        this.bringToFront();
    }

    //default layoutparams for an emoji imageview
    private LinearLayout.LayoutParams getDefaultLayoutParams (int viewIndex)
    {
        boolean isSelected=viewIndex==config.initialSelectedEmoji;
        int h=isSelected?config.selectedEmojiHeight
                :LinearLayout.LayoutParams.MATCH_PARENT;
        float weight=isSelected?config.selectedEmojiWeight:config.unselectedEmojiWeight;
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(0, h, weight);

        int left=isSelected?config.selectedEmojiMarginLeft:config.unselectedEmojiMarginLeft;
        int top=isSelected?config.selectedEmojiMarginTop:config.unselectedEmojiMarginTop;
        int bottom=isSelected?config.selectedEmojiMarginBottom:config.unselectedEmojiMarginBottom;
        int right=isSelected?config.selectedEmojiMarginRight:config.unselectedEmojiMarginRight;
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

                    selectedEmoji=config.initialSelectedEmoji;
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

    public Point getScreenSize ()
    {
        if (cachedScreenSize==null)
        {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            cachedScreenSize = new Point();
            display.getSize(cachedScreenSize);
        }

        return cachedScreenSize;
    }

    public void onTouchUp (float x, float y)
    {
        if (config.onEmojiSelectedListener!=null)
        {
            if (selectedEmoji >=0&& selectedEmoji <this.config.emojis.size())
            {
                if (x!=-1&&y!=-1)
                {
                    config.onEmojiSelectedListener.onEmojiSelected(this.config.emojis.get(selectedEmoji));
                }
            }
        }
    }

    public void onTouchMove (float x, float y)
    {
        int maxX=getScreenSize().x;
        int index=(int)((x/(float)maxX)*config.emojis.size());
        if (index<0) index=0;
        if (index>=config.emojis.size()) index=config.emojis.size()-1;
        ImageView v=emojiImageViews.get(index);
        setSelectedLikeFor(index);
    }

    private void setSelectedLikeFor (int selected)
    {
        setSelectedEmoji(selected);
        for (int i=0; i<selected; i++)
            setUnselectedEmoji(i);
        for (int i=selected+1; i<config.emojis.size(); i++)
            setUnselectedEmoji(i);
    }

    public void onTouchDown (float x, float y)
    {
        int sel=-1, i=0;
        for (ImageView like : emojiImageViews)
        {
            int [] coords=new int [2];
            like.getLocationOnScreen(coords);
            if (coords[0] + like.getWidth()+config.unselectedEmojiMarginRight > x
                    && coords[0]-config.unselectedEmojiMarginLeft < x)
            {
                sel=i;
                break;
            }

            i++;
        }

        if (sel!=-1)
        {
            setSelectedLikeFor(sel);
        }
    }

    private void setSelectedEmoji(int index)
    {
        if (index>=0&&index<emojiImageViews.size())
        {
            selectedEmoji=index;
            ImageView view=emojiImageViews.get(selectedEmoji);
            float w=getWeight (view);
            growView(view, index, w, 4, 0.4f, true);
        }
    }

    private void setUnselectedEmoji(int index)
    {
        if (index>=0&&index<emojiImageViews.size())
        {
            ImageView view=emojiImageViews.get(index);
            float w=getWeight (view);
            shrinkView(view, index, w, 1f, -0.4f, false);
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
