package org.example.projectEnum;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ProcessRecordEnum {

    DEFAULT(0, "默认"),
    SUCCESS(1, "成功")
    ;

    private Integer code;
    private String msg;

    private ProcessRecordEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
