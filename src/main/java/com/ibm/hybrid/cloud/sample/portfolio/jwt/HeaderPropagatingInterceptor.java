/*
       Copyright 2018 IBM Corp All Rights Reserved
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.ibm.hybrid.cloud.sample.portfolio.jwt;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Simple interceptor that will clone all headers from the request it is executed in the context of, 
 * onto the request being made outbound.
 */
@Component
public class HeaderPropagatingInterceptor  implements ClientHttpRequestInterceptor {

    @Autowired
    private HttpServletRequest originalRequest;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        Enumeration<String> headers = originalRequest.getHeaderNames();	
		if (headers != null) {	
			while (headers.hasMoreElements()) {	
				String headerName = headers.nextElement(); 	
				request.getHeaders().add(headerName, originalRequest.getHeader(headerName));
			}	
		}

        ClientHttpResponse response = execution.execute(request, body);

        return response;
    }
}