package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @TableName article_comment
 */
@TableName(value ="article_comment")
@AllArgsConstructor
@Data
public class ArticleComment implements Serializable {
    private Integer articleid;
    @TableId
    private Integer commentid;

    private static final long serialVersionUID = 1L;
}