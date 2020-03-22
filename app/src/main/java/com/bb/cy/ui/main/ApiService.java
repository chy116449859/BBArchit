package com.bb.cy.ui.main;

import com.bb.network.BaseResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Copyright (C), 2015-2019, 邦帮科技有限公司
 * FileName: ApiService
 * Author: CY
 * Date: 2019/11/10 0010 18:13
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */

public interface ApiService {
    @GET("https://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/release/news")
    Observable<BaseResponse<NewsEntity>> getNews(@QueryMap Map<String, String> queryParams);

    @POST
    Observable<BaseResponse<NewsEntity>> getNews1(@Url String url);
}
