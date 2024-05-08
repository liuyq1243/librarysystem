package com.ls.librarysystem.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    // 响应码
    private int code;
    private String msg;
    private Object result;
}
