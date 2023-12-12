package org.asamk.signal.manager.config;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class FixHttpLoggingInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(FixHttpLoggingInterceptor.class);

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Buffer buffer = new Buffer();
        Request newRequest;
        if ( request.body() != null ) {
            request.body().writeTo(buffer);
            newRequest = new Request(request.url(), request.method(), request.headers(), RequestBody.create(buffer.readByteArray(), request.body().contentType()), request.getTags$okhttp());
        }
        else {
            newRequest = new Request(request.url(), request.method(), request.headers(), RequestBody.create(buffer.readByteArray()), request.getTags$okhttp());
        }
        return chain.proceed(newRequest);
    }
}
