package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.process.ProcessTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ProcessTemplateService extends IService<ProcessTemplate> {

    /**
     * 流程定义文件上传
     * @param file 文件
     * @return
     */
    Map<String, Object> uploadProcessDefinition(MultipartFile file) throws Exception;

    /**
     * 流程发布
     * @param id
     */
    void publish(Long id);
}
