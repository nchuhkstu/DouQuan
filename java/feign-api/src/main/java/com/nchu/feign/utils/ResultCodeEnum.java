package com.nchu.feign.utils;

/**
 * 统一返回结果状态信息类
 *
 */
public enum ResultCodeEnum {

    SUCCESS(200,"success"),
    ERROR(500,"unknownError"),
    USERNAME_ERROR(501,"usernameError"),
    PASSWORD_ERROR(502,"passwordError"),
    NOTLOGIN(504,"notLogin"),
    USERNAME_USED(503,"userNameUsed"),
    NAME_USED(506,"nameUsed"),
    MONEY_NOT_ENOUGH(507,"moneyNotEnough"),
    UPLOAD_ERROR(508,"uploadError"),
    LIKED_ERROR(509,"likedError");



    private Integer code;
    private String message;
    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
