package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName user_photo
 */
@TableName(value ="user_photo")
@Data
public class UserPhoto implements Serializable {
    private Integer userid;
    @TableId
    private Integer photoid;

    private static final long serialVersionUID = 1L;
}