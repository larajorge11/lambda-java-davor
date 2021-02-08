package com.poc.common;

import redis.clients.jedis.Jedis;
import java.util.function.Supplier;

import static java.lang.Integer.valueOf;
import static java.lang.System.getenv;

public final class CacheConnectionInstance {

    private static final String REDIS_HOST = "REDIS_HOST";
    private static final String REDIS_PORT = "REDIS_PORT";

    public static Supplier<Jedis> cacheInstance = () -> new Jedis(getenv(REDIS_HOST), valueOf(getenv(REDIS_PORT)));
}
