package com.imooc.exception;

/**
 * Created by wangjie on 2018/6/27.
 */
public class UserNotExistException extends RuntimeException{
    public UserNotExistException() {
        super();
    }

    private String id;


    public UserNotExistException(String message,String id) {
        super(message);
        this.id = id;
    }

    public UserNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
