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
		maven { url "http://maven.andob.info/reporitory/open_source" }
		...
dependencies {
	implementation 'ro.andob.emojilike-android:emojilike-android:beta4'
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
    
    //override these 2 methods if your activity doesn't extend ActivityWithEmoji
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
	.addEmoji(new Emoji(R.drawable.p, ":P"))
	.setOnEmojiSelectedListener(emoji -> Toast.makeText(getContext(), "Selected " + emoji.getDescription(), Toast.LENGTH_SHORT).show())
	.setup();
```

#### 5. [Optional] More configuration methods

``setTouchDownDelay(int touchDownDelay)`` - set the time delay (in milliseconds) between the touch down event and the moment the emoji view is showed (default value = 100)

``setTouchUpDelay(int touchUpDelay)`` - set the time delay (in milliseconds) between the touch up event and the moment the emoji view is hided (default = 500)

``setEmojiAnimationSpeed(float emojiAnimationSpeed)`` - set animation speed (default = 0.2)

``setBackgroundImageResource(int backgroundImageResource)`` - set background image (by default, a white rounded rectangle)

``setEmojiViewOpenedAnimation(Animation emojiViewOpenedAnimation)`` - set the animation when emoji view is opened (by default, a scale in from center - bottom)

 ``setEmojiViewClosedAnimation(Animation emojiViewClosedAnimation)`` - set the animation when emoji view is closed (by default, a scale out to center - bottom)
 
``setEmojiCellViewFactory(IEmojiCellViewFactory factor`` - sets the emoji cell view factory. See below.

```
Various dimensions:
setSelectedEmojiHeight(int selectedEmojiHeight = 85dp)
setSelectedEmojiWeight(int selectedEmojiWeight = 4)
setEmojiViewMarginLeft(int emojiViewMarginLeft = 15dp)
setEmojiViewMarginRight(int emojiViewMarginRight = 15dp)
setSelectedEmojiMarginBottom(int selectedEmojiMarginBottom = 15dp)
setSelectedEmojiMarginTop(int selectedEmojiMarginTop = 0)
setSelectedEmojiMarginLeft(int selectedEmojiMarginLeft = 15dp)
setSelectedEmojiMarginRight(int selectedEmojiMarginRight = 15dp)
setUnselectedEmojiMarginBottom(int unselectedEmojiMarginBottom = 0)
setUnselectedEmojiMarginTop(int unselectedEmojiMarginTop = 30dp)
setUnselectedEmojiMarginLeft(int unselectedEmojiMarginLeft = 15dp)
setUnselectedEmojiMarginRight(int unselectedEmojiMarginRight = 15dp)
setUnselectedEmojiWeight(int unselectedEmojiWeight = 1)
setEmojiImagesContainerHeight(int emojiImagesContainerHeight = 130dp)
setBackgroundViewHeight(int backgroundViewHeight = 50dp)
setBackgroundViewMarginBottom(int backgroundViewMarginBottom = 10dp)
```

#### 6. [Optional] Configure emoji cell view factory

You can customize the way emojis are rendered by specifying a "emoji cell view factory" (similar to a RecyclerView adapter) in the configuration:

The default factory that provides a cell view that renders only images:

```java
EmojiConfig.with(this)
    //...other configuration method calls
    .setEmojiCellViewFactory(EmojiCellView.WithImage::new)
    .setup();
```

Another factory that provides a cell view that renders images with text:

```java
EmojiConfig.with(this)
    //...other configuration method calls
    .setEmojiCellViewFactory(EmojiCellView.WithImageAndText::new)
    .setup();
```

You can also create your own emoji cell view:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="@dimen/default_emoji_description_label_height"
        android:layout_gravity="center_horizontal"
        android:layout_marginBottom="@dimen/default_emoji_description_label_bottom_margin"
        tools:text="Test"
        android:gravity="center_vertical"
        android:textColor="#ffffff"
        android:textSize="16sp"
        android:background="#000000"
        android:paddingLeft="10dp"
        android:paddingRight="10dp"
        android:lines="1"
        android:id="@+id/descriptionLabel"/>

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/imageView"/>

</LinearLayout>
```

```java
public class MyEmojiCellView extends EmojiCellView
{
    public MyEmojiCellView(@NonNull Context context)
    {
        super(context);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.my_emoji_cell_view;
    }

    @Override
    public void setEmoji(Emoji emoji)
    {
        //bind the model to the views
        ImageView imageView=findViewById(R.id.imageView);
        TextView descriptionLabel=findViewById(R.id.descriptionLabel);

        imageView.setImageResource(emoji.getDrawable());
        descriptionLabel.setText(emoji.getDescription());
    }

    @Override
    public void onWeightAnimated(float animationPercent)
    {
        //called when a weight is set upon an emoji cell view - animate your child views accordingly
        TextView descriptionLabel=findViewById(R.id.descriptionLabel);
        descriptionLabel.setAlpha(animationPercent); //make it more opaque or more transparent

        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                (int)(animationPercent*getResources().getDimensionPixelSize(R.dimen.default_emoji_description_label_height)));
        params.bottomMargin=getResources().getDimensionPixelSize(R.dimen.default_emoji_description_label_bottom_margin);
        params.gravity=Gravity.CENTER_HORIZONTAL;
        descriptionLabel.setLayoutParams(params); //scale the textview
    }
}
```

```java
EmojiConfig.with(this)
    //...other configuration method calls
    .setEmojiCellViewFactory(MyEmojiCellView::new)
    .setup();
```

For more info look at the sample project.

### License

```java
Copyright 2016 - 2020 Andrei Dobrescu  

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
