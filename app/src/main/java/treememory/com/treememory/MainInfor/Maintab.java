package treememory.com.treememory.MainInfor;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import treememory.com.treememory.Main.WuBannerPageTransformer;
import treememory.com.treememory.OnlineData.Other;
import treememory.com.treememory.OnlineData.TreeUser;
import treememory.com.treememory.OnlineData.WuContext;
import treememory.com.treememory.OnlineData.WuRemark;
import treememory.com.treememory.OnlineData.WuReward;
import treememory.com.treememory.R;
import treememory.com.treememory.MenuItemActivity.WuRewardWallet;
import treememory.com.treememory.Utils.T;
import treememory.com.treememory.WuUpload.MyUpload;
import treememory.com.treememory.login.LoginActivity;
import treememory.com.treememory.wustringparsing.MyUrlGet;

/**
 * Created by Administrator on 2017/1/18.
 */

public class Maintab extends Activity{
    private String user_id;
    private String TAG="Maintab";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private LayoutInflater mInflater;
    private View view_context,view_data,view_remark;
    private List<View> list_view=new ArrayList<View>();
    private List<String> list_title=new ArrayList<String>();
    private OtherCopy other=new OtherCopy();
    private ArrayList<WuContext> list_context=new ArrayList<WuContext>();
    private ArrayList<WuReward> list_reward=new ArrayList<WuReward>();
    private ArrayList<String> list_reward_str;
    private BmobQuery<WuContext> query;
    private int lock_num=-1;
   // private ListView listview_context;
    private RecyclerView recyclerView_context;
    private TextView text_all;
    private TextView text_wuru;
    private TextView text_qishi;
    private TextView text_liaosao;
    private TextView text_feibang;
    private LinearLayout context_loading_linear;
    private LinearLayout data_loading_linear;
    private RecyclerView recyclerView_remark;
    private WuRemarkRecycleAdapter wuRemarkRecycleAdapter;
    private EditText tab_remark_edit;
    private ScrollView scrollView;
    private ImageView img_head;
    private MyUpload myUpload;
    private Context context;
    private String mObjectId;
    private LinearLayout linear_remark_write;
    private int other_jundge;
    private float remark_bottom_hide = 0;
    private float remark_bottom_show = 0;
    private boolean remark_bottom_status=true;
    private boolean stausa=true;
    WuCircularStatistics wucircle;
    private int[] level_count=new int[]{1,1,1,1};
    private int collapsing_heigh=0;
    private int collapsing_width=0;
    private float collapsing_Y=0;
    ImageView maintab_bg;
    private CollapsingToolbarLayout ctl;
    private PopupWindowMainTab mPopupWindows;
    private int width=-1;
    private int height=-1;
    private WuContextRecyclerViewAdapter mcontextAdapter;
    private Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x1234)
            {
            }
        };
    };

    public Maintab() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnHead();
        setContentView(R.layout.activity_maintab);
        initView();
        initContext();
    }

    private void initData() {
//        String mNames[] = {
//                "welcome","android","TextView",
//                "apple","jamy","kobe bryant",
//                "jordan","layout","viewgroup",
//                "margin","padding","text",
//                "name","type","search","logcat"
//        };
//        String mMoney[] = {
//                "","android","TextView",
//                "apple","jamy","kobe bryant",
//                "jordan","layout","viewgroup",
//                "margin","padding","text",
//                "name","type","search","logcat"
//        };
        TextView reward_num_textview= (TextView) view_data.findViewById(R.id.reward_num);
        reward_num_textview.setText(list_reward_str.size()+"");
        WuTagCloudLayout wuTagCloudLayout= (WuTagCloudLayout) view_data.findViewById(R.id.tab_data_WuTagCloudLayout);
       // MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
//        lp.leftMargin = 5;
//        lp.rightMargin = 5;
//        lp.topMargin = 5;
//        lp.bottomMargin = 5;
        LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        MarginLayoutParams marginLayoutParams= new MarginLayoutParams(lp.width,lp.height);
        marginLayoutParams.setMargins(10,10,10,10);
//        lp.leftMargin = 5;
//        lp.rightMargin = 5;
//        lp.topMargin = 5;
//        lp.bottomMargin = 5;
        for(int i = 0; i <list_reward.size(); i ++){
//            LinearLayout linearLayout=new LinearLayout(context);
//            TextView view = new TextView(this);
//            view.setText(mNames[i]);
//            view.setTextColor(getResources().getColor(R.color.weather_title));
//            view.setTextSize(14);
//            view.setPadding(0,5,0,5);
//            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_remark_bg_purple_top));
//            TextView view1 = new TextView(this);
//            view1.setText(mNames[i]);
//            view1.setTextColor(getResources().getColor(R.color.weather_title));
//            view1.setTextSize(14);
//            view1.setPadding(0,5,0,5);
//            view1.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_remark_bg_purple_top));
//            linearLayout.setPadding(5,5,5,5);
//            linearLayout.addView(view);
//            linearLayout.addView(view1);
            View linear=LayoutInflater.from(context).inflate(R.layout.reward_one,null);
            LinearLayout linear_bg= (LinearLayout) linear.findViewById(R.id.reward_one_linear);
            if(list_reward.get(i).isResponse_status()){
                linear_bg.setBackgroundResource(R.drawable.tab_reward_bgcircle_on);
                TextView text_keyword= (TextView) linear.findViewById(R.id.reward_one_keyword);
                text_keyword.setText(list_reward.get(i).getKeyword());
                TextView text_get_ok= (TextView) linear.findViewById(R.id.reward_one_ok);
                text_get_ok.setText("√");
                wuTagCloudLayout.addView(linear,marginLayoutParams);
            }else {
                TextView text_keyword= (TextView) linear.findViewById(R.id.reward_one_keyword);
                text_keyword.setText(list_reward.get(i).getKeyword());
                TextView text_money= (TextView) linear.findViewById(R.id.reward_one_money);
                text_money.setText(list_reward.get(i).getRmoney()+"");
                wuTagCloudLayout.addView(linear,marginLayoutParams);
            }
        }
        wuTagCloudLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Maintab.this, WuRewardWallet.class);
                Bundle bundle=new Bundle();
                bundle.putString("weiboId",other.getId());
                bundle.putSerializable("otherCopy",other);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
//        View linear_add=LayoutInflater.from(context).inflate(R.layout.tab_reward_add,null);
//        wuTagCloudLayout.addView(linear_add,marginLayoutParams);
       // wuTagCloudLayout.invalidate();

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

    private void initView() {
        context=this;
        mViewPager= (ViewPager) findViewById(R.id.maintab_viewpager);
        mViewPager.setPageTransformer(true,new WuBannerPageTransformer(2));
        mTabLayout=(TabLayout)findViewById(R.id.maintab_tabs);
        //textview= (TextView) findViewById(R.id.maintab_id);
        //img_head= (ImageView) findViewById(R.id.activity_maintab_img);
        Intent it=getIntent();
        Bundle bundle=it.getExtras();
        other= (OtherCopy) bundle.getSerializable("othercopy");
         mObjectId=bundle.getString("objectid");
        Log.i(TAG,"are you crazy?"+other.getObjectId());
        //textview.setText(other.getId());
        mInflater=LayoutInflater.from(this);
        view_context=mInflater.inflate(R.layout.tab_context,null);
        view_data=mInflater.inflate(R.layout.tab_data,null);
        view_remark=mInflater.inflate(R.layout.tab_remark,null);
       // listview_context= (ListView) view_context.findViewById(R.id.tab_context_recyclerview);
        recyclerView_context= (RecyclerView) view_context.findViewById(R.id.tab_context_recyclerview);
//        recyclerView_context.setOnTouchListener(new View.OnTouchListener() {
//            float oldy=0;
//            float collapsing_heigh1=0;
//            CollapsingToolbarLayout.LayoutParams lp1=new CollapsingToolbarLayout.LayoutParams(collapsing_width,collapsing_heigh);
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if((event.getY()-oldy)!=event.getY()) {
//                    lp1.height= (int) (collapsing_heigh1+event.getY() - oldy);
//                    maintab_bg.setLayoutParams(lp1);
//                    collapsing_heigh1=lp1.height;
////                    ObjectAnimator anim = ObjectAnimator.ofFloat(ctl, "y", ctl.getY(), ctl.getY() + event.getY() - oldy);
////                    anim.setDuration(100);
////                    anim.start();
//                    Log.i(TAG, event.getY() + "event");
//                }
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.i(TAG, event.getY() + "上滑");
//                }
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    Log.i(TAG, event.getY() + "上滑1");
//                }
//                oldy=event.getY();
//                return false;
//            }
//        });
        text_all= (TextView) view_data.findViewById(R.id.tab_data_all);
        text_wuru= (TextView) view_data.findViewById(R.id.tab_data_wuru);
        text_qishi= (TextView) view_data.findViewById(R.id.tab_data_qishi);
        text_liaosao= (TextView) view_data.findViewById(R.id.tab_data_liaosao);
        text_feibang= (TextView) view_data.findViewById(R.id.tab_data_feibang);
        wucircle= (WuCircularStatistics) view_data.findViewById(R.id.tab_data_circle);
        ctl= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        maintab_bg= (ImageView) findViewById(R.id.maintab_bg);
        img_head=(ImageView)findViewById(R.id.maintab_imghead);
        // 头部数据填充
        TextView tvname= (TextView) findViewById(R.id.maintab_name);
      //  tvname.setShadowLayer(10F,4F,4F,Color.BLACK);
        final ImageView imgback= (ImageView) findViewById(R.id.maintab_backbutton);
        final ImageButton imgbmore= (ImageButton) findViewById(R.id.maintab_morebutton);
        imgback.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:imgback.setImageResource(R.mipmap.userinfo_buttonicon_back_highlighted);break;
                    case MotionEvent.ACTION_UP:imgback.setImageResource(R.mipmap.userinfo_buttonicon_back);Maintab.this.finish();break;
                }
                return false;
            }
        });
        imgbmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openpopupwindow();
            }
        });
        FrameLayout frameLayout_reward= (FrameLayout) view_data.findViewById(R.id.tab_data_reward);
        frameLayout_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(Maintab.this, WuRewardWallet.class);
                Bundle bundle=new Bundle();
                bundle.putString("weiboId",other.getId());
                bundle.putSerializable("othercopy",other);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
        TextView tvdescription= (TextView) findViewById(R.id.maintab_descripition);
        TextView tvmblog= (TextView) findViewById(R.id.maintab_mblog);
        TextView tvfuns= (TextView) findViewById(R.id.maintab_funs);
        ImageView imgsex= (ImageView) findViewById(R.id.maintab_sex);
        WuUrlGetString wuUrlGetString=new WuUrlGetString("http://weibo.com/"+other.getId()+"/profile?topnav=1&wvr=6&is_all=1");
        Log.i(TAG,"http://weibo.com/"+mObjectId+"/profile?topnav=1&wvr=6&is_all=1");
        wuUrlGetString.setTextMblog(tvmblog);
        wuUrlGetString.setTextfun(tvfuns);
        wuUrlGetString.setTextName(tvname);
        wuUrlGetString.setTextDescription(tvdescription);
        wuUrlGetString.setImagebg(maintab_bg);
        wuUrlGetString.setImagehead(img_head);
        wuUrlGetString.setImagesex(imgsex);
        wuUrlGetString.requestURLString();
       // wuUrlGetString.setUrlImage("http://tva4.sinaimg.cn/crop.0.0.1080.1080.127/68690a6cjw8esw2dzuic7j20u00u0go6.jpg",maintab_bg);
       // wuUrlGetString.getHtml("http://weibo.com/5242909969/profile?topnav=1&wvr=6&is_all=1","post",100500);
        //加载动画
//        Animation anim= AnimationUtils.loadAnimation(this,R.anim.bird_fly);
//        LinearInterpolator li=new LinearInterpolator();
//        anim.setInterpolator(li);
        //背景旋转
//        Animation anim_rorate=AnimationUtils.loadAnimation(this,R.anim.pic_rorate);
//        LinearInterpolator li=new LinearInterpolator();
//        anim_rorate.setInterpolator(li);
//        ImageView img_bg= (ImageView) findViewById(R.id.backdrop);
//        img_bg.startAnimation(anim_rorate);
        //加载初始化
        ImageView img_context_loading= (ImageView) view_context.findViewById(R.id.tab_context_loading_anim);
        AnimationDrawable anim_context_loading= (AnimationDrawable) img_context_loading.getDrawable();
        anim_context_loading.start();
        ImageView img_data_loading= (ImageView) view_data.findViewById(R.id.tab_data_loading_anim);
        AnimationDrawable anim_data_loading= (AnimationDrawable) img_data_loading.getDrawable();
        anim_data_loading.start();
        context_loading_linear= (LinearLayout) view_context.findViewById(R.id.tab_context_linear_loading);
        data_loading_linear= (LinearLayout) view_data.findViewById(R.id.tab_data_linear_loading);
        //锚点
        LinearBehavior wulinear=new LinearBehavior();
        LinearLayout linear_tab= (LinearLayout) view_context.findViewById(R.id.activity_linear_tab);
        //评论初始化
        initremark();
        //添加view集合
        list_view.add(view_context);
        list_view.add(view_data);
        list_view.add(view_remark);
        list_title.add("主页");
        list_title.add("数据");
        list_title.add("评论");
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(list_title.get(2)));
        WuViewpagerAdapter mAdapter=new WuViewpagerAdapter(list_view,list_title);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG,position+"postion_linear");
                switch (position){
                    case 0:linear_remark_write.setVisibility(View.INVISIBLE); break;
                    case 1:linear_remark_write.setVisibility(View.INVISIBLE);break;
                    case 2:linear_remark_write.setVisibility(View.VISIBLE);if(remark_bottom_status){remark_bottom_show=linear_remark_write.getY();remark_bottom_hide=remark_bottom_show+linear_remark_write.getHeight();remark_bottom_status=false;}break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void initremark() {
        myUpload=new MyUpload(this);
        recyclerView_remark= (RecyclerView) view_remark.findViewById(R.id.tab_remark_recyclerview);
        linear_remark_write= (LinearLayout) findViewById(R.id.activity_maintab_linear_remark);
        linear_remark_write.setVisibility(View.INVISIBLE);
        tab_remark_edit= (EditText) findViewById(R.id.tab_remark_write);
        ImageView img_send= (ImageView) findViewById(R.id.tab_remark_send);
        img_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkuser()){
                    WuRemark wuRemark=new WuRemark(tab_remark_edit.getText().toString(),user_id,0,0);
                    wuRemark.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                Log.i(TAG,"成功了！！！！！！"+tab_remark_edit.getText().toString()+user_id);
                                Snackbar.make(view_remark,"评论成功！",Snackbar.LENGTH_SHORT).show();
                                other_jundge=1;
                                otherupdata(mObjectId,s.toString());
                            }else{
                                Log.i(TAG,"出错了？？？？？？");
                                other_jundge=2;
//                                otherupdata(other.getId(),context_id_str[0]);
                            }
                        }
                    });
                }
            }
        });
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_remark.setLayoutManager(staggeredGridLayoutManager);
        recyclerView_remark.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.i(TAG,"dy"+dy);
                boolean isSignificantDelta = Math.abs(dy) > 10;
                if (isSignificantDelta) {
                    if (dy>0&&stausa==true) {
                        hideTools();
                        stausa=false;
                        Log.i(TAG,"stausa"+stausa);
                    } else if(dy<0&&stausa==false){
                        showTools();
                        stausa=true;
                        Log.i(TAG,"stausa"+stausa);
                    }
                }
            }
        });
        ArrayList<String> list_remark=new ArrayList<>();
        list_remark=other.getRemarkid_list();
        if(list_remark!=null&&list_remark.size()!=0) {
            wuRemarkRecycleAdapter = new WuRemarkRecycleAdapter(this, list_remark);
            recyclerView_remark.setItemAnimator(new DefaultItemAnimator());
            recyclerView_remark.setAdapter(wuRemarkRecycleAdapter);
//            wuRemarkRecycleAdapter.setOnRecyclerItemClickListener(new WuRemarkRecycleAdapter.OnRecyclerItemClickListener() {
//                @Override
//                public void onItemclick(WuRemarkRecycleAdapter.MyViewHolder view, int postion) {
//                    Log.i(TAG,postion+"postion"+"点了");
//                    wuRemarkRecycleAdapter.addGreat(view,postion);
//                }
//            });
//            wuRemarkRecycleAdapter.setOnRecyclerItemLongClickListener(new WuRemarkRecycleAdapter.OnRecyclerItemLongClickListener() {
//                @Override
//                public void onItemLongclick(WuRemarkRecycleAdapter.MyViewHolder view, int postion) {
//                    Log.i(TAG,postion+"postion"+"点了长");
//                    wuRemarkRecycleAdapter.addBad(view,postion);
//                }
//            });
//        }
            //initData();
            findreward();
            wuRemarkRecycleAdapter.setOnRecyclerGreatClickListener(new WuRemarkRecycleAdapter.OnRecyclerGreatClickListener() {
                @Override
                public void onGreatClick(WuRemarkRecycleAdapter.MyViewHolder view, int postion) {
                    wuRemarkRecycleAdapter.addGreat(view, postion);
                }
            });
            wuRemarkRecycleAdapter.setOnRecyclerBadClickListener(new WuRemarkRecycleAdapter.OnRecyclerBadClickListener() {
                @Override
                public void onBadClick(WuRemarkRecycleAdapter.MyViewHolder view, int postion) {
                    wuRemarkRecycleAdapter.addBad(view, postion);
                }
            });
        }
    }

    private void initContext(){
        ArrayList<String> list_contextid=new ArrayList<String>();
        list_contextid=other.getContext_id();
        lock_num=list_contextid.size();
        if(lock_num!=-1){
            for(int i=0;i<lock_num;i++){
                findContext(list_contextid.get(i),i);
            }
        }
    }
    private void findContext(String str_objectId,int i){
        query = new BmobQuery<WuContext>();
        query.getObject(str_objectId, new QueryListener<WuContext>() {

            @Override
            public void done(WuContext object, BmobException e) {
                if(e==null){
                   list_context.add(object);
                    if(list_context.size()==lock_num){
                        if(updataContext()){
                            msetlistAdatper();
                        }
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
            }

        });
    }

    private void msetlistAdatper() {
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL);
        recyclerView_context.setLayoutManager(staggeredGridLayoutManager);
        mcontextAdapter=new WuContextRecyclerViewAdapter(this,list_context);
        recyclerView_context.setAdapter(mcontextAdapter);
//        listview_context.setAdapter(new Maincontext_Adapter(this,alist_context,alist_time,alist_level,alist_writer,alist_num,alist_numURL));
        context_loading_linear.setVisibility(View.INVISIBLE);
        updatetab2();
        dnowPicture();
    }

    private boolean updataContext() {
        final SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        final Date[] data1 = {null};
        final Date[] data2 = {null};
        for (int i=0;i<list_context.size();i++){
            Log.i(TAG,list_context.get(i).getCreatedAt()+"日期");
        }
        Comparator<WuContext> comparator = new Comparator<WuContext>(){
            public int compare(WuContext s1, WuContext s2) {
                //排序日期
                    try {
                        data1[0] =sdf.parse(s1.getCreatedAt());
                        data2[0] =sdf.parse(s2.getCreatedAt());
                    } catch (ParseException e) {
                        Log.i(TAG,"wenti");
                        e.printStackTrace();
                    }
                if(data1[0].getTime()> data2[0].getTime()){
                    return -1;
                }else {
                    return 1;
                }
            }
        };
        if(list_context.size()>1){
            Collections.sort(list_context,comparator);
        }
        return true;
    }
    private void updatetab2(){
         level_count=new int[]{0,0,0,0};
        for(int m=0;m<list_context.size();m++){
            switch (list_context.get(m).getFakelevel()){
                case 0:level_count[0]++;break;
                case 1:level_count[1]++;break;
                case 2:level_count[2]++;break;
                case 3:level_count[3]++;break;
            }
        }
        for(int i=0;i<level_count.length;i++){
            Log.i(TAG,level_count[i]+"  条");
        }
        text_all.setText(list_context.size()+"");
        text_wuru.setText(level_count[0]+"");
        text_qishi.setText(level_count[2]+"");
        text_liaosao.setText(level_count[3]+"");
        text_feibang.setText(level_count[1]+"");
        wucircle.setListData(new int[]{level_count[0],level_count[1],level_count[3],level_count[2]});
        wucircle.invalidate();
        data_loading_linear.setVisibility(View.INVISIBLE);
    }
    private void dnowPicture(){
//
    }
    private File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }
    private void updatareward(){
        WuReward wureward=new WuReward(false,"工资多少","他骂我妈","110","120",23,"wf",2);
        wureward.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                }
            }
        });
    }
    private void findreward(){
        list_reward_str=new ArrayList<>();
        Log.i(TAG," zhixingdaozhebule");
        if(other.getRewardid_list()!=null){
            list_reward_str=other.getRewardid_list();
            Log.i(TAG,list_reward_str.size()+"list_reward_stref");
        }else {
            Log.i(TAG," chucisnxvuioerge");
        }
        final String[] a = new String[1];
        BmobQuery<WuReward> query_reward;
        for(int i=0;i<list_reward_str.size();i++){
            query_reward = new BmobQuery<WuReward>();
            Log.i(TAG,list_reward_str.get(i)+" list_reward_str.get(i)");
            query_reward.getObject(list_reward_str.get(i), new QueryListener<WuReward>() {
                @Override
                public void done(WuReward object, BmobException e) {
                    if(e==null){
                        list_reward.add(object);
                        Log.i(TAG,list_reward.size()+"ef");
                        if(list_reward.size()== list_reward_str.size()){
                            updataReward();
                            Log.i(TAG,list_reward.size()+"ef"+list_reward_str.size());
                        }
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }

            });
        }
    }

    private void updataReward() {
        initData();
    }

    private boolean checkuser() {
        TreeUser bmobUser = BmobUser.getCurrentUser(TreeUser.class);
        if(bmobUser != null){
            // 允许用户使用应用
            user_id=bmobUser.getObjectId();
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Intent it=new Intent(this,LoginActivity.class);
            startActivity(it);
            return false;
        }
    }
    private void otherupdata(String id,String str) {
        boolean have_bl=true;
        ArrayList<String> list_remark=new ArrayList<String>();
        if(other_jundge==1){
            if(other.getRemarkid_list()==null){
                Log.i(TAG,"出错了 null？");
                have_bl=false;
            }else{
                list_remark=other.getRemarkid_list();
            }
//            if(other.getRemarkid_list().size()==0){
//                Log.i(TAG,"出错了？000？");
//            }
            if(have_bl){
                if(list_remark!=null&&list_remark.size()!=0){
                    list_remark.add(0,str);
                    //更新Person表里面id为6b6c11c537的数据，address内容更新为“北京朝阳”
                    Other pp = new Other();
                    pp.setValue("remarkid_list",list_remark);
                    Log.i(TAG,"id"+id);
                    pp.update(id, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Log.i(TAG,"更新成功了？？？");
                            }else{
                                Log.i(TAG,"更新出错了？？？"+e.getErrorCode()+e.toString());
                            }
                        }

                    });
                }
            }else {
                Log.i(TAG,"更新没有");
                list_remark = new ArrayList<String>();
                list_remark.add(0,str);
                //更新Person表里面id为6b6c11c537的数据，address内容更新为“北京朝阳”
                Other pp = new Other();
                pp.setValue("remarkid_list",list_remark);
                pp.update(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                        }else{
                        }
                    }

                });}
            Log.i(TAG,"更新没有啊");
        }
    }
    /**
     * 显示工具栏
     */
    private void showTools() {

        ObjectAnimator anim = ObjectAnimator.ofFloat(linear_remark_write, "y",linear_remark_write.getY(),remark_bottom_show);
        anim.setDuration(600);
        anim.start();
        Log.i(TAG,"我没藏了");
    }

    /**
     * 隐藏工具栏
     */
    private void hideTools() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(linear_remark_write, "y",linear_remark_write.getY(),remark_bottom_hide);
        anim.setDuration(600);
        anim.start();
        Log.i(TAG,"我藏了");
    }
    private View.OnClickListener onClicklistenner=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.popupwindow_collection:break;//收藏
                case R.id.popupwindow_frinend_circle:T.showShot(context,"- 暂未实现此接口 -");break;//生活圈
                case R.id.popupwindow_info:break;//基本信息
                case R.id.popupwindow_link:ClipboardManager cm= (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);cm.setText(MyUrlGet.getWeiboLink(mObjectId));T.showShot(context,"- 已复制 -");break;//复制链接
                case R.id.popupwindow_qq:T.showShot(context,"- 暂未实现此接口 -");break;//QQ
                case R.id.popupwindow_qzone:T.showShot(context,"- 暂未实现此接口 -");break;//QQ空间
                case R.id.popupwindow_reward:break;//悬赏
                case R.id.popupwindow_url:Intent it=new Intent(Intent.ACTION_VIEW);
                    it.setData(Uri.parse(MyUrlGet.getWeiboLink(mObjectId)));
                    startActivity(it);break;//主页
                case R.id.popupwindow_weibo:T.showShot(context,"- 暂未实现此接口 -");break;//微博
                case R.id.popupwindow_weixin:T.showShot(context,"- 暂未实现此接口 -");break;//微信
                case R.id.popupwindow_weixin_friend:T.showShot(context,"- 暂未实现此接口 -");break;//朋友圈
                case R.id.popupwindow_zhifubao:T.showShot(context,"- 暂未实现此接口 -");break;//支付宝
                case R.id.popopwindow_lineartop:mPopupWindows.dismiss();break;
            }
        }
    };
    private void openpopupwindow() {
        //外部变暗
        if(width==-1&&height==-1){
            WindowManager windowmanager=this.getWindowManager();
             width=windowmanager.getDefaultDisplay().getWidth();
             height=windowmanager.getDefaultDisplay().getHeight();
        }
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 0.5f;
        this.getWindow().setAttributes(params);
        mPopupWindows = new PopupWindowMainTab(context,onClicklistenner,width,height);
        mPopupWindows.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = Maintab.this.getWindow().getAttributes();
                params.alpha = 1f;
                Maintab.this.getWindow().setAttributes(params);
            }
        });
        //出问题了
        mPopupWindows.showAtLocation(Maintab.this.findViewById(R.id.main_content), Gravity.BOTTOM , 0, 0);
    }
    private void closepopupwindow() {
        mPopupWindows.dismiss();
        // WindowManager.LayoutParams params=this.getWindow().getAttributes();
        //params.alpha=1f;
        //this.getWindow().setAttributes(params);
    }
    public OtherCopy getOther(){
        return other;
    }
    @Override
    protected void onStart() {
        collapsing_heigh=maintab_bg.getHeight();
        collapsing_width=maintab_bg.getWidth();
        collapsing_Y=maintab_bg.getY();
        super.onStart();
    }
}
