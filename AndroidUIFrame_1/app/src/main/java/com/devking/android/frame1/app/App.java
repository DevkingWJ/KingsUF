package com.devking.android.frame1.app;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import com.devking.android.frame1.app.util.L;
import com.devking.android.frame1.app.util.Request;
import com.devking.android.frame1.app.util.ToolUtils;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.bitmap.core.BitmapSize;

import java.io.File;

/**
 * Created by Kings on 2015/3/4.
 */
public class App extends Application{

    private static App mInstance;
    private DbUtils mDataBase;
    private BitmapUtils mBitmapUtils;
    private Request mRequest;
    public  static String DIRECTORY_PATH = ToolUtils.getExternalStoragePath()
            + File.separator;
    private boolean isWifiEnv = false;//是否为wifi连接

    public static boolean isDestory = true;
    public static boolean isUserReplace = false;
    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        DIRECTORY_PATH += getString(R.string.app_name);
        mDataBase = DbUtils.create(this);
        mBitmapUtils = new BitmapUtils(this, null, 1024*1024*1);
        mBitmapUtils.configMemoryCacheEnabled(true);
        mBitmapUtils.configDefaultBitmapMaxSize(new BitmapSize(480, 800));
        mRequest = new Request();
        File appDir = new File(DIRECTORY_PATH);
        if(!appDir.exists()){
            boolean r = appDir.mkdirs();
            if(!r){
                L.e("create files failed!!");
            }
        }
        setWifiEnv(ToolUtils.wifiIsConnected());
    }
    public boolean isWifiEnv() {
        return isWifiEnv;
    }
    public void setWifiEnv(boolean isWifiEnv) {
        this.isWifiEnv = isWifiEnv;
    }
    public static App getInstance() {
        return mInstance;
    }
    public DbUtils getDataBase() {
        return mDataBase;
    }
    public BitmapUtils getBitmapUtils() {
        return mBitmapUtils;
    }
    public Request getRequest() {
        return mRequest;
    }

    /**
     * 获取当前apk版本号
     *
     * @return int
     */
    public static int getVersionCode(){
        String packageName = App.getInstance().getPackageName();
        try {
            return App.getInstance().getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据layout资源文件获取视图
     *
     * @param resource
     * @return
     */
    public static View getView(int resource){
        LayoutInflater inflater = LayoutInflater.from(App.getInstance());
        return inflater.inflate(resource, null);
    }

}
