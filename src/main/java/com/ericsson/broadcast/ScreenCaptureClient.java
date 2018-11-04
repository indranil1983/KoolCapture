/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.ericsson.broadcast;

import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.ericsson.multimedia.capture.ScreenCapture;

/**
 * Example how to use multipart/form encoded POST request.
 */
public class ScreenCaptureClient {
	
	private static HttpPost httppost = new HttpPost("http://localhost:3000/upload");

    public static void postScreen(ScreenCapture sc, String id) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {

        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	ImageIO.write( sc.getSource(), "jpg", baos );
        	baos.flush();
        	byte[] imageInByte = baos.toByteArray();
        	baos.close();
        	HttpEntity reqEntity = MultipartEntityBuilder.create()
        			.addBinaryBody("screenshot",imageInByte)
        			.build();
        	httppost.setEntity(reqEntity);
        	CloseableHttpResponse response = httpclient.execute(httppost);
        	try {
        		HttpEntity resEntity = response.getEntity();
        		EntityUtils.consume(resEntity);
        	} finally {
        		response.close();
        	}
        } finally {
            httpclient.close();
        }
    }

}