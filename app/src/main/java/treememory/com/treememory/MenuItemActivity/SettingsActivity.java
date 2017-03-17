package treememory.com.treememory.MenuItemActivity;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

import treememory.com.treememory.R;
import treememory.com.treememory.Utils.SharePreferenceUtil;
import treememory.com.treememory.Utils.T;
import treememory.com.treememory.WuUpload.WuSdcard;
import treememory.com.treememory.wustringparsing.MyStringPsrsing;

public class SettingsActivity extends Activity implements View.OnClickListener{
    private Context mcontext;
    private ImageView img_wifi;
    private Boolean iswifi=true;
    private LinearLayout cache_linear;
    private TextView cache_text;
    private WuSdcard wuSdcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnHead();
        setContentView(R.layout.acticity_setting);
        mcontext=this;
        initdata();
        initview();
    }

    private void initdata() {
        iswifi=SharePreferenceUtil.getSettingDataBoolean(mcontext,SharePreferenceUtil.WIFIUPDATA_KEY);
        // SharePreference 封装的 Util 类
//        SharedPreferences sp=mcontext.getSharedPreferences("setting",Context.MODE_PRIVATE);
//        iswifi=sp.getBoolean("wifisetting",true);
    }

    private void initview() {
        cache_linear= (LinearLayout) findViewById(R.id.setting_cache_linear);
        cache_text= (TextView) findViewById(R.id.setting_cache);
        wuSdcard=new WuSdcard();
        cache_text.setText("( 共 "+MyStringPsrsing.getFormatSize(wuSdcard.getFolderSize(new File(WuSdcard.pathCache)))+")");
        img_wifi= (ImageView) findViewById(R.id.setting_wifiupdata);
        img_wifi.setOnClickListener(this);
        cache_linear.setOnClickListener(this);
        if(!iswifi){img_wifi.setImageResource(R.mipmap.switching_off);}
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_wifiupdata:savewifisetting();break;
            case R.id.setting_cache_linear:if(wuSdcard.deleteFolderFile(WuSdcard.pathCache,true))
                T.showShot(SettingsActivity.this.getApplicationContext(),"清理成功！");
                cache_text.setText("( 共 "+MyStringPsrsing.getFormatSize(wuSdcard.getFolderSize(new File(WuSdcard.pathCache)))+")");break;
        }
    }
        private void setOnHead() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
    private void savewifisetting(){
        if(iswifi){
            img_wifi.setImageResource(R.mipmap.switching_off);
            iswifi=false;
        }else {
            img_wifi.setImageResource(R.mipmap.switch_on_normal);
            iswifi=true;
        }
        SharePreferenceUtil.putSettingDataBoolean(mcontext,SharePreferenceUtil.WIFIUPDATA_KEY,iswifi);
//        SharedPreferences sp=mcontext.getSharedPreferences("setting", Context.MODE_PRIVATE);
//        SharedPreferences.Editor edit=sp.edit();
//        edit.putBoolean("wifisetting",iswifi);
//        edit.commit();
    };
    @Override
    protected void onStop() {
        super.onStop();
    }
}