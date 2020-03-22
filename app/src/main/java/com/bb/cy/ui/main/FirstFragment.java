package com.bb.cy.ui.main;

import android.view.View;
import android.widget.Button;

import com.bb.base.BaseFragment;
import com.bb.cy.R;
import com.bb.network.HttpManager;
import com.bb.network.IHttpCallBack;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import io.reactivex.Observable;
import sun.misc.BASE64Encoder;


/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: FirstFragment
 * Author: CY
 * Date: 2019/11/10 0010 17:32
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public class FirstFragment extends BaseFragment {


    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_first;
    }

    @Override
    public void initView(View view) {
        Button button = view.findViewById(R.id.click);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //云市场分配的密钥Id
                String secretId = "AKIDCKs9rzCNp3KrX9nA1I6MSvVM5QXmPxp9U8UW";
                //云市场分配的密钥Key
                String secretKey = "7Xa14d15EF9f0ESDf1zw2675J6aOToUEby65tO";
                String source = "market";

                Calendar cd = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                String datetime = sdf.format(cd.getTime());
                // 签名
                try {
                    String auth = calcAuthorization(source, secretId, secretKey, datetime);
                    // 请求方法
                    String method = "GET";
                    // 请求头
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("X-Source", source);
                    headers.put("X-Date", datetime);
                    headers.put("Authorization", auth);
                    HttpManager.getInstance().addHeaders(headers);

                    // 查询参数
                    Map<String, String> queryParams = new HashMap<String, String>();
                    queryParams.put("channelId", "");
                    queryParams.put("channelName", "");
                    queryParams.put("needAllList", "");
                    queryParams.put("needContent", "");
                    queryParams.put("page", "1");
                    queryParams.put("title", "");
//                    String params = urlencode(queryParams);
                    // url参数拼接
                    String url = "https://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/release/news";
                    if (!queryParams.isEmpty()) {
                        url += "?" + urlencode(queryParams);
                    }
                    ApiService apiService = HttpManager.getInstance().create(ApiService.class);
                    Observable observable = apiService.getNews1(url);
                    HttpManager.getInstance().requestSubscribe(observable, new IHttpCallBack<NewsEntity>() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(NewsEntity newsEntity) {

                        }

                        @Override
                        public void onFailure(String errorCode, String errorString) {

                        }

                        @Override
                        public void onError(Throwable throwable) {

                        }
                    });
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void initData() {

    }

    public static String calcAuthorization(String source, String secretId, String secretKey, String datetime)
            throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));
        String sig = new BASE64Encoder().encode(hash);

        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }

    public static String urlencode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }
}
