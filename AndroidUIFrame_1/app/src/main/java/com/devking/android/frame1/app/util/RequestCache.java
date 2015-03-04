package com.devking.android.frame1.app.util;


import com.lidroid.xutils.cache.LruMemoryCache;
import com.lidroid.xutils.http.HttpCache;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @author Kings
 *
 */
public class RequestCache extends HttpCache {
	/**
	 * key:url value: response result
	 */
	private final LruMemoryCache<String, String> mMemoryCache;
	private final LruMemoryCache<String, Boolean> mEnable;
	private final static int DEFAULT_CACHE_SIZE = 1024 * 100;
	private final static long DEFAULT_EXPIRY_TIME = 1000 * 60;
	
	private int cacheSize = DEFAULT_CACHE_SIZE;
	
	private static long defaultExpiryTime = DEFAULT_EXPIRY_TIME;
	
	private final static ConcurrentHashMap<String, Boolean> httpMethod_enabled_map;
	
	public RequestCache(){
		this(RequestCache.DEFAULT_CACHE_SIZE,RequestCache.DEFAULT_EXPIRY_TIME);
	}
	
	public RequestCache(int strLength, long defaultExpiryTime){
		this.cacheSize = strLength;
		setDefaultExpiryTime(defaultExpiryTime);
		mMemoryCache = new LruMemoryCache<String, String>(this.cacheSize){

			@Override
			protected int sizeOf(String key, String value) {
				if(value == null)
					return 0;
				return value.length();
			}
			
		};
		mEnable = new LruMemoryCache<String, Boolean>(this.cacheSize){

			@Override
			protected int sizeOf(String key, Boolean value) {
				if(value == null)
					return 0;
				return 2;
			}
			 
		};
	}

	
	
	public int getCacheSize() {
		return cacheSize;
	}

	public void setCacheSize(int strLength) {
		mMemoryCache.setMaxSize(strLength);
		mEnable.setMaxSize(strLength);
	}
	
	public LruMemoryCache<String, String> getmMemoryCache() {
		return mMemoryCache;
	}

	public LruMemoryCache<String, Boolean> getmEnable() {
		return mEnable;
	}

	public static long getDefaultExpiryTime() {
		return defaultExpiryTime;
	}

	public static void setDefaultExpiryTime(long defaultExpiryTime) {
		RequestCache.defaultExpiryTime = defaultExpiryTime;
	}
	
	public void put(String url, String result, long expiry){
		if(url == null || result == null || expiry < 1)
			return;
		mMemoryCache.put(url, result,System.currentTimeMillis()+expiry);
	}
	
	public void remove(String url){
		mMemoryCache.remove(url);
	}
	
	public void removeEnable(String url){
		mEnable.remove(url);
	}
	
	public String get(String url){
		if(url == null){
			return null;
		}
		if(mEnable.containsKey(url)){
			return null;
		}
		return mMemoryCache.get(url);
	}
	
	public void clear(){
		mMemoryCache.evictAll();
		mEnable.evictAll();
	}
	
	public void putEnable(String url){
		mEnable.put(url, true);
	}
	
	public boolean isEnabled(HttpRequest.HttpMethod method){
		if(method == null){
			return false;
		}
		Boolean enabled = httpMethod_enabled_map.get(method.toString());
		return enabled == null ? false : enabled;
	}
	
	public void setEnable(HttpRequest.HttpMethod method, boolean enabled){
		httpMethod_enabled_map.put(method.toString(), enabled);
	}
	
	static{
		httpMethod_enabled_map = new ConcurrentHashMap<String, Boolean>(10);
		httpMethod_enabled_map.put(HttpRequest.HttpMethod.GET.toString(), true);
	}
	
	public static int getDefaultCacheSize(){
		return DEFAULT_CACHE_SIZE;
	}

	public static ConcurrentHashMap<String, Boolean> getHttpmethodEnabledMap() {
		return httpMethod_enabled_map;
	}
	
	
	
}
