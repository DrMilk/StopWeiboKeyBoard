package treememory.com.treememory.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 2017/2/12.
 */

public class SharePreferenceUtil {
    private static String SETTING_URL="setting";
    public static String INITSDCARD_KEY="isInitSdcard";
    public static String WIFIUPDATA_KEY="iswifi";
    public static void putSettingDataBoolean(Context mcontext,String key,boolean isTrue){
        SharedPreferences sp=mcontext.getSharedPreferences(SETTING_URL,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,isTrue);
        editor.commit();
    }
    public static boolean getSettingDataBoolean(Context mcontext,String key){
        SharedPreferences sp=mcontext.getSharedPreferences(SETTING_URL,Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }
}
