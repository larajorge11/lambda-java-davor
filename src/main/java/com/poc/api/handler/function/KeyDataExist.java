package com.poc.api.handler.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.api.handler.model.InventoryResponse;
import org.apache.http.HttpStatus;

public class KeyDataExist implements CacheData<APIGatewayProxyResponseEvent, String> {

    @Override
    public APIGatewayProxyResponseEvent process(String data) {
        ObjectMapper objectMapper = new ObjectMapper();
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        try {
            InventoryResponse inventoryResponse = objectMapper.readValue(data, InventoryResponse.class);
            responseEvent.setStatusCode(HttpStatus.SC_OK);
            responseEvent.setBody(objectMapper.writeValueAsString(inventoryResponse));

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return responseEvent;
    }
}
