package com.zihuan.rsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zihuan.rsautils.RSAUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Map<String, String> encodeMap=new HashMap<>();
            // 从字符串中得到公钥
            RSAUtils.loadPublicKey("PUBLIC_KEY");
            // 加密
            String encryptByte = RSAUtils.encryptWithRSA(transMap2String(encodeMap));
            HashMap<String, String> params = new HashMap<>();
            params.put("params", encryptByte);  // 加密的参数串
        } catch (Exception e) {
        }
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
