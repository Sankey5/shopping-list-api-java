package com.shoppinglist.config;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestResponseHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println("RestResponseHandler (hasError): " + response.getStatusCode());
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("RestResponseHandler (handleError): " + response.getStatusCode());
    }
}
