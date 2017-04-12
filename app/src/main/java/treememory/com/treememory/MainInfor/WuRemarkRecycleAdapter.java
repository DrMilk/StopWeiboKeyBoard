package treememory.com.treememory.MainInfor;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import treememory.com.treememory.OnlineData.WuRemark;
import treememory.com.treememory.R;

/**
 * Created by Administrator on 2017/1/25.
 */

public class WuRemarkRecycleAdapter extends RecyclerView.Adapter<WuRemarkRecycleAdapter.MyViewHolder>{
    private String TAG="WuRemarkRecycleAdapter";
    private Context context;
    private List<String> list_remark=new ArrayList<>();
    private List<Integer> list_height;
    private List<Integer> list_color;
//    private OnRecyclerItemClickListener mOnRecyclerItemClickListener=null;
//    private OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener=null;
    private ArrayList<WuRemark> list_remark_all=new ArrayList<>();
    private final int NORMAL_TYPE = 0;
    private final int FOOT_TYPE = 1;
    private final int max_count = 12;//最大显示数
    private int max_count_now=0;//显示数
    private int should_max=0;
    private String footViewText = "来呀 快活呀～";//FootView的内容
    private BmobQuery<WuRemark> query;
    private OnRecyclerGreatClickListener mOnRecyclerGreatClickListener=null;
    private OnRecyclerBadClickListener mOnRecyclerBadClickListener=null;
    private Animation anim_great;
    public WuRemarkRecycleAdapter(Context context, List<String> list_remark){
        this.context=context;
        this.list_remark=list_remark;
        list_height=new ArrayList<>();
        list_color=new ArrayList<>();
        for (int i=0;i<list_remark.size();i++){
            int height=new Random().nextInt(100)+200;
            list_height.add(height);
            list_color.add(new Random().nextInt(4));
        }
        Log.i(TAG,"should_max2");
        if(max_count<list_remark.size()){
            should_max+=max_count;
            Log.i(TAG,"should_max1");
            for (int i=0;i<max_count;i++){
                findRemarkContext(list_remark.get(i),0);
            }
        }else {
            should_max=list_remark.size();
            Log.i(TAG,"should_max"+should_max);
            for (int i=0;i<list_remark.size();i++){
                findRemarkContext(list_remark.get(i),0);
            }
        }
        anim_great= AnimationUtils.loadAnimation(context,R.anim.great_big);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==NORMAL_TYPE){
            View view= LayoutInflater.from(context).inflate(R.layout.recycle_remark,parent,false);
            return new MyViewHolder(view,NORMAL_TYPE);
        }
        View footview= LayoutInflater.from(context).inflate(R.layout.tab_remark_list_bottom,parent,false);
        return new MyViewHolder(footview,FOOT_TYPE);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if(getItemViewType(position) == NORMAL_TYPE){
            holder.remark_text.setText(list_remark_all.get(position).getContext());
            if(list_remark_all.get(position).getGreatpeoplo()==0) holder.text_great.setText("");else holder.text_great.setText(list_remark_all.get(position).getGreatpeoplo()+"");
            if(list_remark_all.get(position).getBadpeople()==0) holder.text_bad.setText("");else holder.text_great.setText(list_remark_all.get(position).getGreatpeoplo()+"");
            holder.text_bad.setText(list_remark_all.get(position).getBadpeople()+"");
            holder.text_write.setText(list_remark_all.get(position).getWritename());
            ViewGroup.LayoutParams parmas=holder.remark_text.getLayoutParams();
            parmas.height=list_height.get(position);
            holder.remark_text.setLayoutParams(parmas);
            switch (list_color.get(position)){
                case 0:holder.linear_top.setBackgroundResource(R.drawable.tab_remark_bg_purple_top);holder.linear_bottom.setBackgroundResource(R.drawable.tab_remark_bg_purple_bottom);break;
                case 1:holder.linear_top.setBackgroundResource(R.drawable.tab_remark_bg_blue_top);holder.linear_bottom.setBackgroundResource(R.drawable.tab_remark_bg_blue_bottom);break;
                case 2:holder.linear_top.setBackgroundResource(R.drawable.tab_remark_bg_orange_top);holder.linear_bottom.setBackgroundResource(R.drawable.tab_remark_bg_orange_bottom);break;
                case 3:holder.linear_top.setBackgroundResource(R.drawable.tab_remark_bg_green_top);holder.linear_bottom.setBackgroundResource(R.drawable.tab_remark_bg_green_bottom);break;
            }

        if(mOnRecyclerGreatClickListener!=null){
            holder.img_great.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecyclerGreatClickListener.onGreatClick(holder,holder.getLayoutPosition());
                }
            });
        }
        if(mOnRecyclerBadClickListener!=null){
            holder.img_bad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnRecyclerBadClickListener.onBadClick(holder,holder.getLayoutPosition());
                }
            });
        }
//            if(mOnRecyclerItemClickListener!=null){
//                holder.linear_top.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mOnRecyclerItemClickListener.onItemclick(holder,holder.getLayoutPosition());
//                    }
//                });
//            }
//            if(mOnRecyclerItemLongClickListener!=null){
//                holder.linear_top.setOnLongClickListener(new View.OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View v) {
//                        mOnRecyclerItemLongClickListener.onItemLongclick(holder,holder.getLayoutPosition());
//                        return false;
//                    }
//                });
//            }
        }else {
            if(position==list_remark.size()-1){
                holder.bottom_text.setText("快活不动了～");
                holder.bottom_img.setVisibility(View.INVISIBLE);
            }else {
                holder.bottom_text.setText(footViewText);
                AnimationDrawable animationDrawable= (AnimationDrawable) holder.bottom_img.getDrawable();
                animationDrawable.start();
            }
            if((max_count+max_count_now)<list_remark.size()){
                should_max+=max_count;
                for (int i=max_count_now;i<(max_count+max_count_now);i++){
                    findRemarkContext(list_remark.get(i),0);
                }
            }else {
                should_max=list_remark.size();
                for (int i=max_count_now;i<list_remark.size();i++){
                    findRemarkContext(list_remark.get(i),0);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return list_remark_all.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(list_remark.size()>max_count){
            if (position == should_max-1) {
                return FOOT_TYPE;
            }
        }
        return NORMAL_TYPE;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView remark_text;
        private ImageView img_great;
        private ImageView img_bad;
        private TextView text_great;
        private TextView text_bad;
        private TextView text_write;
        private LinearLayout linear_top;
        private LinearLayout linear_bottom;
        private ImageView bottom_img;
        private TextView bottom_text;
        public MyViewHolder(View itemView,int viewtype) {
            super(itemView);
            if(viewtype==NORMAL_TYPE){
                remark_text= (TextView) itemView.findViewById(R.id.recycle_remark_text);
                img_great= (ImageView) itemView.findViewById(R.id.recycle_remark_great_pic);
                img_bad= (ImageView) itemView.findViewById(R.id.recycle_remark_bad_pic);
                text_great= (TextView) itemView.findViewById(R.id.recycle_remark_great_num);
                text_bad= (TextView) itemView.findViewById(R.id.recycle_remark_bad_num);
                text_write= (TextView) itemView.findViewById(R.id.recycle_remark_writter);
                linear_top= (LinearLayout) itemView.findViewById(R.id.recycle_remark_linear_top);
                linear_bottom= (LinearLayout) itemView.findViewById(R.id.recycle_remark_linear_bottom);
            }else if(viewtype==FOOT_TYPE){
                bottom_img= (ImageView) itemView.findViewById(R.id.tab_remark_bottom_img);
                bottom_text= (TextView) itemView.findViewById(R.id.tab_remark_bottom_text);
            }
        }
    }
//    interface OnRecyclerItemClickListener{
//        public void onItemclick(MyViewHolder view,int postion);
//    }
//    interface OnRecyclerItemLongClickListener{
//        public void onItemLongclick(MyViewHolder view,int postion);
//    }
    interface OnRecyclerGreatClickListener{
        public void onGreatClick(MyViewHolder view,int postion);
    }
    interface OnRecyclerBadClickListener{
        public void onBadClick(MyViewHolder view,int postion);
    }



    public void setOnRecyclerGreatClickListener(OnRecyclerGreatClickListener mOnRecyclerGreatClickListener){
        this.mOnRecyclerGreatClickListener=mOnRecyclerGreatClickListener;
    }
    public void setOnRecyclerBadClickListener(OnRecyclerBadClickListener mOnRecyclerBadClickListener){
        this.mOnRecyclerBadClickListener=mOnRecyclerBadClickListener;
    }
//    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener mOnRecyclerItemClickListener){
//        this.mOnRecyclerItemClickListener=mOnRecyclerItemClickListener;
//    }
//    public void setOnRecyclerItemLongClickListener(OnRecyclerItemLongClickListener mOnRecyclerItemLongClickListener){
//        this.mOnRecyclerItemLongClickListener=mOnRecyclerItemLongClickListener;
//    }
    public void addGreat(MyViewHolder v,int postion){
        v.img_great.setImageResource(R.mipmap.rc_praise_hover);
        v.img_great.startAnimation(anim_great);
        int newi;
        if (v.text_great.getText().toString().length()==0){
            newi=0;
        }else {
            newi=Integer.parseInt(v.text_great.getText().toString());
        }
        newi++;
        list_remark_all.get(postion).setGreatpeoplo(newi);
        v.text_great.setText(newi+"");
        updatagreat(list_remark.get(postion),newi);
    }
    public void addBad(MyViewHolder v,int postion){
        v.img_bad.setImageResource(R.mipmap.rc_no_hover);
        v.img_bad.startAnimation(anim_great);
        int newi=Integer.parseInt(v.text_bad.getText().toString());
        newi++;
        list_remark_all.get(postion).setBadpeople(newi);
        v.text_bad.setText(newi+"");
        updatabad(list_remark.get(postion),newi);
    }
    //创建一个方法来设置footView中的文字
    public void setFootViewText(String footViewText) {
        this.footViewText = footViewText;
    }
//    public void addItem(int position,String value){
//
//        if(position>list_remark.size()){
//            position=list_remark.size();
//        }
//        if(position<0){
//            position=0;
//        }
//        list_remark.add(position,value);
//        list_height.add(position,new Random().nextInt(200)+100);
//        notifyItemInserted(position);
//    }
//    public String deleteItem(int position){
//        if(position>list_remark.size()-1){
//            return null;
//        }
//        list_height.remove(position);
//        String value=list_remark.remove(position);
//        notifyItemRemoved(position);
//          return null;
//    }
private void findRemarkContext(String str_objectId,int i){
    query = new BmobQuery<WuRemark>();
    query.getObject(str_objectId, new QueryListener<WuRemark>() {

        @Override
        public void done(WuRemark object, BmobException e) {
            if(e==null){
                list_remark_all.add(object);
                max_count_now++;
                Log.i(TAG,"max_count_now"+max_count_now);
                Log.i(TAG,list_remark_all.size()+"   "+should_max);
                if(list_remark_all.size()==should_max){
                    notifyDataSetChanged();
                    Log.i(TAG,"meishuaxin");
                }
            }else{
                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
            }
        }

    });
}
    public void updatagreat(String id,int num){
        WuRemark pp = new WuRemark();
        pp.setGreatpeoplo(num);
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
    public void updatabad(String id,int num){
        WuRemark pp = new WuRemark();
        pp.setBadpeople(num);
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
}
