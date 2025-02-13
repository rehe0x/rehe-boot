package com.rehe.scheduled.modules.task.mapstruct;


import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.common.result.Page;
import com.rehe.scheduled.modules.task.dto.ScheduledTaskDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskCreateDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskUpdateDto;
import com.rehe.scheduled.modules.task.dto.response.ScheduledTaskResonseDto;
import com.rehe.scheduled.modules.task.entity.ScheduledTask;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ScheduledTaskMapstruct extends MapstructDtoBaseMapper<ScheduledTaskDto, ScheduledTask> {
    ScheduledTaskMapstruct INSTANCE = Mappers.getMapper(ScheduledTaskMapstruct.class);

    Page<ScheduledTaskResonseDto> toScheduledTaskResponseDto(Page<ScheduledTaskDto> page);

    ScheduledTaskResonseDto toScheduledTaskResponseDto(ScheduledTaskDto scheduledTaskDto);

    ScheduledTask toEntity(ScheduledTaskCreateDto scheduledTaskCreateDto);

    ScheduledTask toEntity(ScheduledTaskUpdateDto scheduledTaskUpdateDto);

}
