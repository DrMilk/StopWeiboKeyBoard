package treememory.com.treememory.MenuItemActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import treememory.com.treememory.OnlineData.WuReward;
import treememory.com.treememory.R;

/**
 * Created by Administrator on 2017/3/13.
 */

public class ListViewWillAdapter extends BaseAdapter{
    private ArrayList<WuReward> list_reward=null;
    private MyViewHolder myViewHolder=null;
    private LayoutInflater mLayoutInflater;
    public ListViewWillAdapter(Context context,ArrayList<WuReward> list_reward){
        mLayoutInflater=LayoutInflater.from(context);
        this.list_reward=list_reward;
    }
    @Override
    public int getCount() {
        return list_reward.size();
    }

    @Override
    public Object getItem(int position) {
        return list_reward.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            myViewHolder =new MyViewHolder();
            convertView=mLayoutInflater.inflate(R.layout.listitme_will,null);
            myViewHolder.text_reward_type= (TextView) convertView.findViewById(R.id.reward_type);
            myViewHolder.text_reward_description= (TextView) convertView.findViewById(R.id.reward_describe);
            myViewHolder.text_reward_date= (TextView) convertView.findViewById(R.id.reward_time);
            myViewHolder.text_reward_release_id= (TextView) convertView.findViewById(R.id.reward_release);
            myViewHolder.text_reward_money= (TextView) convertView.findViewById(R.id.reward_price);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder= (MyViewHolder) convertView.getTag();
        }
        myViewHolder.text_reward_type.setText(list_reward.get(position).getKeyword());
        myViewHolder.text_reward_description.setText(list_reward.get(position).getRcontext());
        myViewHolder.text_reward_release_id.setText(list_reward.get(position).getRelease_id());
        myViewHolder.text_reward_date.setText(list_reward.get(position).getExpire_date());
        myViewHolder.text_reward_money.setText(list_reward.get(position).getRmoney()+"");
        return convertView;
}
    private class MyViewHolder{
        TextView text_reward_type;
        TextView text_reward_description;
        TextView text_reward_release_id;
        TextView text_reward_money;
        TextView text_reward_date;
    }
}
