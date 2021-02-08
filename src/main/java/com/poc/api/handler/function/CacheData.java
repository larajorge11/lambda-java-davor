package com.poc.api.handler.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public interface CacheData<APIGatewayProxyResponseEvent,U> {
    APIGatewayProxyResponseEvent process(U u);
}
