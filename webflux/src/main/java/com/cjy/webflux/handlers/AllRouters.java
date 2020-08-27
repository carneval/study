package com.cjy.webflux.handlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.awt.*;

@Configuration
public class AllRouters {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler handler) {
        return RouterFunctions.nest(
          //相当于类上面的@RequestMapping("/user")
          RequestPredicates.path("/user"),
          //相当于类里面的@RequestMapping/@GetMapping/@PostMapping
          //得到所有用户
          RouterFunctions.route(
            RequestPredicates.GET("/"),
            handler::getAllUser)
          //创建用户
          .andRoute(
            RequestPredicates.POST("/").and(
              RequestPredicates.accept(MediaType.APPLICATION_JSON)),
            handler::createUser)
          //删除用户
          .andRoute(
            RequestPredicates.DELETE("/{id}"),
            handler::deleteUser)
        );
    }
}
