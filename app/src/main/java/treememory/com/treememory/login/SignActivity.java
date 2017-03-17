package treememory.com.treememory.login;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import treememory.com.treememory.R;
import treememory.com.treememory.OnlineData.TreeUser;
import treememory.com.treememory.wustringparsing.MyStringPsrsing;

/**
 * Created by Administrator on 2016/11/28.
 */

public class SignActivity extends Activity {
    private Context mcontext;
    private EditText editTextphone;
    private EditText editTextpassword;
    private EditText editTextname;
    private Spinner mspinner;
    private String sex="男";
    private MyStringPsrsing myStringPsrsing=new MyStringPsrsing();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnHead();
        setContentView(R.layout.signactivity);
        editTextphone= (EditText) findViewById(R.id.signactivity_phonenum);
        editTextpassword= (EditText) findViewById(R.id.signactivity_key);
        editTextname= (EditText) findViewById(R.id.signactivity_name);
        mspinner= (Spinner) findViewById(R.id.sign_spin);
        mcontext=this;
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex= (String) mspinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button button_ok= (Button) findViewById(R.id.sign_chuce);
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptsign();
            }
        });
    }

    private void attemptsign() {
        boolean jundge=true;
        Log.i("Wu","nihao");
        String str_phone=editTextphone.getText().toString();
        String str_key=editTextpassword.getText().toString();
        String str_name=editTextname.getText().toString();
        if(!isCorretUser(str_phone)) {
            editTextphone.setError(getString(R.string.error_invalid_email));
            jundge = false;
        }
        else if (!isMima(str_key)){
            editTextpassword.setError(getString(R.string.error_invalid_password));
             jundge=false;
        }
        else if(!isName(str_name)){
            editTextname.setError("昵称不能为空");
            jundge=false;
        }
        //else if()
        if(jundge){
            TreeUser bu = new TreeUser();
            bu.setUsername(str_phone);
            bu.setPassword(str_key);
            bu.setTreePassword(str_key);
            bu.setTreename(str_name);
            bu.setSex(sex);
            bu.signUp(new SaveListener<TreeUser>() {
                @Override
                public void done(TreeUser s, BmobException e) {
                    if(e==null){
                        Toast.makeText(getApplicationContext(),"注册成功:" +s.toString(),Toast.LENGTH_SHORT).show();
                        Log.i("Wu","成功呢");
                        Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT);
                    }else{
                        Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT).show();
                        Log.i("Wu",e+"");
                        Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_SHORT);
                        editTextpassword.setError(e+"");
                    }
                }
            });
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    int i=1;
//                    while(i>0){
//                        i--;
//                        try {
//                            Thread.sleep(1000l);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    thisfinish();
//                }
//            }).start();
        }else {
            Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT);
        }
    }

    private void thisfinish() {
        this.finish();
    }
    private boolean isCorretUser(String phonenum){
        return  myStringPsrsing.istrueUser(phonenum);
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
