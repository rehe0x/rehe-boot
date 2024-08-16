package com.rehe.storage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
@Builder
@Data
@AllArgsConstructor
public class PartCheckResponse {
    private Integer partNumber;
    private String eTag;
    private long size;
    private boolean completion;
}
