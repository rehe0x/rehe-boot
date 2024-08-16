package com.rehe.modules.admin.storage.dto.request;

import com.rehe.common.exception.BusinessException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="分段信息验证")
@Data
public class PartCheckDetailDto {
    @Schema(description = "分段编号从1开始")
    @NotNull
    @Min(1)
    @Max(10000)
    private Integer partNumber;

    @Schema(description = "md5")
    private String md5hex;

    @Schema(description = "size")
    @NotNull
    @Min(1)
    private Long size;

    public String getMd5hex(boolean b) {
        if(b && !StringUtils.hasText(md5hex)) {
            throw new BusinessException("part md5 is null");
        }
        return md5hex;
    }
}
