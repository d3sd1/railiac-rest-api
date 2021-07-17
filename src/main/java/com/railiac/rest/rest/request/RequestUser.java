package com.railiac.rest.rest.request;

import com.railiac.rest.database.model.User;
import lombok.*;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Setter
@Getter
@ToString
public class RequestUser {
    private User user;

}
