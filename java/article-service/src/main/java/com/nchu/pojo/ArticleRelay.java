package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName article_relay
 */
@TableName(value ="article_relay")
@Data
public class ArticleRelay implements Serializable {
    private Integer articleid;
    @TableId
    private Integer relayid;

    private static final long serialVersionUID = 1L;
}