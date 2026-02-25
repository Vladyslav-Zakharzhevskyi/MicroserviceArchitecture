package org.homecorporation.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor(OAuth2AuthorizedClientManager manager) {
        return requestTemplate -> {
            OAuth2AuthorizeRequest request =
                    OAuth2AuthorizeRequest.withClientRegistrationId("identity")
                            .principal("order-service")
                            .build();

            OAuth2AuthorizedClient client = manager.authorize(request);

            String accessToken = client.getAccessToken().getTokenValue();
            requestTemplate.header("Authorization", "Bearer " + accessToken);
        };
    }

}
