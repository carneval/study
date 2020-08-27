package com.cjy.webflux.exception;

import lombok.Data;

@Data
public class CheckException extends Exception{

    private String fieldName;
    private String fieldValue;
}
