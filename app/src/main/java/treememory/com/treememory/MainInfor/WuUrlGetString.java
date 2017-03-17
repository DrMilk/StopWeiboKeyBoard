package treememory.com.treememory.MainInfor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import treememory.com.treememory.R;
import treememory.com.treememory.Utils.L;
import treememory.com.treememory.Utils.StreamUtils;
import treememory.com.treememory.WuUpload.WuSdcard;
import treememory.com.treememory.wustringparsing.MyUrlGet;

import static android.R.attr.path;

/**
 * Created by Administrator on 2017/2/13.
 */

public class WuUrlGetString {
    private final int HANDER_SET_SEX=9;
    private final int HANDER_SET_ATTNUMS=8;
    private final int HANDER_SET_FANS=7;
    private final int HANDER_SET_MBLOG=6;
    private final int HANDER_SET_DESCRIPTION=5;
    private final int HANDER_SET_NAME=4;
    private final int HANDER_SET_IMAGEBACKGOUND=3;
    private final int HANDER_SET_IMAGEHEAD=2;
    private final int HANDER_SET_TEXTVIEW=1;
    private String TAG="WuUrlGetString";
    private String url_address;
    private URL url=null;
    private TextView textName;
    private TextView textDescription;
    private TextView textMblog;
    private TextView textfun;
    private ImageView imagehead;
    private ImageView imagebg;
    private ImageView imagesex;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int i=msg.what;
            Log.i(TAG,"执行到这步了吗！i"+i);
            switch (i){
                case HANDER_SET_TEXTVIEW:break;
                case HANDER_SET_IMAGEHEAD:Log.i(TAG,"执行到这步了吗！"+"5");imagehead.setImageBitmap(toRoundBitmap((Bitmap) msg.obj));break;
                case HANDER_SET_IMAGEBACKGOUND:Log.i(TAG,"执行到这步了吗！"+"6");imagebg.setImageBitmap((Bitmap) msg.obj);break;
                case HANDER_SET_NAME:textName.setText((String)msg.obj);break;
                case HANDER_SET_DESCRIPTION:textDescription.setText((String)msg.obj);break;
                case HANDER_SET_ATTNUMS:textMblog.setText((String)msg.obj);break;
                case HANDER_SET_FANS:textfun.setText((String)msg.obj);break;
                case HANDER_SET_SEX:String s=(String)msg.obj;Log.i(TAG,s+"性别");if(s.equals("他")){imagesex.setImageResource(R.mipmap.userinfo_icon_male);}
                else if(s.equals("她")) {imagesex.setImageResource(R.mipmap.userinfo_icon_female);}break;
            }
        //    String result= (String) msg.obj;
          //  textView.setText(result);
        }
    };
    public WuUrlGetString(String url_address){
        this.url_address=url_address;
        try {
            // 1 创建一个URL对象
            url=new URL(url_address);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public ImageView getImagesex() {
        return imagesex;
    }

    public void setImagesex(ImageView imagesex) {
        this.imagesex = imagesex;
    }

    public TextView getTextMblog() {
        return textMblog;
    }

    public void setTextMblog(TextView textMblog) {
        this.textMblog = textMblog;
    }

    public TextView getTextfun() {
        return textfun;
    }

    public void setTextfun(TextView textfun) {
        this.textfun = textfun;
    }

    public TextView getTextName() {
        return textName;
    }

    public void setTextName(TextView textName) {
        this.textName = textName;
    }

    public TextView getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(TextView textDescription) {
        this.textDescription = textDescription;
    }

    public ImageView getImagehead() {
        return imagehead;
    }

    public void setImagehead(ImageView imagehead) {
        this.imagehead = imagehead;
    }

    public ImageView getImagebg() {
        return imagebg;
    }

    public void setImagebg(ImageView imagebg) {
        this.imagebg = imagebg;
    }

    public void requestURLString(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result="";
                try {
                    // 2 从 URL 获取urlConnection 对象
                    HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                    // 3 设置请求方式 和超时时间
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(1000*10);
                    // 4 得到响应码
                    int code=urlConnection.getResponseCode();
                    if(code==200){
                        InputStream inputStream=urlConnection.getInputStream();
                        result= StreamUtils.streamToString(inputStream);
                        Log.i(TAG, "bg-----" + MyUrlGet.getBackgroundUrl(result)+"head------"+MyUrlGet.getImageHeadUrl(result));
                        Log.i(TAG,MyUrlGet.decodeUnicode(result));
                        setUrlImage(MyUrlGet.getBackgroundUrl(result),imagebg,HANDER_SET_IMAGEBACKGOUND);
                        setUrlImage(MyUrlGet.getImageHeadUrl(result),imagehead,HANDER_SET_IMAGEHEAD);
                        MyUrlGet.getIdDescription(result);
                        setInforText(HANDER_SET_NAME,MyUrlGet.getIdName(result));
                        setInforText(HANDER_SET_DESCRIPTION,MyUrlGet.getIdDescription(result));
                        setInforText(HANDER_SET_ATTNUMS,MyUrlGet.getAttNums(result));
                        setInforText(HANDER_SET_FANS,MyUrlGet.getFansNums(result));
                        setInforText(HANDER_SET_SEX,MyUrlGet.getIdSex(result));
                        final String finalResult = result;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textName.setText(MyUrlGet.getIdName(finalResult));
                            }
                        });
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textDescription.setText(MyUrlGet.getIdDescription(finalResult));
                            }
                        });
                        // 5 显示到textview
//                        Message msg=Message.obtain();
//                        msg.obj=result;
//                        Log.i(TAG,result);
                        File file=new File(WuSdcard.pathsearchtxt+File.separator+"wangzhang.txt");
                        if(!file.exists()){
                            file.createNewFile();
                        }
                        FileOutputStream fos=new FileOutputStream(file);
                        ObjectOutputStream oos=new ObjectOutputStream(fos);
                        oos.writeObject(MyUrlGet.decodeUnicode(result));
                        oos.flush();
                        oos.close();
                        fos.close();
//                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void getHtml(final String strUrl, final String strPostRequest,
                        final int maxLength){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 读取结果网页
                    StringBuffer buffer = new StringBuffer();
                    System.setProperty("sun.net.client.defaultConnectTimeout", "5000");
                    System.setProperty("sun.net.client.defaultReadTimeout", "5000");
                    try {
                        URL newUrl = new URL(strUrl);
                        HttpURLConnection hConnect = (HttpURLConnection) newUrl
                                .openConnection();
                        // POST方式的额外数据
                        if (strPostRequest.length() > 0) {
                            hConnect.setDoOutput(true);
                            OutputStreamWriter out = new OutputStreamWriter(hConnect
                                    .getOutputStream());
                            out.write(strPostRequest);
                            out.flush();
                            out.close();
                        }
                        // 读取内容
                        BufferedReader rd = new BufferedReader(new InputStreamReader(
                                hConnect.getInputStream()));
                        int ch;
                        for (int length = 0; (ch = rd.read()) > -1
                                && (maxLength <= 0 || length < maxLength); length++)
                            buffer.append((char) ch);
                        String s = buffer.toString();
                        s.replaceAll("//&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
                        System.out.println(s);
                        rd.close();
                        hConnect.disconnect();
                        Log.i(TAG,"真的视我为吗"+buffer.toString().trim()+"");
                    } catch (Exception e) {
                        // return "错误:读取网页失败！";
                        //
                    }
                }catch (Exception e){
                }
            }
        }).start();
    }
    public void setInforText(int count,String s){
        Message msg=Message.obtain();
        msg.what=count;
        msg.obj=s;
        handler.sendMessage(msg);
    }
    public void setUrlImage(final String strurl, ImageView img, final int count){
 //       imageView=img;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i(TAG, "执行到这步了吗！" + "我不信了");
                    URL url = new URL(strurl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(1000 * 20);
                    int code = httpURLConnection.getResponseCode();
                    Log.i(TAG, "code" + code);
                    if (code == 200) {
                        InputStream is = httpURLConnection.getInputStream();
                        Message message = Message.obtain();
                        Log.i(TAG, "执行到这步了吗！" + "1");
                        message.obj = StreamUtils.streamToBitmap(is);
                        Log.i(TAG, "执行到这步了吗！" + "2");
                        message.what =count;
                        Log.i(TAG, "执行到这步了吗！" + "3");
                        handler.sendMessage(message);
                        Log.i(TAG, "执行到这步了吗！" + "4");
                    }
                } catch (Exception e) {
                    Log.i(TAG, "出错了" + e.toString());
                }
            }
        }).start();
    }
    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2 -5;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2 -5;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst_left+15, dst_top+15, dst_right-20, dst_bottom-20);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
}
