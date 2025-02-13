package com.rehe.admin.modules.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.log.OperationLog;
import com.rehe.common.result.Page;
import com.rehe.biz.core.common.dto.PageParamDto;
import com.rehe.admin.modules.system.dto.DictDto;
import com.rehe.admin.modules.system.dto.reqeust.DictCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DictUpdateDto;
import com.rehe.admin.modules.system.mapstruct.DictMapstruct;
import com.rehe.admin.modules.system.mapstruct.UserMapstruct;
import com.rehe.admin.modules.system.service.DictDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.rehe.admin.modules.system.mapper.DictMapper;
import com.rehe.admin.modules.system.entity.Dict;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @description
 * @author rehe
 * @date 2024/8/2
 */
@RequiredArgsConstructor
@Service
public class DictService{

    private final DictMapper dictMapper;

    private final DictDetailService dictDetailService;


    public void createDict(DictCreateDto dictCreateDto){
        Dict entity = DictMapstruct.INSTANCE.toEntity(dictCreateDto);
        entity.setCreateTime(LocalDateTime.now());
        validateDictCode(entity.getCode(),null);
        dictMapper.insertSelective(entity);
    }

    @OperationLog(value = "修改字典")
    public void updateDict(DictUpdateDto dictUpdateDto){
        Dict dict = getById(dictUpdateDto.getId());
        Dict entity = DictMapstruct.INSTANCE.toEntity(dictUpdateDto);
        entity.setId(dict.getId());
        entity.setUpdateTime(LocalDateTime.now());
        validateDictCode(entity.getCode(),dict.getCode());
        dictMapper.updateByPrimaryKeySelective(entity);
    }

    public void deleteDict(Long id){
        Dict dict = getById(id);
        dictMapper.deleteByPrimaryKey(dict.getId());
        dictDetailService.deleteByDictId(dict.getId());
    }

    public Page<DictDto> queryDict(PageParamDto pageParamDto){
        PageHelper.startPage(pageParamDto.getPageNum(), pageParamDto.getPageSize());
        List<Dict> dictList = dictMapper.selectAll();
        return Page.of(new PageInfo<>(dictList), DictMapstruct.INSTANCE.toDto(dictList));
    }

    public Optional<DictDto> findDictById(Long id){
        return Optional.ofNullable(DictMapstruct.INSTANCE.toDto(findById(id)));
    }

    private Dict getById(Long id){
        Dict dict = dictMapper.selectByPrimaryKey(id);
        if(dict == null){
            throw new BusinessException("字典不存在!");
        }
        return dict;
    }

    private Dict findById(Long id){
        return dictMapper.selectByPrimaryKey(id);
    }

    private void validateDictCode(String code,String oldCode){
       Dict dict = dictMapper.selectByCode(code);
       if(dict != null && !dict.getCode().equals(oldCode)) {
           throw new BusinessException("code已存在");
       }
    }

}
