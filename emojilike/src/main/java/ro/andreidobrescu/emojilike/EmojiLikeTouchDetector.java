package ro.andreidobrescu.emojilike;

import android.view.MotionEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by using on 7/4/2016.
 */
public class EmojiLikeTouchDetector
{
    private List<EmojiTriggerManager> emojiTriggerManagers;

    private List<EmojiTriggerManager> getEmojiTriggerManagers()
    {
        if (emojiTriggerManagers==null)
            emojiTriggerManagers=new LinkedList<>();
        return emojiTriggerManagers;
    }

    public boolean dispatchTouchEvent(MotionEvent event)
    {
        for (EmojiTriggerManager manager : getEmojiTriggerManagers())
        {
            if (manager.getEmojiView().isShowed()||event.getAction()==MotionEvent.ACTION_DOWN||event.getAction()==MotionEvent.ACTION_UP)
            {
                boolean shouldCallSuper=manager.onTouch(event);
                if (shouldCallSuper==false&&event.getAction()==MotionEvent.ACTION_MOVE)
                    return false;
            }
        }

        return true;
    }

    public void configure (EmojiConfig config)
    {
        EmojiTriggerManager emojiTriggerManager=new EmojiTriggerManager();
        emojiTriggerManager.configure(config);
        getEmojiTriggerManagers().add(emojiTriggerManager);
    }
}
