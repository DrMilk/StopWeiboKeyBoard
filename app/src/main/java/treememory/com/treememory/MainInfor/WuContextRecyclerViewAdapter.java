package treememory.com.treememory.MainInfor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import treememory.com.treememory.MenuItemActivity.About;
import treememory.com.treememory.OnlineData.WuContext;
import treememory.com.treememory.R;
import treememory.com.treememory.WuUpload.MyUpload;

/**
 * Created by Administrator on 2017/1/31.
 */

public class WuContextRecyclerViewAdapter extends RecyclerView.Adapter<WuContextRecyclerViewAdapter.MyViewHolder>{
    private final int NORMAL_TYPE=0;
    private final int FOOT_TYPE=1;
    MyUpload myUpload;
    private LayoutInflater mlayoutInflater;
    private ArrayList<WuContext> list_context;
    private int should_max=0;
    private Context mcontext;
//    public WuContextRecyclerViewAdapter(Context mcontext, ArrayList<String> list_context, ArrayList<String> list_time, ArrayList<Integer> list_level, ArrayList<String> list_writer, ArrayList<Integer> list_num, ArrayList<String> list_numURL){
//        mlayoutInflater=LayoutInflater.from(mcontext);
//        this.mcontext=mcontext;
//        this.list_context=list_context;
//        this.list_time=list_time;
//        this.list_level=list_level;
//        this.list_writer=list_writer;
//        this.list_num=list_num;
//        this.list_numURL=list_numURL;
//        myUpload=new MyUpload(mcontext);
//        should_max=list_context.size();
//    }
    public WuContextRecyclerViewAdapter(Context mcontext, ArrayList<WuContext> list_context){
        mlayoutInflater=LayoutInflater.from(mcontext);
        this.mcontext=mcontext;
        this.list_context=list_context;
        myUpload=new MyUpload(mcontext);
        should_max=list_context.size();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==NORMAL_TYPE){
            View view=LayoutInflater.from(mcontext).inflate(R.layout.list_context,null);
            return new MyViewHolder(view,NORMAL_TYPE);
        }
        return null;
//        View footview= LayoutInflater.from(mcontext).inflate(R.layout.tab_remark_list_bottom,parent,false);
//        return new MyViewHolder(footview,FOOT_TYPE);
    }

    @Override
    public void onBindViewHolder(MyViewHolder wuViewHolder, int position) {
        wuViewHolder.linear_img.setVisibility(View.VISIBLE);
        wuViewHolder.img1.setVisibility(View.VISIBLE);
        wuViewHolder.img2.setVisibility(View.VISIBLE);
        wuViewHolder.img3.setVisibility(View.VISIBLE);
//        if(getItemViewType(position) == NORMAL_TYPE) {
            wuViewHolder.context_text.setText(list_context.get(position).getContext());
            wuViewHolder.time.setText(list_context.get(position).getCreatedAt().substring(0,16));
            wuViewHolder.writer.setText(list_context.get(position).getWritename());
            switch (list_context.get(position).getFakelevel()) {
                case 0:
                    wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_purple);
                    wuViewHolder.level.setText("侮辱");
                    wuViewHolder.level.setText("侮辱");
                    wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.purple_level));
                    wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.purple_level));
                    break;   //设置颜色break;
                case 1:
                    wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_red);
                    wuViewHolder.level.setText("诽谤");
                    wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.red_level));
                    wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.red_level));
                    break;
                case 2:
                    wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_yellow);
                    wuViewHolder.level.setText("歧视");
                    wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.yello_level));
                    wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.yello_level));
                    break;
                case 3:
                    wuViewHolder.level_img.setImageResource(R.mipmap.ic_alert_bule);
                    wuViewHolder.level.setText("撩骚");
                    wuViewHolder.level.setTextColor(mcontext.getResources().getColor(R.color.blue_level));
                    wuViewHolder.context_text.setTextColor(mcontext.getResources().getColor(R.color.blue_level));
                    break;
            }
            switch (list_context.get(position).getNum()) {
                case 0:
                    wuViewHolder.linear_img.setVisibility(View.GONE);
                    break;
                case 1:
                    wuViewHolder.img1.setTag(list_context.get(position).getObjectId()+"_img1");
                    myUpload.download_asynchronous("keymanword", "context/" + list_context.get(position).getObjectId() + "/" + "img1", wuViewHolder.img1);
                    wuViewHolder.img1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent it=new Intent(mcontext, About.class);
                            mcontext.startActivity(it);
                        }
                    });
                    wuViewHolder.img2.setVisibility(View.INVISIBLE);
                    wuViewHolder.img3.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    wuViewHolder.img1.setTag(list_context.get(position).getObjectId()+"_img1");
                    wuViewHolder.img2.setTag(list_context.get(position).getObjectId()+"_img2");
                    myUpload.download_asynchronous("keymanword", "context/" + list_context.get(position).getObjectId() + "/" + "img1", wuViewHolder.img1);
                    myUpload.download_asynchronous("keymanword", "context/" + list_context.get(position).getObjectId() + "/" + "img2", wuViewHolder.img2);
                    wuViewHolder.img3.setVisibility(View.INVISIBLE);
                    break;
                case 3:
                    wuViewHolder.img1.setTag(list_context.get(position).getObjectId()+"_img1");
                    wuViewHolder.img2.setTag(list_context.get(position).getObjectId()+"_img2");
                    wuViewHolder.img3.setTag(list_context.get(position).getObjectId()+"_img3");
                    myUpload.download_asynchronous("keymanword", "context/" + list_context.get(position).getObjectId() + "/" + "img1", wuViewHolder.img1);
                    myUpload.download_asynchronous("keymanword", "context/" + list_context.get(position).getObjectId() + "/" + "img2", wuViewHolder.img2);
                    myUpload.download_asynchronous("keymanword", "context/" + list_context.get(position).getObjectId() + "/" + "img3", wuViewHolder.img3);
                    break;
            }
//        }
    }
    @Override
    public int getItemCount() {
        return should_max;
    }
    @Override
    public int getItemViewType(int position) {
//        if (position == should_max-1) {
//            return FOOT_TYPE;
//        }
        return NORMAL_TYPE;
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private TextView level;
        private ImageView level_img;
        private TextView context_text;
        private ImageView img1;
        private ImageView img2;
        private ImageView img3;
        private TextView writer;
        private LinearLayout linear_img;
        private ImageView bottom_img;
        private TextView bottom_text;
        public MyViewHolder(View itemView,int viewtype) {
            super(itemView);
            if(viewtype==NORMAL_TYPE){
                context_text= (TextView) itemView.findViewById(R.id.list_context_text);
                img1= (ImageView) itemView.findViewById(R.id.list_context_image1);
                img2= (ImageView) itemView.findViewById(R.id.list_context_image2);
                img3= (ImageView) itemView.findViewById(R.id.list_context_image3);
                level_img= (ImageView) itemView.findViewById(R.id.list_context_levelpic);
                level= (TextView) itemView.findViewById(R.id.list_context_level);
                time= (TextView) itemView.findViewById(R.id.list_context_time);
                writer= (TextView) itemView.findViewById(R.id.list_context_writer);
                linear_img= (LinearLayout) itemView.findViewById(R.id.list_context_linearimg);
            }else if(viewtype==FOOT_TYPE){
                bottom_img= (ImageView) itemView.findViewById(R.id.tab_remark_bottom_img);
                bottom_text= (TextView) itemView.findViewById(R.id.tab_remark_bottom_text);
            }
        }
    }
}
