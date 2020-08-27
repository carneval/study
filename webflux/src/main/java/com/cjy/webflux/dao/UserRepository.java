package com.cjy.webflux.dao;

import com.cjy.webflux.pojo.User;
import lombok.Data;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Data
@Service
public class UserRepository {

    public Flux<User> findAll(){
        return null;
    }

    public Flux<User> save(User user) {
        return null;
    }


    public Flux<User> saveAll(Publisher<User> entityStream){
        return null;
    }

    public Mono<User> findById(String id) {
        return null;
    }

    public Mono<Void> delete(User user){
        return null;
    }
}
