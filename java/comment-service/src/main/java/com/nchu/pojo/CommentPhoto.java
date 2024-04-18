package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName comment_photo
 */
@TableName(value ="comment_photo")
@Data
public class CommentPhoto implements Serializable {
    private Integer photoid;

    private Integer commentid;

    private static final long serialVersionUID = 1L;
}