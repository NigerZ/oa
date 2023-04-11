package org.example.handler;

import lombok.Data;
import lombok.ToString;
import org.example.result.ResultCodeEnum;

@ToString
@Data
public class LoginException extends RuntimeException{

    private Integer code;

    private String msg;

    public LoginException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public LoginException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }
}
