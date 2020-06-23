package com.zhq.exclusivememory.utils;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.orhanobut.logger.Logger;
import com.zhq.exclusivememory.api.API;
import com.zhq.exclusivememory.api.ApiService;
import com.zhq.exclusivememory.base.APP;
import com.zhq.exclusivememory.base.NoBodyEntity;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/19.
 */

public class RetrofitNetManager {

    private static volatile OkHttpClient mOkHttpClient;
    private static long CONNECT_TIMEOUT = 20L;
    private static long READ_TIMEOUT = 10L;
    private static long WRITE_TIMEOUT = 10L;

    public static ApiService getApiService() {
        Retrofit retrofit = getRetrofit();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    public static Retrofit getRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(getOkHttpClient())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(NobodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;


    }

    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitNetManager.class) {
                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(APP.getAppContext()));
                Cache cache = new Cache(new File(APP.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(new Interceptor() {
                                @Override
                                public Response intercept(Chain chain) throws IOException {
                                    Request.Builder builder1 = chain.request().newBuilder();
                                    builder1.addHeader("Accept", API.API_VERSION);
                                    Response proceed = chain.proceed(builder1.build());
                                    return proceed;
                                }
                            })
                            .addInterceptor(mLoggingInterceptor)
                            .cookieJar(cookieJar)
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }


    private static final HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);


    /**
     * 代替gson converter转换无响应体的response
     */
    public static class NobodyConverterFactory extends Converter.Factory {

        public static final NobodyConverterFactory create() {
            return new NobodyConverterFactory();
        }

        private NobodyConverterFactory() {
        }

        //将响应对象responseBody转成目标类型对象(也就是Call里给定的类型)
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                                Retrofit retrofit) {
            //判断当前的类型是否是我们需要处理的类型
            if (NoBodyEntity.class.equals(type)) {
                //是则创建一个Converter返回转换数据
                return new Converter<ResponseBody, NoBodyEntity>() {
                    @Override
                    public NoBodyEntity convert(ResponseBody value) throws IOException {
                        //这里直接返回null是因为我们不需要使用到响应体,本来也没有响应体.
                        //返回的对象会存到response.body()里.
                        return null;
                    }
                };
            }
            return null;
        }

        //其它两个方法我们不需要使用到.所以不需要重写.
        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                              Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            return null;
        }

        @Override
        public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                    Retrofit retrofit) {
            return null;
        }
    }


}
