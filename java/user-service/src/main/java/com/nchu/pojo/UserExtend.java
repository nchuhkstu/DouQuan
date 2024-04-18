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
 * @TableName user_extend
 */
@AllArgsConstructor
@TableName(value ="user_extend")
@Data
public class UserExtend implements Serializable {
    @TableId
    private Integer userid;

    private Integer avatarid;

    private String gender;

    private Date birthday;

    private Date registrationtime;

    private String selfintroduction;

    private String name;

    private static final long serialVersionUID = 1L;
}