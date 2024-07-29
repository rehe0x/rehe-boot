package com.rehe.modules.admin.system.service;

import com.rehe.common.exception.BusinessException;
import com.rehe.modules.admin.system.dto.DeptDto;
import com.rehe.modules.admin.system.dto.reqeust.DeptCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.DeptUpdateDto;
import com.rehe.modules.admin.system.dto.response.DeptResponseDto;
import com.rehe.modules.admin.system.mapstruct.DeptMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rehe.modules.admin.system.mapper.DeptMapper;
import com.rehe.modules.admin.system.entity.Dept;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Service
@RequiredArgsConstructor
public class DeptService{

    private final DeptMapper deptMapper;
    @Lazy
    private final UserService userService;

    public void createDept(DeptCreateDto deptCreateDto) {
        Dept entity = DeptMapstruct.INSTANCE.toEntity(deptCreateDto);
        entity.setCreateTime(LocalDateTime.now());
        validateParentDept(entity.getParentId());
        deptMapper.insertSelective(entity);
    }

    public void updateDept(DeptUpdateDto deptUpdateDto) {
        Dept dept = getById(deptUpdateDto.getId());
        Dept entity = DeptMapstruct.INSTANCE.toEntity(deptUpdateDto);
        entity.setId(dept.getId());
        validateParentDept(entity.getParentId());
        entity.setUpdateTime(LocalDateTime.now());
        deptMapper.updateByPrimaryKeySelective(entity);
    }

    public void deleteDept(Long id) {
        Dept dept = getById(id);
        List<Dept> deptList = deptMapper.selectAll();
        Map<Long, List<Dept>> parentToChildrenMap = deptList.stream()
                .collect(Collectors.groupingBy(Dept::getParentId));
        List<Long> childIds = getDeptChildIds(parentToChildrenMap, dept.getId(), new ArrayList<>());
        childIds.add(dept.getId());
        Optional.ofNullable(userService.findUserByDeptIds(childIds)).orElseThrow(() -> new BusinessException("该部门下存在有效用户，无法删除")) ;
        deptMapper.deleteByPrimaryKeys(childIds);
    }


    public List<DeptDto> queryDept() {
       List<Dept> deptList = deptMapper.selectAll();
       return DeptMapstruct.INSTANCE.toDto(deptList);
    }

    public DeptDto getDeptById(Long id) {
        return DeptMapstruct.INSTANCE.toDto(getById(id));
    }

    public Optional<List<DeptDto>> getDeptAll(){
        List<Dept> deptList = deptMapper.selectAll();
        return Optional.of(DeptMapstruct.INSTANCE.toDto(deptList));
    }

    private Dept getById(Long id) {
        return Optional.ofNullable(deptMapper.selectByPrimaryKey(id))
                .orElseThrow(() ->
                new BusinessException(String.format("该部门不存在 %s",id)));
    }

    private Optional<Dept>  findById(Long id) {
        return Optional.ofNullable(deptMapper.selectByPrimaryKey(id));
    }

    private List<Long> getDeptChildIds(Map<Long, List<Dept>> parentToChildrenMap, Long parentId, List<Long> childIds) {
        List<Dept> childList = parentToChildrenMap.get(parentId);
        if(childList == null){
            return childIds;
        }
        for (Dept dept : childList) {
            childIds.add(dept.getId());
            getDeptChildIds(parentToChildrenMap, dept.getId(), childIds);
        }
        return childIds;
    }

    /**
     * 验证上级部门
     */
    private Dept validateParentDept(Long parentId) {
        if(parentId == null || parentId.equals(0L)){
            return null;
        }
        Optional<Dept> dept = findById(parentId);
        return dept.orElseThrow(() -> new BusinessException(String.format("上级不存在 %s", parentId)));
    }
}
