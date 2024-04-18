package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName user_article
 */
@TableName(value ="user_article")
@Data
public class UserArticle implements Serializable {
    private Integer userid;
    @TableId
    private Integer articleid;

    private static final long serialVersionUID = 1L;
}