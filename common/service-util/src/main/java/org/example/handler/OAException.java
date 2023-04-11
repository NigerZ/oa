package org.example.handler;

import lombok.Data;
import org.example.result.ResultCodeEnum;

@Data
public class OAException extends RuntimeException{
    private Integer code;
    private String msg;

    public OAException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public OAException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
