package treememory.com.treememory.wustringparsing;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import treememory.com.treememory.Utils.L;

/**
 * Created by Administrator on 2016/12/30.
 */

public class MyUrlGet {
//    private String secretId;
//    private String secretKey;
//    public MySignGet(String secretId,String secretKey){
//        this.secretId=secretId;
//        this.secretKey=secretKey;
//    }
    public static String getBackgroundUrl(String s){
        int i=s.indexOf("background");
        i+=13;
        int ii=s.indexOf("\"",i);
        s=s.substring(i,ii);
        s=s.replace("/","");
        return s.replace("\\","/");
    }
    public static String getImageHeadUrl(String s){
        int i=s.indexOf("profile_image_url");
        i+=20;
        int ii=s.indexOf("\"",i);
        s=s.substring(i,ii);
        s=s.replace("/","");
        return changeImgBig(s.replace("\\","/"));
    }
    private static String changeImgBig(String s){
        int i=0;
        i=s.indexOf("50");
        int count=0;
        if(i==-1){
            return s;
        }
        while(i!=-1){
            if(s.charAt(i+2)=='/'&&s.charAt(i-1)=='.'){
                break ;
            }
            i=s.indexOf("50",i+1);
        }
        String s_before=s.substring(0,i);
        String s_after=s.substring(i+2);
        String s_end=s_before+"1024"+s_after;
        return s_end;
    }
    public static String getIdName(String s){
        int i=s.indexOf("avatar_hd");
        int ii=s.lastIndexOf("name", i);
        ii+=7;
        i=s.indexOf("\"",ii);
        return decodeUnicode(s.substring(ii,i));
    }
    public static String getIdDescription(String s){
        int i=s.indexOf("description");
        i+=14;
        int ii=s.indexOf("\"", i);
        return "简介: "+decodeUnicode(s.substring(i,ii));
    }
    public static String decodeUnicode(String unicodeStr) {
        if (unicodeStr == null) {
            return null;
        }
        StringBuffer retBuf = new StringBuffer();
        int maxLoop = unicodeStr.length();
        for (int i = 0; i < maxLoop; i++) {
            if (unicodeStr.charAt(i) == '\\') {
                if ((i < maxLoop - 5) && ((unicodeStr.charAt(i + 1) == 'u') || (unicodeStr.charAt(i + 1) == 'U')))
                    try {
                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i + 2, i + 6), 16));
                        i += 5;
                    } catch (NumberFormatException localNumberFormatException) {
                        retBuf.append(unicodeStr.charAt(i));
                    }
                else
                    retBuf.append(unicodeStr.charAt(i));
            } else {
                retBuf.append(unicodeStr.charAt(i));
            }
        }
        return retBuf.toString();
    }
    public static String getMblogNums(String s){
        int i=s.indexOf("mblogNum");
        i+=11;
        int ii=s.indexOf("\"",i);
        return parseCommonNum(Integer.parseInt(s.substring(i,ii)));
    }
    public static String getAttNums(String s){
        int i=s.indexOf("attNum");
        i+=9;
        int ii=s.indexOf("\"",i);
        return parseCommonNum(Integer.parseInt(s.substring(i,ii)));
    }
    public static String getFansNums(String s){
        int i=s.indexOf("fansNum");
        i+=10;
        int ii=s.indexOf("\"",i);
        return parseCommonNum(Integer.parseInt(s.substring(i,ii)));
    }
    public static String getIdSex(String s){
        int b=s.indexOf("genderIcon");
        int i=s.indexOf("ta",b);
        i+=5;
        int ii=s.indexOf("\"",i);
        if(decodeUnicode(s.substring(i,ii)).equals("他"))
            return "他";
        else if(decodeUnicode(s.substring(i,ii)).equals("她"))
            return "她";
        else
            return "未知";
    }
    public static String parseCommonNum(int i){
        int isTenMillions=i/10000;
        if(isTenMillions==0)
            return i+"";
        else
            return isTenMillions+"万";
    }
    public static String getWeiboLink(String id){
        return "http://weibo.com/u/"+id;
    }
}
