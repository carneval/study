package com.cjy.webflux.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
@RestController
//如果是Controller就会报错 无法解析"some string"视图
public class WebFluxController {

    //阻塞5秒钟
    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {

        }
        return "some string";
    }

    //普通的SpringMVC方法
    @GetMapping("/mvc")
    public String getMvc() {
        log.info("getMvc start");
        String result = createStr();
        log.info("getMvc end.");
        return result;
    }

    //Mono 返回0-1个元素
    @GetMapping("/flux")
    public Mono<String> getFlux() {
        log.info("getFlux start");
        Mono<String> result = Mono.fromSupplier(() -> createStr());
        log.info("getFlux end.");
        return result;
    }

    //Flux 返回0-n个元素
    @GetMapping(value = "/fluxSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        Flux<String> result = Flux
          .fromStream(IntStream.range(1, 5).mapToObj(i -> {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {

                }
                return "flux data -- " + i;
        }));
        return result;
    }

}
