package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.handler.FileUploadException;
import org.example.mapper.ProcessTemplateMapper;
import org.example.model.process.ProcessTemplate;
import org.example.result.ResultCodeEnum;
import org.example.service.ProcessService;
import org.example.service.ProcessTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {

    @Autowired
    private ProcessService processService;
    @Override
    public Map<String, Object> uploadProcessDefinition(MultipartFile file) throws Exception
    {

        //TODO 计算文件的md5值，可以判断文件的唯一性，配合redis缓存文件的md5值（key：MD5,value:文件路径）避免文件重复传输（大文件妙传）



        int hashCode = file.getBytes().hashCode();
        System.out.println(hashCode);
        //获取文件名称
        String originalFilename = file.getOriginalFilename();
        String name = UUID.randomUUID().toString();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));

        //获取项目的相对路径
        String absolutePath = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath() + "." + suffix;
        //流程定义文件上传的位置
        File tempFile = new File(absolutePath + "/processes/");
        if (!tempFile.exists()) tempFile.mkdirs();
        //流程定义文件写入的目标文件
        File targetFile = new File(absolutePath + "/processes/" + name);
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            throw new FileUploadException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
        Map<String, Object> result = new HashMap<>();
        //文件存储位置
        result.put("processDefinitionPath", "processes/" + name);
        //文件后缀
        result.put("suffix", suffix);
        //文件原名
        result.put("originalFilename", originalFilename);

        result.put("processDefinitionKey", originalFilename.substring(0, originalFilename.indexOf(".")));
        return result;
    }

    @Override
    public void publish(Long id)
    {
        ProcessTemplate processTemplate = this.getById(id);
        processTemplate.setStatus(1);
        baseMapper.updateById(processTemplate);

        //部署流程定义，后续完善
        //获取流程模板中流程定义的文件路径
        String processDefinitionPath = processTemplate.getProcessDefinitionPath();

        if(!StringUtils.isEmpty(processDefinitionPath)) processService.PublishByZip(processDefinitionPath);
    }
}
