package com.example.hackrandroid.app.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
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
    if (sourceSansRegular == null)
      sourceSansRegular = Typeface.createFromAsset(context.getAssets(), "fonts/sourceSansProRegular.ttf");
    return sourceSansRegular;
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

  public static Bitmap getCroppedBitmap(Bitmap bitmap) {
    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
            bitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);

    final float borderOffset = 2;
    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
            bitmap.getWidth() / 2, paint);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);

//    Paint mPaint = new Paint();
//    mPaint.setColor(0xFFFFFFFF);
//    mPaint.setStrokeWidth(borderOffset);
//    mPaint.setStyle(Paint.Style.STROKE);
//    mPaint.setAntiAlias(true);
//    canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
//            bitmap.getWidth() / 2 - (borderOffset / 2), mPaint);
    Bitmap _bmp = Bitmap.createScaledBitmap(output, 100, 100, false);
    return _bmp;
//    return output;
  }

}
