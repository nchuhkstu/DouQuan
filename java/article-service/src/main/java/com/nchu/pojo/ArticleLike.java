package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @TableName article_like
 */
@TableName(value ="article_like")
@AllArgsConstructor
@Data
public class ArticleLike implements Serializable {
    private Integer articleid;
    @TableId
    private Integer likeid;

    private static final long serialVersionUID = 1L;
}