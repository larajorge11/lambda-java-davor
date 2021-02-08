package com.poc.api.handler.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.api.handler.model.MessageError;
import org.apache.http.HttpStatus;

public class NoKeyDataExist implements CacheData<APIGatewayProxyResponseEvent, String> {

    private static final String DATA_NOT_FOUND = "Data has not found";

    @Override
    public APIGatewayProxyResponseEvent process(String unused) {
        ObjectMapper objectMapper = new ObjectMapper();
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();

        MessageError messageError = MessageError.builder().message(DATA_NOT_FOUND).build();

        responseEvent.setStatusCode(HttpStatus.SC_NOT_FOUND);
        try {
            responseEvent.setBody(objectMapper.writeValueAsString(messageError));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return responseEvent;
    }
}
