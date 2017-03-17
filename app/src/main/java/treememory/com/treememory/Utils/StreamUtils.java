package treememory.com.treememory.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/13.
 */

public class StreamUtils {
    public static String streamToString(InputStream is){
        String result="";
        byte[] buffer=new byte[1024];
        int len=0;
        try {
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            while((len=is.read(buffer))!=-1){
                bos.write(buffer,0,len);
                bos.flush();
            }
            result=bos.toString();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Bitmap streamToBitmap(InputStream is){
        return BitmapFactory.decodeStream(is);
    }
}
