package treememory.com.treememory.WuUpload;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import treememory.com.treememory.OnlineData.Other;

/**
 * Created by Administrator on 2017/2/19.
 */

public class MyInfoDao {
    private MySqliteOpenHelper msoh;
    public void MyInfoDao(Context context){
        msoh=new MySqliteOpenHelper(context);
    }
    public void goAdd(Context context, Other other){
        SQLiteDatabase sqld=msoh.getReadableDatabase();
        sqld.execSQL("insert into info(name,phone) values(?,?);",new Object[]{other.getId(),"123456789"});
        sqld.close();
    }
    public void goDelete(Other other){
        SQLiteDatabase sqld=msoh.getReadableDatabase();
        sqld.execSQL("delete from info where name=?;",new Object[]{other.getId()});
        sqld.close();
    }
    public void goChange(Other other){
        SQLiteDatabase sqLiteDatabase=msoh.getReadableDatabase();
        sqLiteDatabase.execSQL("updata info set phone=? where name=?;",new Object[]{"ef","ef"});
        sqLiteDatabase.close();
    }
    public void goShow(){
        SQLiteDatabase sqLiteDatabase=msoh.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select name,phone from info where name=?;",new String[]{"ef"});
        //获取cursor内容
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                cursor.getInt(0);
            }
        }
    }
}
