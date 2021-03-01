package com.poc.api.handler.function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.api.handler.model.InventoryRequest;
import io.vavr.control.Option;
import org.apache.logging.log4j.util.Strings;
import redis.clients.jedis.Jedis;

import java.io.IOException;

import static com.common.cache.CacheConnectionInstance.cacheInstance;

public class InventoryFunction implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static LambdaLogger logger;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();;
        logger = context.getLogger();
        logger.log("EVENT: You are calling the API Lambda");

        ObjectMapper objectMapper = new ObjectMapper();

        logger.log("EVENT: REQUEST" + input.getResource());
        logger.log("EVENT: REQUEST" + input.getBody());
        try {
            InventoryRequest request = objectMapper.readValue(input.getBody(), InventoryRequest.class);

            Jedis jedis = cacheInstance.get();
            String dataCache = jedis.get(request.getDataPointId());

            responseEvent = Option.of(dataCache)
                    .filter(x -> Strings.isNotEmpty(x))
                    .map(data -> new KeyDataExist().process(data))
                    .getOrElse(() -> new NoKeyDataExist().process(""));


        } catch (IOException ex) {
            logger.log(ex.getMessage());
        }

        return responseEvent;
    }
}
