package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName article_view
 */
@TableName(value ="article_view")
@Data
public class ArticleView implements Serializable {
    private Integer articleid;
    @TableId
    private Integer viewid;

    private static final long serialVersionUID = 1L;
}