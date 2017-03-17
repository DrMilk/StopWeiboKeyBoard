package treememory.com.treememory.Utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/2/22.
 */

public class L {
    private static boolean isOpen=true;
    private static String myName="Wu";
    static String[] TGAS={
            "LoginActivity",
            "MainActivity",
            "Usermineinfor",
    };
    private L() {
        throw new UnsupportedOperationException("L cannot instantiated!");
    }
    public static void logWhat(String tga,String msg){
        for(String one:TGAS){
            if(one.equals(tga)){
                System.out.println(myName+"----【"+tga+"】----"+msg);
                break;
            }
        }

    }
    public static void v(String tag,String msg){
        if(isOpen)Log.v(tag,msg);
    }
    public static void i(String tag,String msg){
        if(isOpen)Log.i(tag,msg);
    }
    public static void d(String tag,String msg){
        if(isOpen)Log.d(tag,msg);
    }
    public static void w(String tag,String msg){
        if(isOpen)Log.w(tag,msg);
    }
    public static void e(String tag,String msg){
        if(isOpen)Log.e(tag,msg);
    }

}
