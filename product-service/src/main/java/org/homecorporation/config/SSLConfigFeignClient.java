package org.homecorporation.config;

import feign.Client;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

@Configuration
public class SSLConfigFeignClient {

    @Bean
    public Client feignClient(SslBundles sslBundles) {
        SSLContext sslContext = sslBundles
                .getBundle("products-client")
                .createSslContext();

        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        return new Client.Default(sslSocketFactory, new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                // host eq CN on communicator side subject e.g. "CN=<hostname.part>"
                return true;
            }
        });
    }



}
