package com.zihuan.rsa;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.zihuan.rsautils.EncryptionUtils;
import com.zihuan.rsautils.RSAUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Map<String, String> encodeMap = new HashMap<>();
            // 从字符串中得到公钥
            RSAUtils.loadPublicKey("PUBLIC_KEY");
            // 加密
            String encryptByte = RSAUtils.encryptWithRSA(transMap2String(encodeMap));
            HashMap<String, String> params = new HashMap<>();
            params.put("params", encryptByte);  // 加密的参数串
        } catch (Exception e) {
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE},10086);
        }
//        File file = new File("/storage/emulated/0/屏保.mp4");
//        try {
//            EncryptionUtils.entrypt(file, 10086);
//            Log.e("加密", "加密完成");
//        } catch (IOException e) {
//            Log.e("异常", "出错了" + e);
//        }

    }

    /**
     * map转为字符串
     *
     * @param map
     * @return
     */
    public static String transMap2String(Map map) {
        java.util.Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (java.util.Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }
}
