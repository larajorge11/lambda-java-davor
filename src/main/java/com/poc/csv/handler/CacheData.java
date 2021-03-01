package com.poc.csv.handler;

import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.poc.csv.handler.model.Inventory;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.function.BiConsumer;

import static com.common.cache.CacheConnectionInstance.cacheInstance;

public final class CacheData {

    public static BiConsumer<List<Inventory>, LambdaLogger> storeCache = (inventories, logger) -> {

        Gson gsonInventory = new Gson();
        Jedis jedis = cacheInstance.get();
        jedis.flushDB();

        logger.log("EVENT: Saving... " + inventories);
        inventories
                .forEach(x -> {
                    jedis.set(x.getDataPointId(), gsonInventory.toJson(x));
                });

    };
}
