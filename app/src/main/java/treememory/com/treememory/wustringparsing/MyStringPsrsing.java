package treememory.com.treememory.wustringparsing;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/12/29.
 */

public class MyStringPsrsing {
    public MyStringPsrsing(){

    }
    public  String getWeiboId(String s){
        s=s.substring(18);
        return s;
    }
    public  Boolean istrueUser(String s){
        if(s.length()>9&&s.length()<11){
            return true;
        }else {
            return false;
        }
    }
    /**
     * 格式化文件单位
     * @param size
     * @return
     */
    public static String getFormatSize(long size) {
        double kiloByte = size/1024;
        if(kiloByte < 1) {
            return size + "Byte(s)";
        }

        double megaByte = kiloByte/1024;
        if(megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte/1024;
        if(gigaByte < 1) {
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte/1024;
        if(teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


}
