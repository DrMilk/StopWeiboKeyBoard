package treememory.com.treememory.MenuItemActivity;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import treememory.com.treememory.OnlineData.WuReward;
import treememory.com.treememory.R;
import treememory.com.treememory.WuUpload.MyUpload;


/**
 * Created by Administrator on 2017/3/7.
 */

public class FragmentWill extends Fragment{
    private MyUpload myUpload;
    private Context mcontext;
    private ListView listview_will;
    private ArrayList<WuReward> list_reward_will=new ArrayList<WuReward>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragementwill,null);
        listview_will= (ListView) view.findViewById(R.id.fragmentwill_listview);
        ListViewWillAdapter listViewWillAdapter=new ListViewWillAdapter(getActivity(),list_reward_will);
        listview_will.setAdapter(listViewWillAdapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=getActivity().getApplication();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        list_reward_will=new ArrayList<>();
        Bundle bundle=getArguments();
        ArrayList<String> keyward=new ArrayList<>();
        ArrayList<String> rcontext=new ArrayList<>();
        ArrayList<String> response_id=new ArrayList<>();
        ArrayList<String> release_id=new ArrayList<>();
        ArrayList<Integer> rmoney=new ArrayList<>();
        ArrayList<String> expire_date=new ArrayList<>();
        ArrayList<Integer> photograph=new ArrayList<>();
        keyward=bundle.getStringArrayList("keyward");
        rcontext=bundle.getStringArrayList("rcontext");
        response_id=bundle.getStringArrayList("response_id");
        release_id=bundle.getStringArrayList("release_id");
        rmoney=bundle.getIntegerArrayList("rmoney");
        expire_date=bundle.getStringArrayList("expire_date");
        photograph=bundle.getIntegerArrayList("photograph");
        for(int i=0;i<keyward.size();i++){
            WuReward wuReward=new WuReward(true,keyward.get(i),rcontext.get(i),response_id.get(i),
                    release_id.get(i),rmoney.get(i),expire_date.get(i),photograph.get(i));
            list_reward_will.add(wuReward);
        }
    }
}
