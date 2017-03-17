package treememory.com.treememory.Main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import treememory.com.treememory.KeyBoardInfor.WuWebView;
import treememory.com.treememory.KeySearch.WuSearch;
import treememory.com.treememory.MenuItemActivity.About;
import treememory.com.treememory.MenuItemActivity.SettingsActivity;
import treememory.com.treememory.MenuItemActivity.Writetreememory;
import treememory.com.treememory.MenuItemActivity.WuRewardWallet;
import treememory.com.treememory.OnlineData.TreeUser;
import treememory.com.treememory.R;
import treememory.com.treememory.WuUpload.MySqliteOpenHelper;
import treememory.com.treememory.WuUpload.MyUpload;
import treememory.com.treememory.WuUpload.WuSdcard;
import treememory.com.treememory.login.LoginActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    String TGA="MainActivity";
    private Context mcontext;
    private TextView text_username;
    private int bannercount=0;
    private int nowcount=0;
    private ViewPager bannerpager;
    private int bossPosition=0;
    private MyUpload myupload;
    private ArrayList<ImageView> list_banner_img;
    private LinearLayout ll;
    private boolean isUpdataBanner=false;
    private Thread bannerThread;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int status=msg.what;
            switch (status){
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnHead();
        setContentView(R.layout.activity_main);
        mcontext=this;
        myupload=new MyUpload(mcontext);
        mbmobinitdata();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ll= (LinearLayout) findViewById(R.id.main_banner_linear_point);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotouser();
            }
        });
        text_username = (TextView) headerView.findViewById(R.id.nav_header_username);
        checkuser();
        initbanner();
        ImageView img= (ImageView) findViewById(R.id.main_test);
        WuSdcard wuSdcard=new WuSdcard();
        wuSdcard.initWuSdcard(mcontext);
        //img.setImageBitmap(wuSdcard.getPicture(wuSdcard.getPathwriteimg(),"headimghead.jpg"));
    }

    private void createdataSql() {
        MySqliteOpenHelper msoh=new MySqliteOpenHelper(mcontext);
        msoh.getReadableDatabase();

    }

    private boolean checkuser() {
        TreeUser bmobUser = BmobUser.getCurrentUser(TreeUser.class);
        if(bmobUser != null){
            // 允许用户使用应用
            String name= (String) BmobUser.getObjectByKey("treename");
            text_username.setText(name);
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            treerun();
            return false;
        }
    }

    private void gotouser() {
    }

    private void mbmobinitdata() {
        Bmob.initialize(this, "3403a24a48b30855a5d71cd4e6c5a903");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId("3403a24a48b30855a5d71cd4e6c5a903")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(30)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }

    private void treerun() {
        treeabord();
    }

    private void treeabord() {
        Intent it=new Intent(this,LoginActivity.class);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent it=new Intent(this,LoginActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_write) {
            // Handle the camera action
            if(checkuser()){
                Intent it=new Intent(this,Writetreememory.class);
                startActivity(it);
            }
        } else if (id == R.id.nav_look) {
            Intent it=new Intent(this,WuSearch.class);
            startActivity(it);
        } else if (id == R.id.nav_nearby) {
            Intent it=new Intent(this,WuRewardWallet.class);
            startActivity(it);
        } else if (id == R.id.nav_setting) {
            Intent it=new Intent(this,SettingsActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_about) {
            Intent it=new Intent(this,About.class);
            startActivity(it);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void setOnHead() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    //       | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            );
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    //        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            //   window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }
//    private void setOnHead() {
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//            window.setNavigationBarColor(Color.TRANSPARENT);
//        }
//    }

    private void updataBanner(){
        if(!isUpdataBanner) {
            //ImageView imageView = (ImageView) findViewById(R.id.main_test);
           // myupload.download_show_saveEverthing("keymanword", "KeyServer/firstmain/banner/" + "qcode_banner_0" + 1 + ".jpg", "picture", WuSdcard.pathwriteimg, imageView);
            int otherbanercount = 0;
            for (int i = 0; i < 10; i++) {
                if (!myupload.isHaveObject("keymanword", "KeyServer/firstmain/banner/" + "qcode_banner_0" + (i + 1) + ".jpg")) {
                    otherbanercount = i;
                    break;
                }
            }
            if (otherbanercount != 0) {
                if (otherbanercount >= bannercount - 1) {
                    int distanceCount = otherbanercount - bannercount;
                    for (int ii = 0; ii < distanceCount; ii++) {
                        ImageView img = new ImageView(this);
                        img.setBackgroundResource(R.mipmap.default_user_page_cover);
                        list_banner_img.add(img);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
                        params.rightMargin = 6;
                        params.leftMargin = 6;
                        ImageView v = new ImageView(this);
                        v.setBackgroundResource(R.drawable.banner_off_point);
                        v.setLayoutParams(params);
                        ll.addView(v);
                    }
                }
            }
            for (int i = 0; i < list_banner_img.size(); i++)
          //      myupload.download_show_saveEverthing("keymanword", "KeyServer/firstmain/banner/" + "qcode_banner_0" + (i + 1) + ".jpg", "picture", WuSdcard.pathwriteimg, list_banner_img.get(i));
            isUpdataBanner=true;
        }
    }
    private void initbanner() {
        int selectcount=0;
        bannerpager= (ViewPager) findViewById(R.id.main_banner_viewpager);
        list_banner_img=new ArrayList<>();
        if(bannercount==0) bannercount=4;
        for(int ib=0;ib<bannercount;ib++){
            ImageView img=new ImageView(this);
            img.setBackgroundResource(R.mipmap.default_user_page_cover);
            list_banner_img.add(img);
        }
        for (int i=0;i<bannercount;i++){
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);
            params.rightMargin=6;
            params.leftMargin=6;
            ImageView v=new ImageView(this);
            if(i==0)
                v.setBackgroundResource(R.drawable.banner_on_point);
            else
                v.setBackgroundResource(R.drawable.banner_off_point);
            v.setLayoutParams(params);
            ll.addView(v);
        }
        WuBannerViewpagerAdapter madapter=new WuBannerViewpagerAdapter(mcontext,list_banner_img);
        bannerpager.setAdapter(madapter);
        bannerpager.setPageTransformer(false,new WuBannerPageTransformer(1));
        bannerpager.setCurrentItem(300-300%list_banner_img.size());
        nowcount=300-300%list_banner_img.size();
        bannerpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                nowcount=position;
                position=position%ll.getChildCount();
                Log.i("MainActivity","position"+position+"getChildCount()"+ll.getChildCount());
                bossPosition=position;
                for(int ii=0;ii<list_banner_img.size();ii++){
                    if(ii==position){
                        ll.getChildAt(ii).setBackgroundResource(R.drawable.banner_on_point);
                        Log.i("MainActivity",bossPosition+"bossPosition");}
                    else{
                        ll.getChildAt(ii).setBackgroundResource(R.drawable.banner_off_point);
                        Log.i("MainActivity",bossPosition+"bossPosition");}
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void startBanner(){
        if(bannerThread==null){
            bannerThread= new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        mhandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bannerpager.setCurrentItem(nowcount++);
                            }
                        },2000l);
                        try {
                            Thread.sleep(2800l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            bannerThread.start();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        startBanner();
        createdataSql();
        updataBanner();
    }
}
