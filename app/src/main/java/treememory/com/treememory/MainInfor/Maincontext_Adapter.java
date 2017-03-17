package treememory.com.treememory.MainInfor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import treememory.com.treememory.R;
import treememory.com.treememory.WuUpload.MyUpload;

/**
 * Created by Administrator on 2017/1/21.
 */

public class Maincontext_Adapter extends BaseAdapter{
    MyUpload myUpload;
    private LayoutInflater mlayoutInflater;
    private ArrayList<String> list_context;
    private ArrayList<String> list_time;
    private ArrayList<String> list_writer;
    private ArrayList<Integer> list_level;
    private ArrayList<Integer> list_num;
    private ArrayList<String> list_numURL;
    private MyViewHolder wuViewHolder;
    private Context mcontext;
    public Maincontext_Adapter(Context mcontext, ArrayList<String> list_context,ArrayList<String> list_time,ArrayList<Integer> list_level,ArrayList<String> list_writer,ArrayList<Integer> list_num,ArrayList<String> list_numURL){
        mlayoutInflater=LayoutInflater.from(mcontext);
        this.mcontext=mcontext;
        this.list_context=list_context;
        this.list_time=list_time;
        this.list_level=list_level;
        this.list_writer=list_writer;
        this.list_num=list_num;
        this.list_numURL=list_numURL;
        myUpload=new MyUpload(mcontext);
    }
    @Override
    public int getCount() {
        return list_context.size();
    }

    @Override
    public Object getItem(int position) {
        return list_context.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            wuViewHolder=new MyViewHolder();
            convertView=mlayoutInflater.inflate(R.layout.list_context,null);
            wuViewHolder.context_text= (TextView) convertView.findViewById(R.id.list_context_text);
            wuViewHolder.img1= (ImageView) convertView.findViewById(R.id.list_context_image1);
            wuViewHolder.img2= (ImageView) convertView.findViewById(R.id.list_context_image2);
            wuViewHolder.img3= (ImageView) convertView.findViewById(R.id.list_context_image3);
            wuViewHolder.level_img= (ImageView) convertView.findViewById(R.id.list_context_levelpic);
            wuViewHolder.level= (TextView) convertView.findViewById(R.id.list_context_level);
            wuViewHolder.time= (TextView) convertView.findViewById(R.id.list_context_time);
            wuViewHolder.writer= (TextView) convertView.findViewById(R.id.list_context_writer);
            wuViewHolder.linear_img= (LinearLayout) convertView.findViewById(R.id.list_context_linearimg);
            convertView.setTag(wuViewHolder);
        }else{
            wuViewHolder= (MyViewHolder) convertView.getTag();
        }
        wuViewHolder.context_text.setText(list_context.get(position));
        wuViewHolder.time.setText(list_time.get(position));
        wuViewHolder.writer.setText(list_writer.get(position));
        switch (list_level.get(position)){
            case 0:wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_purple);wuViewHolder.level.setText("侮辱");wuViewHolder.level.setText("侮辱");wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.purple_level));wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.purple_level));break;   //设置颜色break;
            case 1:wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_red);wuViewHolder.level.setText("诽谤");wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.red_level));wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.red_level));break;
            case 2:wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_yellow);wuViewHolder.level.setText("歧视");wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.yello_level));wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.yello_level));break;
            case 3:wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_bule);wuViewHolder.level.setText("撩骚");wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.blue_level));wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.blue_level));break;
        }
        switch (list_num.get(position)){
            case 0:wuViewHolder.linear_img.setVisibility(View.GONE);break;
            case 1:myUpload.download_asynchronous("keymanword","context/"+list_numURL.get(position)+"/"+"img1",wuViewHolder.img1);wuViewHolder.img2.setVisibility(View.INVISIBLE);wuViewHolder.img3.setVisibility(View.INVISIBLE);break;
            case 2:myUpload.download_asynchronous("keymanword","context/"+list_numURL.get(position)+"/"+"img1",wuViewHolder.img1);myUpload.download_asynchronous("keymanword","context/"+list_numURL.get(position)+"/"+"img2",wuViewHolder.img2);wuViewHolder.img3.setVisibility(View.INVISIBLE);break;
            case 3:myUpload.download_asynchronous("keymanword","context/"+list_numURL.get(position)+"/"+"img1",wuViewHolder.img1);myUpload.download_asynchronous("keymanword","context/"+list_numURL.get(position)+"/"+"img2",wuViewHolder.img2);myUpload.download_asynchronous("keymanword","context/"+list_numURL.get(position)+"/"+"img3",wuViewHolder.img3);break;
        }
        return convertView;
    }
    private class MyViewHolder{
        private TextView time;
        private TextView level;
        private ImageView level_img;
        private TextView context_text;
        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private TextView writer;
        private LinearLayout linear_img;
    }
}
