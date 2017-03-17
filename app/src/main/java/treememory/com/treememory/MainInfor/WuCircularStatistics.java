package treememory.com.treememory.MainInfor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import treememory.com.treememory.R;

/**
 * Created by Administrator on 2017/1/21.
 */

public class WuCircularStatistics extends View{
    private String TAG="WuCircularStatistics";
    private String[] level=new String[]{"侮辱","诽谤","歧视","撩骚"};
    private int[] strpresent=new int[]{};
    private float radius=300;
    private float circularwidth=150;
    private Paint mpaint;
    private int mHeight;
    private int mWidth;
    private int[] mColor = new int[]{this.getResources().getColor(R.color.purple_level),
                                    this.getResources().getColor(R.color.red_level),
                                    this.getResources().getColor(R.color.blue_level),
                                    this.getResources().getColor(R.color.yello_level)};
    public WuCircularStatistics(Context context) {
        super(context);
    }

    public WuCircularStatistics(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WuCircularStatistics(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight=h;
        mWidth=w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //移动画布到圆环的左上角
        canvas.translate(mWidth / 2 - radius / 2, mHeight / 2 - radius / 2);
        //初始化画笔
        initPaint();
        //画圆环
        drawcircle(canvas);
    }
    private void initPaint(){
        mpaint=new Paint();
        mpaint.setAntiAlias(true);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(circularwidth);
    }
    private void drawcircle(Canvas canvas){
        float startPercent = 0;
        float sweepPercent = 0;
        float all=0;
        for(int q=0;q<strpresent.length;q++){
            all+=strpresent[q];
        }
        for (int i = 0; i <strpresent.length; i++) {
            mpaint.setColor(mColor[i]);
            Log.i(TAG,startPercent+"起始度");
            //这里采用比例占100的百分比乘于360的来计算出占用的角度，使用先乘再除可以算出值
            if(strpresent[i]!=0){
                startPercent = sweepPercent + startPercent;
                sweepPercent = strpresent[i] * 360 / all;
                Log.i(TAG,sweepPercent+"结束度");
                sweepPercent++;
                canvas.drawArc(new RectF(0, 0, radius, radius), startPercent,sweepPercent, false, mpaint);
                startPercent--;
            }
        }
    }
    public void setListData(int[] strpresent){
        this.strpresent=strpresent;
    }
}
