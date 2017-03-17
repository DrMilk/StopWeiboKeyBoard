package treememory.com.treememory.MenuItemActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import treememory.com.treememory.R;
import treememory.com.treememory.WuUpload.MyUpload;
import treememory.com.treememory.WuUpload.WuSdcard;

public class About extends AppCompatActivity {
    private ImageView img_bird;
    private AnimationDrawable anim_draw_bird;
    private Animation anim_fly;
    private Context mcontext;
    private MyUpload myUpload;
    private ImageView img_word;
//    private int[] wordcount=new int[]{1,3,5,7};
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:img_word.setImageResource(R.mipmap.about1);break;
                case 1:img_word.setImageResource(R.mipmap.about3);break;
                case 2:img_word.setImageResource(R.mipmap.about5);break;
                case 3:img_word.setImageResource(R.mipmap.about7);break;
            }
            img_word.startAnimation(anim_word);
        }
    };
    private MediaPlayer mp;
    private Animation anim_word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnHead();
        setContentView(R.layout.activity_about);
        initView();
        downdata();
        startall();
    }

    private void startall() {
        mp.start();
        anim_draw_bird.start();
        img_word.startAnimation(anim_word);
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (true){
                    try {
                        Message msg=new Message();
                        i++;
                        msg.what=i%4;
                        Thread.sleep(4000);
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void downdata() {
        File filemucis=new File(WuSdcard.getMysdcard().pathabout+File.separator+"keybgmusic.mp3");
        if(!filemucis.exists()){
            myUpload.download_saveEverthing("keymanword","KeyServer/about/bgmusic/keybgmusic.mp3",myUpload.UPLOAD_MUSIC,WuSdcard.pathabout);
            return;
        }
         mp=new MediaPlayer();
        try {
            FileInputStream fis=new FileInputStream(filemucis);
            mp.setDataSource(fis.getFD());
            mp.prepare();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        mcontext=this;
        myUpload=new MyUpload(mcontext);
        img_bird= (ImageView) findViewById(R.id.activity_about_bird);
        img_word= (ImageView) findViewById(R.id.about_word);
        anim_draw_bird= (AnimationDrawable) img_bird.getDrawable();
        anim_fly= AnimationUtils.loadAnimation(this,R.anim.bird_fly);
        anim_word=AnimationUtils.loadAnimation(this,R.anim.aboutwordanim);
        LinearInterpolator li=new LinearInterpolator();
        anim_fly.setInterpolator(li);
        anim_word.setInterpolator(li);
        anim_word.setFillBefore(true);
        anim_word.setFillAfter(true);
        img_bird.setAnimation(anim_fly);
      //  img_word.setAnimation(anim_word);

    }
    private void setOnHead() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
}
