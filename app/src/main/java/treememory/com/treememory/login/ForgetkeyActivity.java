package treememory.com.treememory.login;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import treememory.com.treememory.R;

/**
 * Created by Administrator on 2016/11/28.
 */

public class ForgetkeyActivity extends Activity{
    private EditText editTextphone;
    private EditText editTextpassword;
    private EditText editTextauthcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnHead();
        setContentView(R.layout.forgetactivity);
        editTextphone= (EditText) findViewById(R.id.forgetactivity_phonenum);
        editTextpassword= (EditText) findViewById(R.id.forgetactivity_key);
        editTextauthcode= (EditText) findViewById(R.id.forgetactivity_knowkey);
        Button button_yanzheng= (Button) findViewById(R.id.forget_auth);
        button_yanzheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        Button button_ok= (Button) findViewById(R.id.forget_ok);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptsend();
            }
        });
    }

    private void attemptsend() {
        String str_phone=editTextphone.getText().toString();
        String str_key=editTextpassword.getText().toString();;
        String str_authcode=editTextauthcode.getText().toString();
        if(!isCorretPhone(str_phone))
            editTextphone.setError(getString(R.string.error_invalid_email));
        else if(!isMima(str_key))
            editTextpassword.setError(getString(R.string.error_invalid_password));

    }

    private boolean isCorretPhone(String phonenum){
        return  phonenum.length()==11;
    }
    private boolean isMima(String mima){
        return  mima.length()>5;
    }
    private boolean isName(String phonenum){
        return  phonenum.length()>0;
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
