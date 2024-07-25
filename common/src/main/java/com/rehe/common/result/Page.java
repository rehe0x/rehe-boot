package com.rehe.common.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Data
@NoArgsConstructor
public class Page<T> {
    private int pageNum;
    private int pageSize;
    private long total;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Schema(hidden = true)
    protected List<T> list;

    private Page(PageInfo<?> pageInfo, List<T> list) {
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
        this.total = pageInfo.getTotal();
        this.list = list;
    }

    public static <T> Page<T> of(PageInfo<?> pageInfo,List<T> list) {
      return new Page<>(pageInfo,list);
    }

    private Page(Page<T> page, List<T> list) {
        this.pageNum = page.getPageNum();
        this.pageSize = page.getPageSize();
        this.total = page.getTotal();
        this.list = page.getList();
    }


}
