package treememory.com.treememory.KeySearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import treememory.com.treememory.R;

/**
 * Created by Administrator on 2017/1/16.
 */

public class Search_adapter extends BaseAdapter{
    private LayoutInflater minflater;
    private ArrayList<String> mlist;
    private WuViewHolder wuViewHolder;
    public Search_adapter(Context context, ArrayList<String> list){
        minflater=LayoutInflater.from(context);
        mlist=list;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            wuViewHolder=new WuViewHolder();
            convertView=minflater.inflate(R.layout.list_search,null);
            wuViewHolder.textView= (TextView) convertView.findViewById(R.id.list_search_txt);
            convertView.setTag(wuViewHolder);
        }else {
            wuViewHolder= (WuViewHolder) convertView.getTag();
        }
        wuViewHolder.textView.setText(mlist.get(position));
        return convertView;
    }
    private class WuViewHolder{
        private TextView textView;
    }
    public void updata(){
        notifyDataSetChanged();
    }
}

