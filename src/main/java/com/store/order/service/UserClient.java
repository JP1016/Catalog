package com.store.order.service;

import com.store.order.model.internal.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "UserService", url = "${server.userUrl}")
public interface UserClient {
    @RequestMapping(method = RequestMethod.GET, value = "api/users/{id}")
    User getUser(@PathVariable("id") String userId);
}