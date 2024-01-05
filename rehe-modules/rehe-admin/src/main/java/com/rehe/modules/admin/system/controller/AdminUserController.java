package com.rehe.modules.admin.system.controller;

import com.alibaba.fastjson2.JSON;
import com.rehe.modules.admin.system.service.IAdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xiech
 * @since 2024-01-05
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AdminUserController {

    private final IAdminUserService iAdminUserService;

    @GetMapping("/admin")
    public ResponseEntity<String> authenticate() {
        List list = iAdminUserService.list();
        System.out.println(JSON.toJSONString(list));
        return ResponseEntity.ok("ddd");
    }
}
