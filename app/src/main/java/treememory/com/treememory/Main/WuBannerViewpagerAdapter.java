package treememory.com.treememory.Main;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import treememory.com.treememory.R;
import treememory.com.treememory.WuUpload.MyUpload;
import treememory.com.treememory.WuUpload.WuSdcard;

/**
 * Created by Administrator on 2017/2/2.
 */

public class WuBannerViewpagerAdapter extends PagerAdapter{
    private List<ImageView> mimglist=new ArrayList<>();
    private List<Bitmap> list_bitmap=new ArrayList<>();
    private List<Boolean> list_bitmap_change=new ArrayList<>();
    private WuSdcard wuSdcard=new WuSdcard();
    private MyUpload myUpload;
    private Bitmap bitmap;
    public WuBannerViewpagerAdapter(Context mcontext,List<ImageView> mimglist){
        this.mimglist=mimglist;
        myUpload=new MyUpload(mcontext);
        Log.i("AAA",mimglist.size()+"");
        for(int i=0;i<10;i++){
            list_bitmap.add(null);
            list_bitmap_change.add(false);
        }
    }
    @Override
    public int getCount() {
        return 600;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//官方推荐写法
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position=position%mimglist.size();
        if(!list_bitmap_change.get(position)){
            bitmap=wuSdcard.getPicture(WuSdcard.pathCacheBanner,"qcode_banner_0" + (position + 1) + ".jpg");
            if(bitmap!=null){
                list_bitmap.set(position,bitmap);
                mimglist.get(position).setImageBitmap(list_bitmap.get(position));
                list_bitmap_change.set(position,true);
            }else {
                myUpload.download_show_saveEverthing(myUpload.bucket_key,"KeyServer/firstmain/banner/" + "qcode_banner_0" + (position + 1) + ".jpg",
                        myUpload.UPLOAD_PIC, wuSdcard.pathCacheBanner,mimglist.get(position));
            }
        }
        container.addView(mimglist.get(position));
        return mimglist.get(position);
//        if(position<list_bitmap.size()) {
//            Bitmap bitmap=wuSdcard.getPicture(WuSdcard.pathwriteimg + File.separator, "KeyServer/firstmain/banner/" + "qcode_banner_0" + (position + 1) + ".jpg");
//            if(bitmap!=null){
//                list_bitmap.add(position,bitmap);
//                //mimglist.get(position).setImageBitmap(bitmap);
//                container.addView(mimglist.get(position));
//            }
//        }else{
//            Bitmap bitmap=wuSdcard.getPicture(WuSdcard.pathwriteimg + File.separator, "KeyServer/firstmain/banner/" + "qcode_banner_0" + (position + 1) + ".jpg");
//            if(bitmap!=null){
//                list_bitmap.add(bitmap);
//               // mimglist.get(position).setImageBitmap(bitmap);
//                container.addView(mimglist.get(position));
//
//            }
//        }
//        Bitmap bitmap=wuSdcard.getPicture(WuSdcard.pathwriteimg + File.separator,"qcode_banner_0" + (position + 1) + ".jpg");
//        mimglist.get(position).setImageBitmap(bitmap);
        //mimglist.get(position).setImageBitmap(WuSdcard.getMysdcard().getPicture(WuSdcard.pathwriteimg,"KeyServer/firstmain/banner/" + "qcode_banner_0"+(position+1)+".jpg"));

    }

}
