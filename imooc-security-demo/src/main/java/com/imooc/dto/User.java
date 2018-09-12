package com.imooc.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.imooc.validator.MyConstraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * Created by wangjie on 2018/6/24.
 */
@Data
public class User {

    public interface UserSampleView{

    }

    public interface UserDetailView extends UserSampleView{

    }

    @JsonView(UserSampleView.class)
    private int id;

    @JsonView(UserSampleView.class)//UserSampleView视图只显示用户名
    @MyConstraint(message = "这是一个测试！")
    private String username;

    @JsonView(UserDetailView.class) //UserDetailView视图显示用户名和密码
    @NotBlank(message = "密码不能为空！")
    private String password;

    @JsonView(UserSampleView.class)
    @Past(message = "生日必须是过去的时间！")
    private Date birthday;
}
