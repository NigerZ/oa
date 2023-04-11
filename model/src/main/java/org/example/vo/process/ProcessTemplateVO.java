package org.example.vo.process;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ProcessTemplateVO implements Serializable {

    @ApiModelProperty(value = "图标")
    private String iconUrl;

    @ApiModelProperty(value = "模板名称")
    private String processTemplateName;
}
