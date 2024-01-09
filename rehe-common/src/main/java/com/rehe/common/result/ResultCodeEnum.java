package com.rehe.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiech
 * @description
 * @date 2024/1/9
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESSFUL(0,"成功"),
    FAIL(-1, "失败"),
    ERROR(-999,"未知异常");
    private final int code;
    private final String msg;

}
