package treememory.com.treememory.KeySearch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import treememory.com.treememory.MainInfor.Maintab;
import treememory.com.treememory.MainInfor.OtherCopy;
import treememory.com.treememory.OnlineData.Other;
import treememory.com.treememory.OnlineData.TreeAllMore;
import treememory.com.treememory.R;
import treememory.com.treememory.WuUpload.WuSdcard;


/**
 * Created by Administrator on 2017/1/14.
 */

public class WuSearch extends Activity implements View.OnClickListener{
    private final Other other=new Other();
    private String TAG="WuSearchWuSearchh";
    private ListView lv;
    private SearchView sv;
    private ArrayList<String> list_data=new ArrayList<String>();
    private ArrayList<String> list_search=new ArrayList<String>();
    private String seracgurl= WuSdcard.getMysdcard().getPathsearchtxt()+ File.separator+"search.txt";
    private Search_adapter msearch_adapter;
    private  View footview;
    private TextView more_text1;
    private TextView more_text2;
    private TextView more_text3;
    private TextView more_text4;
    private TextView more_text5;
    private TextView more_text6;
    private TextView more_text7;
    private TextView more_text8;
    private TextView foottext;
    private TextView sv_textView;
    private LinearLayout list_footview_linear;
    private WuProcessDialog wuProcessDialog;
    private BmobQuery<TreeAllMore> tam;
    private ArrayList<String> list_more=new ArrayList<>();
    private Handler hander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i=msg.what;
            if(i==1){more_text1.setText(
                    list_more.get(0)+"");
                more_text2.setText(list_more.get(1)+"");
                more_text3.setText(list_more.get(2)+"");
                more_text3.setTextColor(WuSearch.this.getResources().getColor(R.color.blue_level));
                more_text4.setText(list_more.get(3)+"");
                more_text4.setTextColor(WuSearch.this.getResources().getColor(R.color.red_level));
                more_text5.setText(list_more.get(4)+"");
                more_text5.setTextColor(WuSearch.this.getResources().getColor(R.color.green_level));
                more_text6.setText(list_more.get(5)+"");
                more_text6.setTextColor(WuSearch.this.getResources().getColor(R.color.yello_level));
                more_text7.setText(list_more.get(6)+"");
                more_text8.setText(list_more.get(7)+"");
                list_footview_linear.setVisibility(View.VISIBLE);}
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setOnHead();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        read();
        initView();
        initfootView();
        findTreeAllMore("fzPY888M");
    }

    private void initfootView() {
        more_text1= (TextView) footview.findViewById(R.id.list_foot_text1);
        more_text2= (TextView) footview.findViewById(R.id.list_foot_text2);
        more_text3= (TextView) footview.findViewById(R.id.list_foot_text3);
        more_text4= (TextView) footview.findViewById(R.id.list_foot_text4);
        more_text5= (TextView) footview.findViewById(R.id.list_foot_text5);
        more_text6= (TextView) footview.findViewById(R.id.list_foot_text6);
        more_text7= (TextView) footview.findViewById(R.id.list_foot_text7);
        more_text8= (TextView) footview.findViewById(R.id.list_foot_text8);
        more_text1.setOnClickListener(this);
        more_text2.setOnClickListener(this);
        more_text3.setOnClickListener(this);
        more_text4.setOnClickListener(this);
        more_text5.setOnClickListener(this);
        more_text6.setOnClickListener(this);
        more_text7.setOnClickListener(this);
        more_text8.setOnClickListener(this);
    }

    private void sv_settext() {
        //获取到TextView的ID
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        //获取到TextView的控件
        sv_textView = (TextView) sv.findViewById(id);
    }

    private void initView() {
        footview= View.inflate(this,R.layout.list_footview,null);
        list_footview_linear= (LinearLayout) footview.findViewById(R.id.list_foot_linear);
        list_footview_linear.setVisibility(View.INVISIBLE);
        if(list_data!=null&&list_data.size()!=0){
            foottext= (TextView) footview.findViewById(R.id.list_foottxt);
            foottext.setText("清除历史记录");
            foottext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreatea();
                }
            });
        }
        lv= (ListView) findViewById(R.id.search_list);
        msearch_adapter=new Search_adapter(this,list_data);
        lv.setAdapter(msearch_adapter);
        lv.addFooterView(footview);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG,id+"id");
                if(id==-1) {
                    // 点击的是headerView或者footerView
                }else{
                    int realPosition=(int)id;
                    // 响应代码
                    sv_textView.setText(list_data.get(realPosition));
                }
            }
        });
        wuProcessDialog=new WuProcessDialog(WuSearch.this);
        wuProcessDialog.setCanceledOnTouchOutside(false);
        sv= (SearchView) findViewById(R.id.search_view);
        sv.setQueryHint("微博地址");
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                record(query);
                wuProcessDialog.show();
                findid(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(TextUtils.isEmpty(newText)) {

                }else{

                }
                if(newText.length()>25){
                    sv_textView.setText(newText.substring(19));
                }
                return false;
            }
        });
        sv_settext();
    }

    private void record(String s) {
        for(int i=0;i<list_search.size();i++){
            if(s.equals(list_search.get(i))){
                list_search.remove(i);
            }
        }
        list_search.add(0,s);
        list_data=list_search;
            File file=new File(seracgurl);
            try {
                FileOutputStream fos=new FileOutputStream(file);
                ObjectOutputStream oos=new ObjectOutputStream(fos);
                oos.writeObject(list_search);
                oos.flush();
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    private void read(){
        File file=new File(seracgurl);
        try {
            FileInputStream fis=new FileInputStream(file);
            ObjectInputStream ois=new ObjectInputStream(fis);
            list_search= (ArrayList<String>) ois.readObject();
            list_data=list_search;
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void recreatea(){
        File file=new File(seracgurl);
        if(file.exists()){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        list_data.clear();
        msearch_adapter.notifyDataSetChanged();
        foottext.setText("暂无历史记录");
    }
    private void findid(String s){
        BmobQuery<Other> query = new BmobQuery<Other>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("id",s);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<Other>() {
            @Override
            public void done(List<Other> object, BmobException e) {
                if(e==null){
                  Toast.makeText(WuSearch.this,"找到"+object.get(0).getId(),Toast.LENGTH_SHORT).show();
                    OtherCopy otherCopy=new OtherCopy(object.get(0).getId(),object.get(0).getContext_id());
                    otherCopy.setRemarkid_list(object.get(0).getRemarkid_list());
                    otherCopy.setRewardid_list(object.get(0).getRewardid_list());
                    Intent it=new Intent(WuSearch.this, Maintab.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("othercopy",otherCopy);
                    bundle.putString("objectid",object.get(0).getObjectId());
                    it.putExtras(bundle);
                    startActivity(it);
                }else{
                    Toast.makeText(WuSearch.this,"没有该用户",Toast.LENGTH_SHORT).show();
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
                wuProcessDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(list_more!=null){
         if(list_more.size()==16){
             String s="";
             if(v.getId()==R.id.list_foot_text1){
                 s=list_more.get(8);
                 sv_textView.setText(list_more.get(0)+"");
             }else if(v.getId()==R.id.list_foot_text2){
                 s=list_more.get(9);
                 sv_textView.setText(list_more.get(1)+"");
             }else if(v.getId()==R.id.list_foot_text3){
                 s=list_more.get(10);
                 sv_textView.setText(list_more.get(2)+"");
             }else if(v.getId()==R.id.list_foot_text4){
                 s=list_more.get(11);
                 sv_textView.setText(list_more.get(3)+"");
             }else if(v.getId()==R.id.list_foot_text5){
                 s=list_more.get(12);
                 sv_textView.setText(list_more.get(4)+"");
             }else if(v.getId()==R.id.list_foot_text6){
                 s=list_more.get(13);
                 sv_textView.setText(list_more.get(5)+"");
             }else if(v.getId()==R.id.list_foot_text7){
                 s=list_more.get(14);
                 sv_textView.setText(list_more.get(6)+"");
             }else if(v.getId()==R.id.list_foot_text8){
                 s=list_more.get(15);
                 sv_textView.setText(list_more.get(7)+"");
             }
             if(s.length()!=0){
                 record(s);
                 wuProcessDialog.show();
                 findid(s);
               //  ["全是文字你点这里","有图片的点这里","还没有","还没有","还没有","还没有","还没有","还没有","5242909969","1445962081","1","1","1","1","1","1"]
             }
         }
        }
    }
    private void findTreeAllMore(String str_objectId){
        try{
            tam = new BmobQuery<TreeAllMore>();
            tam.getObject(str_objectId, new QueryListener<TreeAllMore>() {

                @Override
                public void done(TreeAllMore object, BmobException e) {
                    if(e==null){
                        list_more=object.getSearch_more_list();
                        Message msg=Message.obtain();
                        msg.what=1;
                        hander.sendMessage(msg);
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }

            });
        }catch (Exception e){}
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

    @Override
    protected void onStart() {
        super.onStart();
    }
}
