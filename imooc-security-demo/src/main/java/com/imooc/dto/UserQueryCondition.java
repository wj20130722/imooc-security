package com.imooc.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wangjie on 2018/6/24.
 */
@Data
@ApiModel(description="用户查询条件")
public class UserQueryCondition {
    @ApiModelProperty(value="用户名",name="username")
    private String username;
    @ApiModelProperty(value="用户年龄",name="age")
    private int age;
}
