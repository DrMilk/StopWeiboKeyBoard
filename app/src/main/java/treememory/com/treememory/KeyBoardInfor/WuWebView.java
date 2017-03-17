package treememory.com.treememory.KeyBoardInfor;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import treememory.com.treememory.R;

/**
 * Created by Administrator on 2017/2/1.
 */

public class WuWebView extends Activity{
    private WebView mwebview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setOnHead();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wuwebview);
        mwebview= (WebView) findViewById(R.id.wuwebView);
        mwebview.getSettings().setJavaScriptEnabled(true);
        mwebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mwebview.getSettings().setSupportMultipleWindows(true);
        mwebview.setWebViewClient(new WebViewClient());
        mwebview.setWebChromeClient(new WebChromeClient());
        mwebview.loadUrl("http://weibo.com/u/5242909969");
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
}
