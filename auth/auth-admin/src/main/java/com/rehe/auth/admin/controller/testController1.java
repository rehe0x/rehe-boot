package com.rehe.auth.admin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.auth.admin.dto.ApiDemo2Dto;
import com.rehe.auth.admin.dto.ApiDemoDto;
import com.rehe.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
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
