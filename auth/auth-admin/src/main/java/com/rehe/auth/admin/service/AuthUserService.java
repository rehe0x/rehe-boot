package com.rehe.auth.admin.service;

import com.rehe.auth.admin.dto.AuthMenuDto;
import com.rehe.auth.admin.dto.AuthUserDto;
import com.rehe.auth.admin.dto.JwtUserDto;
import com.rehe.auth.admin.dto.AuthorityDto;
import com.rehe.auth.admin.entity.User;
import com.rehe.auth.admin.mapper.AuthUserMapper;
import com.rehe.auth.admin.mapstruct.AuthUserMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserMapper authUserMapper;

    public Optional<AuthUserDto> findByUsername(String username){
        User user = authUserMapper.selectByUsername(username);
        return Optional.ofNullable(AuthUserMapstruct.INSTANCE.toDto(user));
    }

    public Optional<AuthUserDto> findByPhone(String phone){
        User user = authUserMapper.selectByPhone(phone);
        return Optional.ofNullable(AuthUserMapstruct.INSTANCE.toDto(user));
    }

    public Optional<AuthUserDto> findByOpenId(String openId){
        User user = authUserMapper.selectByOpenId(openId);
        return Optional.ofNullable(AuthUserMapstruct.INSTANCE.toDto(user));
    }

    public Optional<AuthUserDto> findByUserId(Long userId){
        User user = authUserMapper.selectByUserId(userId);
        return Optional.ofNullable(AuthUserMapstruct.INSTANCE.toDto(user));
    }

    public List<AuthMenuDto> getUserMenus(Integer platformId, Long userId){
        if(platformId == null){
            List<Integer> platformList = authUserMapper.selectPlatformByUser(userId);
            if(CollectionUtils.isEmpty(platformList)){
                return Collections.emptyList();
            }
            platformId = platformList.get(0);
        }

        List<AuthMenuDto> menuDtoList = authUserMapper.selectMenuByUser(platformId, userId);
        if(CollectionUtils.isEmpty(menuDtoList)){
            return Collections.emptyList();
        }
        return new ArrayList<>(menuDtoList.stream()
                .collect(Collectors.toMap(
                        AuthMenuDto::getId,
                        Function.identity(),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new))
                .values());
    }

    public Set<AuthorityDto> getUserAuthorities(Integer platformId, Long userId){
        List<AuthMenuDto> menuDtoList = this.getUserMenus(platformId, userId);

        return menuDtoList.stream()
                .filter(menuDto -> menuDto.getMenuType() != 0 && StringUtils.hasText(menuDto.getPermission()))
                .map(menuDto -> new AuthorityDto(menuDto.getPermission()))
                .collect(Collectors.toSet());
    }


}
