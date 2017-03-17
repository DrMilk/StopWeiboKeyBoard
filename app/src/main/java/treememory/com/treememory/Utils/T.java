package treememory.com.treememory.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/2/22.
 */

public class T {
    private T() {
        throw new UnsupportedOperationException("T cannot instantiated!");
    }
    public static void showShot(Context context,CharSequence s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    public static void showLong(Context context,CharSequence s){
        Toast.makeText(context,s,Toast.LENGTH_LONG);
    }
}
