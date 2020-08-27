package com.cjy.webflux.handlers;

import com.cjy.webflux.dao.UserRepository;
import com.cjy.webflux.pojo.User;
import com.cjy.webflux.util.CheckUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


@Component
public class UserHandler {

    private final UserRepository repository;

    public UserHandler(UserRepository rep) {
        this.repository = rep;
    }

    //获取所有用户
    public Mono<ServerResponse> getAllUser(ServerRequest request) {
        return ServerResponse.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(this.repository.findAll(), User.class);
    }

    //创建用户
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
//        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
//          .body(this.repository.saveAll(user), User.class);

        return user.flatMap(u -> {
            //校验代码
            CheckUtil.checkName(u.getName());

            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(this.repository.save(u), User.class);
        });
    }

    //删除用户
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return this.repository.findById(id)
          .flatMap(user -> this.repository.delete(user).then(ServerResponse.ok().build()))
          .switchIfEmpty(ServerResponse.notFound().build());
    }
}
