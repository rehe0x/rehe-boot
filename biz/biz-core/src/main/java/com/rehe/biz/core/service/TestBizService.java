package com.rehe.biz.core.service;



import com.rehe.biz.core.mapper.TestBizMapper;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;



/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Service
@RequiredArgsConstructor
public class TestBizService {
    private final TestBizMapper testBizMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public void createUser() {
        testBizMapper.deleteByPrimaryKey(1L);
    }


}
