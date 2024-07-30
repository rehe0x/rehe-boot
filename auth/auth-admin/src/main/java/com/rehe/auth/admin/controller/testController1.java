package com.rehe.auth.admin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ceshi123")
@RequiredArgsConstructor
@Tag(name = "接口示例", description = "接口示例")
@ApiSupport(order = 10)
public class testController1 {
    @GetMapping("")
    public String get_v1() {
        return "hello lsdffdsf";
    }

}
