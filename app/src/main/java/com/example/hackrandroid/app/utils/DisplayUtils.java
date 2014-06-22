package com.example.hackrandroid.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.hackrandroid.app.R;

public class DisplayUtils {

  private static Typeface sourceSansLight;
  private static Typeface sourceSansRegular;
  private static final Typeface sansSerifLight = Typeface.create("sans-serif-light", Typeface.NORMAL);

  public static Typeface getSourceSansLightTypeface(Context context) {
    // Creating pictos from assets requires a reference to a context, which cannot be obtained statically
    if (sourceSansLight == null)
      sourceSansLight = Typeface.createFromAsset(context.getAssets(), "fonts/sourceSansProLight.ttf");
    return sourceSansLight;
  }

  public static Typeface getSourceSansRegularTypeface(Context context) {
    // Creating pictos from assets requires a reference to a context, which cannot be obtained statically
    if (sourceSansLight == null)
      sourceSansLight = Typeface.createFromAsset(context.getAssets(), "fonts/sourceSansProRegular.ttf");
    return sourceSansLight;
  }

  public static Typeface getSansSerifLight() {
    return sansSerifLight;
  }

  public static void sansSerifLightifyTextView(TextView tv) {
    tv.setTypeface(sansSerifLight);
  }

  public static void sourcesSansLightifyTextView(TextView tv) {
    tv.setTypeface(getSourceSansLightTypeface(tv.getContext()));
  }

  public static void sourcesSansRegularifyTextView(TextView tv) {
    tv.setTypeface(getSourceSansRegularTypeface(tv.getContext()));
  }

  public static void shakeView(View view) {
    Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake);
    view.startAnimation(shake);
  }

  public static int toDp(int px) {
    return (int) ((px / Resources.getSystem().getDisplayMetrics().density) + 0.5);
  }

  public static int toPx(int dp) {
    return (int) ((dp * Resources.getSystem().getDisplayMetrics().density) + 0.5);
  }

}
