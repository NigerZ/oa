package org.example.handler;

import lombok.Data;
import org.example.result.ResultCodeEnum;

@Data
public class FileUploadException extends Exception{
    private Integer code;
    private String msg;

    public FileUploadException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public FileUploadException(ResultCodeEnum resultCodeEnum) {
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
