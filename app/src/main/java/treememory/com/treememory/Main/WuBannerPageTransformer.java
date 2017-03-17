package treememory.com.treememory.Main;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/2/1.
 */

public class WuBannerPageTransformer implements ViewPager.PageTransformer{
    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.7f;
    private String TAG="WuBannerPageTransformer";
    private int kind=0;
    public WuBannerPageTransformer(int i){
        kind=i;
    }
    public void transformPage(View view, float position) {
       switch (kind){
           case 1: int pageWidth = view.getWidth();
               int pageHeight = view.getHeight();

               if (position < -1) { // [-Infinity,-1)
                   // This page is way off-screen to the left.
                   view.setAlpha(0);

               } else if (position <= 1) { // [-1,1]
                   // Modify the default slide transition to shrink the page as well
                   float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                   float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                   float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                   if (position < 0) {
                       view.setTranslationX(horzMargin - vertMargin / 2);
                   } else {
                       view.setTranslationX(-horzMargin + vertMargin / 2);
                   }

                   // Scale the page down (between MIN_SCALE and 1)
                   view.setScaleX(scaleFactor);
                   view.setScaleY(scaleFactor);

                   // Fade the page relative to its size.
                   view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

               } else { // (1,+Infinity]
                   // This page is way off-screen to the right.
                   view.setAlpha(0);
               }break;
           case 2:int width = view.getWidth();
               int height = view.getHeight();
               //這裏只對右邊的view做了操作
               if (position > 0 && position <= 1) {//right scorlling
                   //position是1.0->0,但是沒有等於0
                   Log.e(TAG, "right----position====" + position);
                   //設置該view的X軸不動
                   view.setTranslationX(-width * position);
                   //設置縮放中心點在該view的正中心
                   view.setPivotX(width / 2);
                   view.setPivotY(height / 2);
                   //設置縮放比例（0.0，1.0]
                   view.setScaleX(1 - position);
                   view.setScaleY(1 - position);

               } else if (position >= -1 && position < 0) {//left scrolling

               } else {//center

               }break;
       }
    }
}
