package treememory.com.treememory.MainInfor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import treememory.com.treememory.R;

/**
 * Created by Administrator on 2017/2/22.
 */

public class PopupWindowMainTab extends PopupWindow{
    private View more_menu;
    private View.OnClickListener onClickListener;
    private Context context;
    public PopupWindowMainTab(Context context, View.OnClickListener onClickListener,int width,int height){
        this.onClickListener=onClickListener;
        this.context=context;
        LayoutInflater inflater= LayoutInflater.from(context);
        more_menu=inflater.inflate(R.layout.popopwindow_maintab,null);
        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        this.setContentView(more_menu);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.my_popup_anim);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
//        Log.i("Wu",mMenu.getHeight()+"");
        this.setHeight(height/2);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable background = new ColorDrawable(0x4f000000);
//        //设置弹出窗体的背景
//        this.setBackgroundDrawable(background);
        intiViewClick();
    }
    private void intiViewClick(){
        ImageView imageView1= (ImageView) more_menu.findViewById(R.id.popupwindow_collection);
        ImageView imageView2= (ImageView) more_menu.findViewById(R.id.popupwindow_frinend_circle);
        ImageView imageView3= (ImageView) more_menu.findViewById(R.id.popupwindow_info);
        ImageView imageView4= (ImageView) more_menu.findViewById(R.id.popupwindow_link);
        ImageView imageView5= (ImageView) more_menu.findViewById(R.id.popupwindow_qq);
        ImageView imageView6= (ImageView) more_menu.findViewById(R.id.popupwindow_qzone);
        ImageView imageView7= (ImageView) more_menu.findViewById(R.id.popupwindow_reward);
        ImageView imageView8= (ImageView) more_menu.findViewById(R.id.popupwindow_url);
        ImageView imageView9= (ImageView) more_menu.findViewById(R.id.popupwindow_weibo);
        ImageView imageView10= (ImageView) more_menu.findViewById(R.id.popupwindow_weixin);
        ImageView imageView11= (ImageView) more_menu.findViewById(R.id.popupwindow_zhifubao);
        ImageView imageView12= (ImageView) more_menu.findViewById(R.id.popupwindow_weixin_friend);
        LinearLayout linear= (LinearLayout) more_menu.findViewById(R.id.popopwindow_lineartop);
        imageView1.setOnClickListener(onClickListener);
        imageView2.setOnClickListener(onClickListener);
        imageView3.setOnClickListener(onClickListener);
        imageView4.setOnClickListener(onClickListener);
        imageView5.setOnClickListener(onClickListener);
        imageView6.setOnClickListener(onClickListener);
        imageView7.setOnClickListener(onClickListener);
        imageView8.setOnClickListener(onClickListener);
        imageView9.setOnClickListener(onClickListener);
        imageView10.setOnClickListener(onClickListener);
        imageView11.setOnClickListener(onClickListener);
        imageView12.setOnClickListener(onClickListener);
        linear.setOnClickListener(onClickListener);
    }
}
