package com.rehe.common.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/7/4
 */
@Getter
@Setter
public class ResultPage<T> extends Result<List<T>> {
    private Page<T> page;
    private ResultPage(List<T> data, Page<T> page) {
        super(data);
        page.setList(null);
        this.page = page;
    }

    private ResultPage() {
        super();
    }

    public static <T> ResultPage<T> ok(Page<T> page) {
        return new ResultPage<>(page.getList(), page);
    }
}

