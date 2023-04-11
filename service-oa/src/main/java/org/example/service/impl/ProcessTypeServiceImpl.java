package org.example.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.ProcessTypeMapper;
import org.example.model.process.ProcessTemplate;
import org.example.model.process.ProcessType;
import org.example.service.ProcessTemplateService;
import org.example.service.ProcessTypeService;
import org.example.vo.process.ProcessTemplateVO;
import org.example.vo.process.ProcessTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService {

    @Autowired
    private ProcessTemplateService processTemplateService;

    @Override
    public IPage<ProcessType> index(IPage<ProcessType> pageParam) {
        return this.page(pageParam);
    }

    @Override
    public List<ProcessTypeVO> findProcessType() {
        List<ProcessTypeVO> result = new ArrayList<>();
        //获取所有的流程类型
        List<ProcessType> processTypeList = this.list();
        //获取所有的流程模板
        List<ProcessTemplate> processTemplateList = processTemplateService.list();

        //封装数据
        processTypeList.stream().forEach(item -> {
            ProcessTypeVO processTypeVO = new ProcessTypeVO();
            List<ProcessTemplate> processTemplates = processTemplateList
                    .stream().filter(template -> item.getId().equals(template.getProcessTypeId())).collect(Collectors.toList());
            List<ProcessTemplateVO> processTemplateVOList = processTemplates.stream().map(processTemplate -> {
                ProcessTemplateVO processTemplateVO = new ProcessTemplateVO();
                processTemplateVO.setProcessTemplateName(processTemplate.getName())
                        .setIconUrl(processTemplate.getIconUrl());
                return processTemplateVO;
            }).collect(Collectors.toList());
            processTypeVO.setProcessTypeName(item.getName()).setProcessTemplateList(processTemplateVOList);
        });


        return null;
    }
}
