package com.devking.android.frame1.app.util;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.util.URLEncodedUtils;
import org.apache.http.NameValuePair;

import java.util.List;

public class Request extends HttpUtils {
	public static RequestCache cache = new RequestCache();
	
	public Request(){
		//调整线程池
		configRequestThreadPoolSize(4);
		//十秒缓存期限
		configDefaultHttpCacheExpiry(1000 * 10);
		
		HttpUtils.sHttpCache.clear();
	}
	
	public <T> HttpHandler<T> requestGet(String url, RequestCallBack<T> callback){
		return requestGet(url, null, callback);
	}
	
	public <T> HttpHandler<T> requestPost(String url,RequestCallBack<T> callback){
		return requestPost(url, null, callback);
	}
	
	public <T> HttpHandler<T> requestGet(String url, RequestParams params,RequestCallBack<T> callBack){
		return send(HttpMethod.GET, url, params, callBack);
	}
	
	public <T> HttpHandler<T> requestPost(String url, RequestParams params, RequestCallBack<T> callBack){
		return send(HttpMethod.POST, url, params, callBack);
	}
	
	public static String getUrlWithQueryString(String url, RequestParams params){
		if(params != null){
			List<NameValuePair> list = params.getQueryStringParams();
			String paramString = URLEncodedUtils.format(list, params.getCharset());
			url += "?" + paramString;
		}
		return url;
	}
	
	public static enum State{
		NORMAL,REQUESTING,SUCCESS,FAILED
	}
}
