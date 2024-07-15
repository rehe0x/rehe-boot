package com.rehe.modules.admin.system.dto;

import com.rehe.modules.admin.common.dto.PageParamDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Schema(description = "菜单列表查询")
@Data
public class MenuQueryDto  {
    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "系统ID")
    @NotNull(message = "系统ID不能为空")
    private Integer platformId;
}
