package org.example.projectEnum;

import lombok.Getter;

/**
 * 流程状态枚举类
 */
@Getter
public enum ProcessStatusEnum {

    DEFAULT(0, "默认"),
    APPROVAL(1, "审批中"),
    APPROVED(2, "审批通过"),
    TURN_DOWN(-1, "驳回")
    ;

    private Integer key;
    private String val;

    private ProcessStatusEnum(Integer key, String val) {
        this.key = key;
        this.val = val;
    }
}
