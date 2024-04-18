package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @TableName user_login
 */
@TableName(value ="user_login")
@AllArgsConstructor
@Data
public class UserLogin implements Serializable {
    private Integer userid;

    private Date logintime;

    private String loginplace;

    private static final long serialVersionUID = 1L;
}