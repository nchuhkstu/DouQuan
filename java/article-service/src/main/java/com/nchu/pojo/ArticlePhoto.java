package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName article_photo
 */
@TableName(value ="article_photo")
@Data
public class ArticlePhoto implements Serializable {
    private Integer articleid;
    @TableId
    private Integer photoid;

    private static final long serialVersionUID = 1L;
}