package org.homecorporation.config;

import org.homecorporation.exception.M2MAuthenticationFailed;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OAuth2ApplicationConfig {

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository repository, OAuth2AuthorizedClientService service) {

        OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(repository, service);

        manager.setAuthorizedClientProvider(provider);

        setContextAttributesMapper(manager);
        setFailureHandler(manager);
        setSuccessHandler(manager);

        return manager;
    }

    private static void setSuccessHandler(AuthorizedClientServiceOAuth2AuthorizedClientManager manager) {
        manager.setAuthorizationSuccessHandler((authorizedClient, principal, attributes) -> {
            System.out.println("Client successfully authorized: Client name: " + authorizedClient.getPrincipalName());
        });
    }

    private static void setFailureHandler(AuthorizedClientServiceOAuth2AuthorizedClientManager manager) {
        manager.setAuthorizationFailureHandler((authorizationException, principal, attributes) -> {
            throw new M2MAuthenticationFailed(authorizationException.getError().toString(), principal);
        });
    }

    private static void setContextAttributesMapper(AuthorizedClientServiceOAuth2AuthorizedClientManager manager) {
        manager.setContextAttributesMapper(oAuth2AuthorizeRequest -> {
            Map<String, Object> attributes = new HashMap<>();
            if (!oAuth2AuthorizeRequest.getAttributes().isEmpty()) {
                attributes.putAll(oAuth2AuthorizeRequest.getAttributes());
            }
            attributes.put("Service", "OrderService");
            attributes.put("Communication", "m2m");
            attributes.put("AuthorizationGrantType", "ClientCredentials");
            return attributes;
        });
    }


}
