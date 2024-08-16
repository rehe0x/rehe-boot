package com.rehe.storage.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
@Builder
@Data
public class PartCheckRequest {
    private Integer partNumber;
    private String eTag;
    private long size;

}
