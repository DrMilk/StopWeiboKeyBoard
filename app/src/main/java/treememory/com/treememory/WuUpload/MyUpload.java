package treememory.com.treememory.WuUpload;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.CannedAccessControlList;
import com.alibaba.sdk.android.oss.model.CreateBucketRequest;
import com.alibaba.sdk.android.oss.model.CreateBucketResult;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import treememory.com.treememory.R;
import treememory.com.treememory.Utils.L;
import treememory.com.treememory.Utils.LruCacheUtil;

/**
 * Created by Administrator on 2016/12/30.
 */

public class MyUpload{
    private String TAG="MyUpload";
    private LruCacheUtil lruCacheUtil=new LruCacheUtil();
    private WuSdcard wuSdcard=new WuSdcard();
    public final String UPLOAD_PIC="picture";
    public final String UPLOAD_MUSIC="music";
    public final String bucket_key="keymanword";
    private Handler mhandler=new Handler();
    OSS oss;
          public MyUpload(Context context){
              String endpoint = "oss-cn-qingdao.aliyuncs.com";
// 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
              OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAIt54qe4IyeKHz","nSwBEy9hYwuUDWeNxMrcHsgVz4mBPZ");
              ClientConfiguration conf = new ClientConfiguration();
              conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
              conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
              conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
              conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
               oss = new OSSClient(context, endpoint, credentialProvider);
          }
         public boolean goUpload(String bucket,String objectstr,String path){
             // 构造上传请求
             PutObjectRequest put = new PutObjectRequest(bucket,objectstr,path);
// 异步上传时可以设置进度回调
             put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                 @Override
                 public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                     Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
                 }
             });

             OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                 @Override
                 public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                     Log.d("PutObject", "UploadSuccess");
                 }

                 @Override
                 public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                     // 请求异常
                     if (clientExcepion != null) {
                         // 本地异常如网络异常等
                         clientExcepion.printStackTrace();
                     }
                     if (serviceException != null) {
                         // 服务异常
                         Log.e("ErrorCode", serviceException.getErrorCode());
                         Log.e("RequestId", serviceException.getRequestId());
                         Log.e("HostId", serviceException.getHostId());
                         Log.e("RawMessage", serviceException.getRawMessage());
                     }
                 }
             });

// task.cancel(); // 可以取消任务

// task.waitUntilFinished(); // 可以等待直到任务完成
             return true;
         }
    public boolean createbucket(String bucketname){
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketname);
        createBucketRequest.setBucketACL(CannedAccessControlList.PublicRead); // 指定Bucket的ACL权限
        createBucketRequest.setLocationConstraint("oss-cn-qingdao"); // 指定Bucket所在的数据中心
        OSSAsyncTask createTask = oss.asyncCreateBucket(createBucketRequest, new OSSCompletedCallback<CreateBucketRequest, CreateBucketResult>() {
            @Override
            public void onSuccess(CreateBucketRequest request, CreateBucketResult result) {
                Log.d("locationConstraint", request.getLocationConstraint());
            }

            @Override
            public void onFailure(CreateBucketRequest request, ClientException clientException, ServiceException serviceException) {
                // 请求异常
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
        return true;
    }
    //异步
    public byte[] download_asynchronous(String bucketName, final String objectKey, final ImageView img){
        final String cache_str=objectKey.split("/")[1]+"_"+objectKey.split("/")[2];
        Bitmap bitmap=wuSdcard.getPicture(WuSdcard.pathCacheImage,cache_str);
        L.i(TAG, "Tag"+(String) img.getTag());
        if(bitmap!=null){
            if(img.getTag().equals(cache_str)){
                img.setImageBitmap(bitmap);
            }else {
                img.setImageResource(R.mipmap.mall_comment_photos_add);
            }
            return null;
        }
//        if(lruCacheUtil.getBitmapFromMemCache(objectKey)!=null){
//            img.setImageBitmap(lruCacheUtil.getBitmapFromMemCache(objectKey));
//            L.i(TAG,"ok le");
//            return null;
//        }
      //  lruCacheUtil.getBitmapFromMemCache(objectKey);
        final ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        GetObjectRequest get = new GetObjectRequest(bucketName,objectKey);
        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();
              //  lruCacheUtil.addBitmapToMemoryCache(objectKey,BitmapFactory.decodeStream(inputStream));
                wuSdcard.savePicture(WuSdcard.pathCacheImage,cache_str,BitmapFactory.decodeStream(inputStream));
                if(img.getTag().equals(cache_str)){
                    Bitmap bitmap=wuSdcard.getPicture(WuSdcard.pathCacheImage,cache_str);
                    if(bitmap!=null){
                            img.setImageBitmap(bitmap);
                    }
                }
                byte[] buffer = new byte[2048];
                int len;
                try {
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 处理下载的数据
                        Log.i("MyUpload","下载成功了");
                        outStream.write(buffer, 0, len);
                        Log.i("MyUpload",outStream.toByteArray().length+"weishaa");
                    }
                    Log.i("MyUpload","下载成功了");
                    test(outStream.toByteArray());
                    outStream.close();
                    inputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
// task.cancel(); // 可以取消任务
        Log.i("MyUpload",outStream.toByteArray().length+"youyisile");
        return outStream.toByteArray();
// task.waitUntilFinished(); // 如果需要等待任务完成
    }
    //异步
    public byte[] download_saveEverthing(String bucketName, final String objectKey, final String kind,final String save_url){
        final ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        GetObjectRequest get = new GetObjectRequest(bucketName,objectKey);
        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                InputStream inputStream = result.getObjectContent();
                byte[] buffer = new byte[2048];
                int len;
                try {
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 处理下载的数据
                        outStream.write(buffer, 0, len);
                    }
                    Log.i("MyUpload","下载成功了"+outStream.toByteArray().length+"weishaa");
                    Log.i("MyUpload",objectKey.split("/")[objectKey.split("/").length-1]);
                    if(kind.equals("music")){Log.i("MyUpload","要保存音乐了");WuSdcard.getMysdcard().saveMusic(save_url,objectKey.split("/")[objectKey.split("/").length-1],outStream.toByteArray());
                    } else if(kind.equals("picture")){WuSdcard.getMysdcard().savePicture(save_url,objectKey.split("/")[objectKey.split("/").length-1],BitmapFactory.decodeStream(inputStream));
                    }
                    outStream.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                Log.e("ErrorCID", request.getObjectKey());
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
// task.cancel(); // 可以取消任务
        Log.i("MyUpload",outStream.toByteArray().length+"youyisile");
        return outStream.toByteArray();
// task.waitUntilFinished(); // 如果需要等待任务完成
    }
    //同步
    public byte[] download_synchronous(String bucketName,String objectKey){
        final ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        // 构造下载文件请求
        GetObjectRequest get = new GetObjectRequest(bucketName,objectKey);
        try {
            // 同步执行下载请求，返回结果
            GetObjectResult getResult = oss.getObject(get);
            Log.d("Content-Length", "" + getResult.getContentLength());
            // 获取文件输入流
            InputStream inputStream = getResult.getObjectContent();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = inputStream.read(buffer))!=-1) {
                // 处理下载的数据，比如图片展示或者写入文件等
                Log.i("MyUpload",len+"zzzz"+buffer.toString());
                outStream.write(buffer,0,len);
               // Log.i("MyUpload",outStream.toByteArray().length+"zzzz");
            }
            test(outStream.toByteArray());
            // 下载后可以查看文件元信息
            ObjectMetadata metadata = getResult.getMetadata();
            Log.d("MyUpload", metadata.getContentType());
        } catch (ClientException e) {
            // 本地异常如网络异常等
            Log.d("MyUpload", "?");
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常
            Log.d("MyUpload", "//");
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("MyUpload",outStream.toByteArray().length+"youyisile");
        return outStream.toByteArray();
    }
    private void test(byte[] bytes){
        Bitmap bitmap=Bytes2Bimap(bytes);
        File f = new File(Environment.getExternalStorageDirectory()+ File.separator+"com.namewu.Stopkeyboard"+File.separator+"img1");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.i(TAG, "已经保存");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.i(TAG, "已经保存2");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i(TAG, "已经保存1");
            e.printStackTrace();
        }
        Log.i(TAG,"执行刀了");
    }
    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            Log.i(TAG,"111111111");
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            Log.i(TAG,"222222222");
            return null;
        }
    }
    public boolean isHaveObject(String bucketName,String objectKey){
        try {
            if (oss.doesObjectExist(bucketName,objectKey)) {
                Log.d("doesObjectExist", "object exist.");
                return true;
            } else {
                Log.d("doesObjectExist", "object does not exist.");
                return false;
            }
        } catch (ClientException e) {
            // 本地异常如网络异常等
            e.printStackTrace();
        } catch (ServiceException e) {
            // 服务异常
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("RequestId", e.getRequestId());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }
        return false;
    }

    public void download_show_saveEverthing(String bucketName, final String objectKey, final String kind, final String save_url, final ImageView img) {
        final ByteArrayOutputStream outStream=new ByteArrayOutputStream();
        GetObjectRequest get = new GetObjectRequest(bucketName,objectKey);
        OSSAsyncTask task = oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
                // 请求成功
                Log.i("MyUpload","下载成功了"+outStream.toByteArray().length+"weishaa");
                Log.i("MyUpload",objectKey.split("/")[objectKey.split("/").length-1]);
                InputStream inputStream = result.getObjectContent();
                byte[] buffer = new byte[2048];
                int len;
                try {
                    if(kind.equals("picture")){
                        WuSdcard wuSdcard=new WuSdcard();
                        final Bitmap bitmap=BitmapFactory.decodeStream(inputStream);
                        wuSdcard.savePicture(save_url,objectKey.split("/")[objectKey.split("/").length-1],bitmap);
                        Log.i("MyUp","执行没啊"+inputStream.toString());
                      mhandler.post(new Runnable() {
                          @Override
                          public void run() {
                              img.setImageBitmap(bitmap);
                          }
                      });
                        Log.i("MyUp","大小");
                        outStream.close();
                        inputStream.close();
                        return;
                    }
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 处理下载的数据
                        outStream.write(buffer, 0, len);
                    }
                    if(kind.equals("music")){
                        Log.i("MyUpload","要保存音乐了");
                        WuSdcard wuSdcard=new WuSdcard();
                        wuSdcard.saveMusic(save_url,objectKey.split("/")[objectKey.split("/").length-1],outStream.toByteArray());
                        outStream.close();
                        inputStream.close();
                    }
                } catch (IOException e) {
                    Log.i("MyUp","什么鬼啊"+e.toString());
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(GetObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                Log.e("ErrorCID", request.getObjectKey());
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
// task.cancel(); // 可以取消任务
        Log.i("MyUpload",outStream.toByteArray().length+"youyisile");
// task.waitUntilFinished(); // 如果需要等待任务完成
    }
}
