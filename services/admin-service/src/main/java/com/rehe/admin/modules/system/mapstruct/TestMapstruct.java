package com.rehe.admin.modules.system.mapstruct;//package com.rehe.admin.modules.system.mapstruct;
//
//import com.rehe.common.mapstruct.MapstructBaseMapper;
//import com.rehe.admin.modules.system.entity.Menu;
//import com.rehe.admin.modules.system.entity.User;
//import com.rehe.admin.modules.system.vo.MenuVo;
//import com.rehe.admin.modules.system.vo.TestVo;
//import com.rehe.admin.modules.system.vo.UserVo;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import org.mapstruct.ReportingPolicy;
//
//import java.util.List;
//
///**
// * @author xiech
// * @description
// * @date 2024/1/8
// */
//@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
//public interface TestMapstruct  extends MapstructBaseMapper<TestVo, User> {
//
//
//    @Mappings({
//       @Mapping(source = "menuList", target = "menuListVo")
//    })
//    @Override
//    TestVo toTarget(User entity);
//
//
//
//
//    List<MenuVo> convertBeanList(List<Menu> menuList);
//
//    @Mappings({
//            @Mapping(source = "userList", target = "userListVo")
//    })
//    MenuVo menuToVO(Menu entity);
//
//
//    List<UserVo> convertBeanUserList(List<User> userList);
//
//}
