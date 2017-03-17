package treememory.com.treememory.Utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Administrator on 2017/3/2.
 */

public class LruCacheUtil {
    private String TAG="LruCacheUtil";
    int MAXMEMONRY ;
    private LruCache<String,Bitmap> mMemoryCache;
    public LruCacheUtil(){
        MAXMEMONRY = (int) (Runtime.getRuntime() .maxMemory() / 1024);
        if(mMemoryCache==null){
            mMemoryCache=new LruCache<String,Bitmap>(MAXMEMONRY/8){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return (value.getRowBytes()*value.getHeight())/1024;
                }
            };
        }
    }
    public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                L.i("CacheUtils",
                        "mMemoryCache.size() " + mMemoryCache.size());
                mMemoryCache.evictAll();
                L.i("CacheUtils", "mMemoryCache.size()" + mMemoryCache.size());
            }
            mMemoryCache = null;
        }
    }

    public synchronized void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (mMemoryCache.get(key) == null) {
            if (key != null && bitmap != null)
                mMemoryCache.put(key, bitmap);
        } else
            L.i(TAG, "the res is aready exits");
    }

    public synchronized Bitmap getBitmapFromMemCache(String key) {
        Bitmap bm = mMemoryCache.get(key);
        if (key != null) {
            return bm;
        }
        return null;
    }

    /**
     * 移除缓存
     *
     * @param key
     */
    public synchronized void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }

}
