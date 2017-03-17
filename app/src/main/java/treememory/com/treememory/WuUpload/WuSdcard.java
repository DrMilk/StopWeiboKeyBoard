package treememory.com.treememory.WuUpload;

/**
 * Created by Administrator on 2017/2/11.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import treememory.com.treememory.Utils.L;
import treememory.com.treememory.Utils.SharePreferenceUtil;


public  class WuSdcard {
    private String TAG="WuSdcard";
    private static String path= Environment.getExternalStorageDirectory()+ File.separator+"com.namewu.Stopkeyboard";
    private static WuSdcard mysdcard=null;
    public static final String pathwriteimg=path+File.separator+"writeimg";
    public static final String pathheadimg=path+File.separator+"headimg";
    public static final String pathsearchtxt=path+File.separator+"data";
    public static final String pathCache=path+File.separator+"cache";
    public static final String pathCacheBanner=path+File.separator+"cache"+File.separator+"banner";
    public static final String pathCacheImage=path+File.separator+"cache"+File.separator+"image";
    public static final String pathabout=path+File.separator+"data"+File.separator+"about";
    public WuSdcard(){
    }
    public boolean initWuSdcard(Context context){
      //  if (!SharePreferenceUtil.getSettingDataBoolean(context,SharePreferenceUtil.INITSDCARD_KEY)) {
            File dir;
            dir = new File(pathwriteimg);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            dir = new File(pathheadimg);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            dir = new File(pathsearchtxt);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            dir = new File(pathCacheBanner);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            dir = new File(pathCacheImage);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            SharePreferenceUtil.putSettingDataBoolean(context, SharePreferenceUtil.INITSDCARD_KEY, true);
      //  }
        return true;
    }
    public static WuSdcard getMysdcard(){
        if(mysdcard==null){
            mysdcard=new WuSdcard();
        }
        return mysdcard;
    }

    public String getPathwriteimg() {
        return pathwriteimg;
    }

    public String getPathheadimg() {
        return pathheadimg;
    }

    public String getPathsearchtxt() {
        return pathsearchtxt;
    }
    public void savePicture(String path, String name, Bitmap bitmap){
        Log.i(TAG,"执行了"+name);
        FileOutputStream fos=null;
        ObjectOutputStream oos=null;
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        file=new File(path+File.separator+name);
        try {
            fos=new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            Log.i("WuSdcard","保存一个了");
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.i("WuSdcard","出错了"+e.toString());
            e.printStackTrace();
        }finally {

        }
    }
    public Bitmap getPicture(String path, String name){
        File file=new File(path+File.separator,name);
        Log.i("WuSdcard",file.getAbsolutePath());
        if(!file.exists()){
            Log.i("WuSdcard","不存在");
            return null;
        }
        return BitmapFactory.decodeFile(file.getAbsolutePath());
        }
    public void saveMusic(String path,String name,byte[] mpsbyte){
        FileOutputStream fos=null;
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
            Log.i("WuSdcard","创建文件夹成功");
        }
        file=new File(path+File.separator+name);
        try {
            if(!file.exists()){
                file.createNewFile();
                Log.i("WuSdcard","创建文件成功");
                fos=new FileOutputStream(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.write(mpsbyte);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public long getFolderSize(File file){
        long size=0;
        File[] files=file.listFiles();
        if(files==null)return 0;
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                size=size+getFolderSize(files[i]);
            }else {
                size=size+files[i].length();
            }
            L.i(TAG,size+"size");
        }
        return size;
    }
    /**
     * 删除指定目录下文件及目录
     * @param deleteThisPath
     * @param
     * @return
     */
    public boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return true;
    }
}
