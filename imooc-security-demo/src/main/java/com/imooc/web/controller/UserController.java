package com.imooc.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.dto.User;
import com.imooc.dto.UserQueryCondition;
import com.imooc.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjie on 2018/6/24.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    //PC基于session社交注册工具类
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    //APP重构基于redis社交注册工具类
//    @Autowired
//    private AppSingUpUtils appSingUpUtils; //APP端社交注册工具类

    @Autowired
    private SecurityProperties securityProperties;

    //jwt 访问授权时 Authentication 包装的信息与jwt原始的信息不一致 Principal 里面是user_name而不是UserDetails对象
    @GetMapping("/me")
    public Object getCurrentUser(Authentication user,HttpServletRequest request /*@AuthenticationPrincipal UserDetails user*/) throws Exception{
        //获取认证信息
        //SecurityContextHolder.getContext().getAuthentication()
        String header = request.getHeader("Authorization");
        String jwtToken = StringUtils.substringAfter(header,"bearer ");
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(jwtToken).getBody();
        String company = (String)claims.get("company");
        log.info("company is {}",company);
        return user;
    }

    /**
     * 对于社交系统第一次登陆的用户需要注册或者绑定到业务系统
     * 不提供自动注册功能 需要用户自己完成注册或者绑定操作 之后需要登录
     * 自动注册无需用户登录
     */

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request){
        //不管是注册用户还是绑定用户，都会拿到一个用户唯一标识
        //TODO 可以根据需要实现注册和绑定逻辑
        String userId = user.getUsername();
        providerSignInUtils.doPostSignUp(userId,new ServletWebRequest(request));
//        appSingUpUtils.doPostSignUp(new ServletWebRequest(request),userId);
    }



    @PutMapping("/{id:\\d+}")
    public User updateUser(@RequestBody @Valid User user, BindingResult errors){
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(e->
            {
                FieldError error = (FieldError)e;
                System.out.println(error.getObjectName()+" "+error.getField()+" "+error.getDefaultMessage());
            });
        }
        System.out.println(user);
        user.setId(1);
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody @Valid User user, BindingResult errors){
        if(errors.hasErrors()){
            errors.getAllErrors().stream().forEach(e-> System.out.println(e.getObjectName()+" "+((FieldError)e).getField()+" "+e.getDefaultMessage()));
        }
        System.out.println(user);
        user.setId(1);
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id){
        System.out.println(id);
    }

    @GetMapping
    @JsonView(User.UserSampleView.class)
    @ApiOperation(value="用户查询服务",notes = "用户查询服务")
    public List<User> query(UserQueryCondition userQueryCondition, @PageableDefault(page = 1,size=10,sort = "username,asc") Pageable pageable){
        System.out.println(ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getSort());
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());
        return users;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    public User getUserInfo(@ApiParam("用户id") @PathVariable String id){
        System.out.println("进入getUserInfo服务");
        System.out.println(id);
        User user = new User();
        user.setUsername("tom");
        return user;
    }


}
