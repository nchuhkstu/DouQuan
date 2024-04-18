package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @TableName user_avatar
 */
@TableName(value ="user_avatar")
@AllArgsConstructor()
@Data
public class UserAvatar implements Serializable {
    private Integer photoid;
    private Integer userid;

    private static final long serialVersionUID = 1L;
}