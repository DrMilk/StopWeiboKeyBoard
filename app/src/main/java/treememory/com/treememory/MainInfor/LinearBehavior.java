package treememory.com.treememory.MainInfor;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/1/23.
 */

public class LinearBehavior extends CoordinatorLayout.Behavior<LinearLayout> {
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, LinearLayout child, View dependency) {
        return dependency instanceof ListView;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, LinearLayout child, View dependency) {
        modifyAvatarDependingDependencyState(child, dependency);
        return true;
    }
    private void modifyAvatarDependingDependencyState(
            LinearLayout linear, View dependency) {
           linear.setY(dependency.getY());
        //  avatar.setBlahBlah(dependency.blah / blah);
    }
}
