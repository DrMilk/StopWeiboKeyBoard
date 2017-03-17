package treememory.com.treememory.MenuItemActivity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import treememory.com.treememory.R;

/**
 * Created by Administrator on 2017/3/7.
 */

public class FragmentDone extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragementdone,null);
        return view;
    }
}
