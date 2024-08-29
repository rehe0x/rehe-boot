package com.rehe.admin.modules.system.service;

import com.rehe.common.exception.BusinessException;
import com.rehe.admin.modules.system.dto.DictDetailDto;
import com.rehe.admin.modules.system.dto.reqeust.DictDetailCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DictDetailUpdateDto;
import com.rehe.admin.modules.system.mapstruct.DictDetailMapstruct;
import com.rehe.admin.modules.system.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.rehe.admin.modules.system.entity.DictDetail;
import com.rehe.admin.modules.system.mapper.DictDetailMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description
 * @author rehe
 * @date 2024/8/2
 */
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
@Service
public class DictDetailService{

    private final DictDetailMapper dictDetailMapper;

    private final DictService dictService;


    public void createDictDetail(DictDetailCreateDto dictDetailCreateDto) {
        DictDetail entity =  DictDetailMapstruct.INSTANCE.toEntity(dictDetailCreateDto);
        entity.setCreateTime(LocalDateTime.now());
        dictService.findDictById(entity.getDictId()).orElseThrow(() -> new BusinessException("字典不存在"));
        dictDetailMapper.insertSelective(entity);
    }

    public void updateDictDetail(DictDetailUpdateDto dictDetailUpdateDto) {
        DictDetail dictDetail = getById(dictDetailUpdateDto.getId());
        DictDetail entity = DictDetailMapstruct.INSTANCE.toEntity(dictDetailUpdateDto);
        entity.setId(dictDetail.getId());
        entity.setDictId(dictDetail.getDictId());
        entity.setUpdateTime(LocalDateTime.now());
        dictDetailMapper.updateByPrimaryKeySelective(entity);
    }

    public void deleteDictDetail(Long id) {
        dictDetailMapper.deleteByPrimaryKey(id);
    }

    public List<DictDetailDto> queryDictDetail(Long dictId){
        List<DictDetail> dictDetailList = dictDetailMapper.selectByDictId(dictId);
        return DictDetailMapstruct.INSTANCE.toDto(dictDetailList);
    }

    public void deleteByDictId(Long dictId) {
        dictDetailMapper.deleteByDictId(dictId);
    }


    private DictDetail getById(Long id) {
        DictDetail dictDetail = dictDetailMapper.selectByPrimaryKey(id);
        if(dictDetail == null) {
            throw new BusinessException("字典明细不存在");
        }
        return dictDetail;
    }


}
