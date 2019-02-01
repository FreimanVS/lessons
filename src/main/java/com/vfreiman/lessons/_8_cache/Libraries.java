package com.vfreiman.lessons._8_cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.Weigher;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.concurrent.TimeUnit;

public class Libraries {

    private void ehcache() {
        //1. Create a cache manager
        CacheManager cm = CacheManager.getInstance();

        //2. Create a cache called "cache1"
        cm.addCache("cache1");

        //3. Get a cache called "cache1"
        Cache cache = cm.getCache("cache1");

        //4. Put few elements in cache
        cache.put(new Element("1","Jan"));
        cache.put(new Element("2","Feb"));
        cache.put(new Element("3","Mar"));

        //5. Get element from cache
        Element ele = cache.get("1");

        //6. Print out the element
        String output = (ele == null ? null : ele.getObjectValue().toString());
        System.out.println(output);

        //7. Is key in cache?
        System.out.println(cache.isKeyInCache("1"));
        System.out.println(cache.isKeyInCache("5"));

        //8. shut down the cache manager
        cm.shutdown();
    }

    /**
     * it is thread-safe
     * you can insert values manually into the cache using put(key,value)
     * you can measure your cache performance using CacheStats ( hitRate(), missRate(), ..)
     */
    private void guava() throws InterruptedException {
        CacheLoader<String, String> loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return key.toUpperCase();
            }
        };

        LoadingCache<String, String> cache =
                CacheBuilder.newBuilder()
                        .maximumSize(3) //We can limit the size of our cache. If the cache reaches the limit, the oldest items will be evicted.
                        .build(loader);
        System.out.println("SIZE: " + cache.size());
        System.out.println("ELEM: " + cache.getUnchecked("hello")); //computes and loads the value into the cache if it doesn’t already exist.
        System.out.println("SIZE: " + cache.size());




        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("forth");
        System.out.println(cache.getIfPresent("first"));
        System.out.println(cache.getIfPresent("forth"));
        System.out.println("=============================================");




        Weigher<String, String> weighByLength = new Weigher<String, String>() {
            @Override
            public int weigh(String key, String value) {
                return value.length();
            }
        };

        cache = CacheBuilder.newBuilder()
                .maximumWeight(16) //We can also limit the cache size using a custom weight function. In the following code, we use the length as our custom weight function
                .weigher(weighByLength)
                .build(loader);

        cache.getUnchecked("first");
        cache.getUnchecked("second");
        cache.getUnchecked("third");
        cache.getUnchecked("last");
        System.out.println("SIZE: " + cache.size());
        System.out.println("getIfPresent(\"first\"): " + cache.getIfPresent("first"));
        System.out.println("getIfPresent(\"last\"): " +  cache.getIfPresent("last"));
        System.out.println("=============================================");




        cache = CacheBuilder.newBuilder()
                .expireAfterAccess(2, TimeUnit.MILLISECONDS) //Beside using size to evict old records, we can use time. In the following example, we customize our cache to remove records that have been idle for 2ms
                .build(loader);

        System.out.println("getUnchecked(\"hello\"): " + cache.getUnchecked("hello"));
        System.out.println("SIZE: " + cache.size());

        System.out.println("getUnchecked(\"hello\"): " + cache.getUnchecked("hello"));
        Thread.sleep(300);

        System.out.println("getUnchecked(\"test\"): " + cache.getUnchecked("test"));
        System.out.println("SIZE: " + cache.size());
        System.out.println("getIfPresent(\"hello\"): "  + cache.getIfPresent("hello"));
        System.out.println("=============================================");




        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(2,TimeUnit.MILLISECONDS) //We can also evict records based on their total live time. In the following example, the cache will remove the records after 2ms of being stored
                .build(loader);

        System.out.println(cache.getUnchecked("hello"));
        System.out.println("SIZE: " + cache.size());
        Thread.sleep(300);
        System.out.println(cache.getUnchecked("test"));
        System.out.println("SIZE: " + cache.size());
        System.out.println(cache.getIfPresent("hello"));
        System.out.println("=============================================");




        cache = CacheBuilder.newBuilder()
                .weakKeys()
                .softValues()
                .refreshAfterWrite(1,TimeUnit.MINUTES) //the cache is refreshed automatically every 1 minute
                .build(loader);

        cache.refresh("key"); //refresh a key manually
    }
    public static void main(String[] args) throws InterruptedException {
        Libraries l = new Libraries();
        l.guava();
        l.ehcache();
    }
}
