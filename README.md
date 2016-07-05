# emojilike-android

## Facebook-like "Like with Emoji" library for Android

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-emojilike--android-green.svg?style=true)](https://android-arsenal.com/details/1/3839)

![](https://github.com/andob/emojilike-android/raw/master/emojilike.gif)  

Just like Facebook's app:
- long press on like button
- keep your finger down, swipe left or right
- release your finger

### Show me the code  

#### Step 1. Get it with

```gradle
allprojects {
	repositories {
		maven { url "https://jitpack.io" }
		...
dependencies {
	compile 'com.github.andob:emojilike-android:beta2'
...
```

#### Step 2. Setup your "emoji-like" activity

This library uses the activity to detect touch events, to prevent sending touch events to RecyclerView/ListView/ScrollView when the EmojiLikeView is visible on screen (when you select an emoji, you can move your finger up/down and the activity will prevent the scrolling view to scroll).

Thus, you will need to extend a class called ActivityWithEmoji

```java
public class FragmentActivitySample extends ActivityWithEmoji
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        .....
```

**OR** without extending,

```java
public class MainActivity extends AppCompatActivity implements IActivityWithEmoji
{
    EmojiLikeTouchDetector emojiLikeTouchDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        .....
        emojiLikeTouchDetector=new EmojiLikeTouchDetector();
        .....
    }
    
    //override theese 2 methods if your activity doesn't extend ActivityWithEmoji
    @Override
    public boolean dispatchTouchEvent(MotionEvent event)
    {
        boolean shouldCallSuper=emojiLikeTouchDetector.dispatchTouchEvent(event);
        if (shouldCallSuper)
            return super.dispatchTouchEvent(event);
        return false;
    }

    @Override
    public void configureEmojiLike(EmojiConfig config)
    {
        emojiLikeTouchDetector.configure(config);
    }
}
```

#### Step 3. Define EmojiLikeView in XML

You can put an EmojiLikeView whereever you want, in an activity, fragment, an item from an adapter (for RecyclerViews or ListViews), etc.

You just need to define the "trigger" view (=the like button, the button that, once long pressed, the view with emojis will be visible on screen)

```java
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Like"
        android:id="@+id/likeButton" ....../>
```

And the view with emojis:

```java
<ro.andreidobrescu.emojilike.EmojiLikeView
        android:layout_width="match_parent"
        android:layout_height="110dp"
        android:id="@+id/emojiView"
        android:layout_alignBottom="@+id/likeButton"
        android:layout_marginBottom="10dp"/>
```

Then get them in your activity/fragment/list cell/etc.

#### Step 4. Configure the emojis

When the activity/fragment/list cell is created, use EmojiConfig class to configure how emojis will behave. Pass a reference to the context to "with" metod (the context will need to implement IActivityWithEmoji or extend ActivityWithEmoji as described above), the "trigger" view to "on" method, and the EmojiLikeView to "open" method. Then, use addEmoji to add emoji objects (with picture and description).

With this class you can personalize the EmojiLikeView, the in and out animation, background image, margins, delays. There is no default background image (the image behind emojis) or in/out animations, so you will probably need to set them as below.

```java
//in onCreate/onCreateView/onBindViewHolder/etc
EmojiConfig.with(this)
	.on(likeButton)
	.open(emojiView)
	.addEmoji(new Emoji(R.drawable.like, "Like"))
	.addEmoji(new Emoji(R.drawable.haha, "Haha"))
	.addEmoji(new Emoji(R.drawable.kiss, "Kiss"))
	.addEmoji(new Emoji(R.drawable.sad, "Sad"))
	.addEmoji(new Emoji(R.drawable.t, ":P"))
	.setEmojiViewInAnimation((AnimationSet)AnimationUtils.loadAnimation(this, R.anim.in_animation))
	.setEmojiViewOutAnimation((AnimationSet) AnimationUtils.loadAnimation(this, R.anim.out_animation))
	.setBackgroundImage(R.drawable.background_drawable)
	.setOnEmojiSelectedListener(new OnEmojiSelectedListener() {
		@Override
		public void onEmojiSelected(Emoji emoji) {
			Toast.makeText(getContext(), "Selected " + emoji.getDescription(), Toast.LENGTH_SHORT).show();
		}
	})
	.setup();
```

For more info look at the sample project.

### License

```java
Copyright 2016 Andrei Dobrescu  

Licensed under the Apache License, Version 2.0 (the "License"); 
you may not use this file except in compliance with the License. 
You may obtain a copy of the License at  

http://www.apache.org/licenses/LICENSE-2.0  

Unless required by applicable law or agreed to in writing, software 
distributed under the License is distributed on an "AS IS" BASIS, 
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
See the License for the specific language governing permissions and 
limitations under the License.`
```
