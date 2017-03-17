package treememory.com.treememory.MenuItemActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import treememory.com.treememory.MainInfor.OtherCopy;
import treememory.com.treememory.OnlineData.Other;
import treememory.com.treememory.OnlineData.WuReward;
import treememory.com.treememory.R;
import treememory.com.treememory.Utils.L;

/**
 * Created by Administrator on 2017/3/8.
 */

public class RewardAdd extends Activity implements View.OnClickListener{
    private String TAG="RewardAdd";
    private EditText edit_key;
    private EditText edit_description;
    private EditText edit_money;
    private Spinner spinner_date;
    private Button button_back;
    private Button button_ok;
    private boolean uploadreward_status;
    private boolean downother_status;
    private String weiboId;
    private Other mother;
    private OtherCopy othercopy;
    private String backid;
    private int reward_money=7;
    OtherCopy other;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wurewardadd);
        initdata();
        initView();
    }

    private void initdata() {
        Intent it=this.getIntent();
        Bundle bundle=it.getExtras();
        other= (OtherCopy) bundle.getSerializable("othercopy");
        weiboId= (String) bundle.get("weiboId");
    }

    private void initView() {
        edit_key= (EditText) findViewById(R.id.rewardadd_key);
        edit_description= (EditText) findViewById(R.id.rewardadd_description);
        edit_money= (EditText) findViewById(R.id.rewardadd_money);
        spinner_date= (Spinner) findViewById(R.id.rewardadd_date);
        button_back= (Button) findViewById(R.id.rewardadd_back);
        button_ok= (Button) findViewById(R.id.rewardadd_ok);
        button_ok.setOnClickListener(this);
        button_back.setOnClickListener(this);
        spinner_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reward_money=7+position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rewardadd_back:RewardAdd.this.finish();break;
            case R.id.rewardadd_ok:uploading();break;
        }
    }
    public void uploading(){
        WuReward wureward=new WuReward(false,edit_key.getText().toString(),edit_description.getText().toString(),"-1","-1",17,"26",0);
        wureward.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    uploadreward_status=true;
                    backid=s;
                    otherupdata("1",backid);
                    L.i(TAG,"ward上传成功");
                }else {
                    L.i(TAG,"ward上传失败"+e.toString());
                }
            }
        });
        BmobQuery<Other> query = new BmobQuery<Other>();
//查询playerName叫“比目”的数据
        query.addWhereEqualTo("id",weiboId);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        // query.setLimit(1);
//执行查询方法
        query.findObjects(new FindListener<Other>() {
            @Override
            public void done(List<Other> object, BmobException e) {
                if(e==null){
                    // toast("查询成功：共"+object.size()+"条数据。");
                    downother_status=true;
                    mother=object.get(0);
                    otherupdata("1",backid);
                    // otherupdata(id,context_id_str[0]);
                    Log.i(TAG,"成功：");
                }else{
                    Log.i(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    private void otherupdata(String id,String str) {
        if(uploadreward_status&&downother_status){
            ArrayList<String> list_other;
            if(mother.getRewardid_list()==null){
                list_other=new ArrayList<String>();
            }else {
                list_other=mother.getRewardid_list();
            }
            list_other.add(0,str);
            //更新Person表里面id为6b6c11c537的数据，address内容更新为“北京朝阳”
            Other pp = new Other();
            pp.setValue("rewardid_list",list_other);
            pp.update(mother.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        Log.i(TAG,"更新成功！");
                    }else{
                        Log.i(TAG,"更新失败！");
                    }
                }

            });
        }
    }
}
