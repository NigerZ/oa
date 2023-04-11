package org.example.vo.process;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class ProcessTypeVO implements Serializable {

    @ApiModelProperty(value = "流程类型名称")
    private String processTypeName;

    @ApiModelProperty(value = "流程模板vo")
    private List<ProcessTemplateVO> processTemplateList;
}
