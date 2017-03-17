package treememory.com.treememory.MenuItemActivity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import treememory.com.treememory.MainInfor.OtherCopy;
import treememory.com.treememory.OnlineData.WuContext;
import treememory.com.treememory.OnlineData.WuReward;
import treememory.com.treememory.R;
import treememory.com.treememory.Utils.BitmapUtil;
import treememory.com.treememory.Utils.L;
import treememory.com.treememory.Utils.StreamUtils;
import treememory.com.treememory.wustringparsing.MyUrlGet;

/**
 * Created by Administrator on 2017/2/10.
 */

public class WuRewardWallet extends Activity implements View.OnClickListener{
    private String TAG="WuRewardWallet";
    private TextView text_done;
    private TextView text_will;
    private Fragment fragmentdone;
    private Fragment fragmentwill;
    private FragmentTransaction ft;
    private FragmentManager fmmanager;
    private ImageView imghead;
    private Boolean done_status=false;
    private String weibo_url_str;
    private OtherCopy other;
    private BmobQuery<WuReward> query;
    private ArrayList<WuReward> list_reward=new ArrayList<>();
    private ArrayList<String> rewardid_list;
    private boolean load_reward_lock=false;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    L.i(TAG,"执行到了吗");imghead.setImageBitmap(BitmapUtil.toRoundBitmap((Bitmap)msg.obj));break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wurewardwallet);
        initdata();
        initView();
        updateView();
    }

    private void updateView() {
        for(int i=0;i<rewardid_list.size();i++){
            findReward(rewardid_list.get(i));
        }
    }

    private void initdata() {
        Intent it=this.getIntent();
        Bundle bundle=it.getExtras();
        weibo_url_str= (String) bundle.get("weiboId");
        other= (OtherCopy) bundle.getSerializable("otherCopy");
        rewardid_list=other.getRewardid_list();
        fragmentdone=new FragmentDone();
        fragmentwill=new FragmentWill();
    }

    private void findReward(String rewared_id_str) {
        query=new BmobQuery<>();
        L.i(TAG,rewared_id_str);
        query.getObject(rewared_id_str, new QueryListener<WuReward>() {
            @Override
            public void done(WuReward wuReward, BmobException e) {
                if(e==null){
                    list_reward.add(wuReward);
                    L.i(TAG,"下载成功");
                    if(list_reward.size()==rewardid_list.size()){
                        load_reward_lock=true;
                        if(done_status){
                            updataFragment(done_status);
                        }else {
                            updataFragment(done_status);
                        }

                    }
                }else {
                    L.i(TAG,"下载失败"+e.toString());
                }
            }
        });
    }

    private void updataFragment(boolean doneorwill) {
        ArrayList<WuReward> list_will=new ArrayList<>();
        ArrayList<WuReward> list_done=new ArrayList<>();
        for(int i=0;i<list_reward.size();i++){
            if(!list_reward.get(i).isResponse_status()){
                list_will.add(list_reward.get(i));
            }else {
                list_done.add(list_reward.get(i));
            }
        }
        ArrayList<String> keyward=new ArrayList<>();
        ArrayList<String> rcontext=new ArrayList<>();
        ArrayList<String> response_id=new ArrayList<>();
        ArrayList<String> release_id=new ArrayList<>();
        ArrayList<Integer> rmoney=new ArrayList<>();
        ArrayList<String> expire_date=new ArrayList<>();
        ArrayList<Integer> photograph=new ArrayList<>();
        if(doneorwill){
            for(int i=0;i<list_done.size();i++){
                keyward.add(list_done.get(i).getKeyword());
                rcontext.add(list_done.get(i).getRcontext());
                response_id.add(list_done.get(i).getResponse_id());
                release_id.add(list_done.get(i).getRelease_id());
                rmoney.add(list_done.get(i).getRmoney());
                expire_date.add(list_done.get(i).getExpire_date());
                photograph.add(list_done.get(i).getPhotograph());
            }
        }else {
            for(int i=0;i<list_will.size();i++){
                keyward.add(list_will.get(i).getKeyword());
                rcontext.add(list_will.get(i).getRcontext());
                response_id.add(list_will.get(i).getResponse_id());
                release_id.add(list_will.get(i).getRelease_id());
                rmoney.add(list_will.get(i).getRmoney());
                expire_date.add(list_will.get(i).getExpire_date());
                photograph.add(list_will.get(i).getPhotograph());
            }
        }
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("keyward",keyward);
        bundle.putStringArrayList("rcontext",rcontext);
        bundle.putStringArrayList("response_id",response_id);
        bundle.putStringArrayList("release_id",release_id);
        bundle.putIntegerArrayList("rmoney",rmoney);
        bundle.putStringArrayList("expire_date",expire_date);
        bundle.putIntegerArrayList("photograph",photograph);
        if(doneorwill){
            fragmentdone.setArguments(bundle);
        }else {
            fragmentwill.setArguments(bundle);
        }
        initfragment();
    }

    private void initfragment() {
        fmmanager=getFragmentManager();
        if(done_status){
            ft=fmmanager.beginTransaction();
            ft.replace(R.id.wurewardwallet_fragment_line,fragmentdone);
            ft.commit();
            L.i(TAG,"执行到了 done");
        }else {
            ft=fmmanager.beginTransaction();
            ft.replace(R.id.wurewardwallet_fragment_line,fragmentwill);
            ft.commit();
            L.i(TAG,"执行到了 will");
        }
    }

    private void initView() {
        text_done= (TextView) findViewById(R.id.wurewardwallet_done);
        text_will= (TextView) findViewById(R.id.wurewardwallet_will);
        imghead= (ImageView) findViewById(R.id.rewardwaller_imghead);
        ImageView addreward= (ImageView) findViewById(R.id.wurewardwallet_add);
        addreward.setOnClickListener(this);
        text_done.setOnClickListener(this);
        text_will.setOnClickListener(this);
        updateimghead();
    }
    private void updateimghead(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   URL weibo_url=new URL("http://weibo.com/"+weibo_url_str+"/profile?topnav=1&wvr=6&is_all=1");
                   HttpURLConnection httpurlconnection_weibo= (HttpURLConnection) weibo_url.openConnection();
                   httpurlconnection_weibo.setRequestMethod("GET");
                   httpurlconnection_weibo.setConnectTimeout(1000*20);
                   int code_weibo=httpurlconnection_weibo.getResponseCode();
                   L.i(TAG,"1");
                   if(code_weibo==200){
                       InputStream is=httpurlconnection_weibo.getInputStream();
                       String imgheadurl=StreamUtils.streamToString(is);
                       URL head_url=new URL(MyUrlGet.getImageHeadUrl(imgheadurl));
                       HttpURLConnection httpurlconnection= (HttpURLConnection) head_url.openConnection();
                       httpurlconnection.setRequestMethod("GET");
                       httpurlconnection.setConnectTimeout(1000*20);
                       int code=httpurlconnection.getResponseCode();
                       if(code==200){
                           L.i(TAG,"1");
                           InputStream inputstream=httpurlconnection.getInputStream();
                           Bitmap bitmap=StreamUtils.streamToBitmap(inputstream);
                           Message msg=Message.obtain();
                           msg.what=1;
                           msg.obj=bitmap;
                           mhandler.sendMessage(msg);
                           L.i(TAG,"1");
                       }
                   }
               }catch (Exception exception){
                   L.i(TAG,"出错了吗？");
               }
           }
       }).start();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wurewardwallet_will:
                if(done_status){
                    if(load_reward_lock){
//                        Bundle bundle=new Bundle();
//                        bundle.putSerializable("otherCopy",other);
//                        fragmentwill.setArguments(bundle);
                        ft=fmmanager.beginTransaction();
                        ft.replace(R.id.wurewardwallet_fragment_line,fragmentwill);
                        ft.commit();
                    }
                    text_will.setTextColor(getResources().getColor(R.color.mp_blue));
                    text_will.setBackgroundResource(R.drawable.reward_wallet_top_bg_normal2);
                    text_done.setTextColor(getResources().getColor(R.color.white));
                    text_done.setBackgroundResource(R.drawable.reward_wallet_top_bg_press);
                    done_status=!done_status;
                }break;
            case R.id.wurewardwallet_done:
                if(!done_status){
                    if(load_reward_lock){
                        ft=fmmanager.beginTransaction();
                        ft.replace(R.id.wurewardwallet_fragment_line,fragmentdone);
                        ft.commit();
                    }
                    text_done.setTextColor(getResources().getColor(R.color.mp_blue));
                    text_done.setBackgroundResource(R.drawable.reward_wallet_top_bg_press2);
                    text_will.setTextColor(getResources().getColor(R.color.white));
                    text_will.setBackgroundResource(R.drawable.reward_wallet_top_bg_normal);
                    done_status=!done_status;
                }break;
            case R.id.wurewardwallet_add:Intent it=new Intent();
                it.setClass(WuRewardWallet.this,RewardAdd.class);
                Bundle bundle=new Bundle();
                bundle.putString("weiboId",weibo_url_str);
                it.putExtras(bundle);
                startActivity(it);
                break;
        }
    }
}
