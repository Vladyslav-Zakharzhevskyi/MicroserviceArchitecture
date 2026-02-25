package org.homecorporation.exception;

import org.springframework.security.core.Authentication;

public class M2MAuthenticationFailed extends RuntimeException {
    public M2MAuthenticationFailed(String service, Authentication principal) {
        System.out.println("Issue with m2m Authorization for service: " + service + "with principal: " + principal.getName());
    }
}
