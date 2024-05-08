package com.ls.librarysystem.result;

public class ResultFactory {
    public static Result buildResult(int code, String message, Object result) {
        return new Result(code, message, result);
    }

    public static Result buildResult(ResultCode resultCode, String message, Object result) {
        return new Result(resultCode.code, message, result);
    }

    public static Result buildSuccessResult(Object result) {
        return buildResult(ResultCode.SUCCEED, "成功", result);
    }

    public static Result buildFailureResult(String message) {
        return buildResult(ResultCode.FAIL, message, null);
    }
}
