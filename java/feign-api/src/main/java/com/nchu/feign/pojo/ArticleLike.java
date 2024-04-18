package com.nchu.feign.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName article_like
 */
@Data
@AllArgsConstructor
public class ArticleLike implements Serializable {
    private Integer articleid;
    private Integer likeid;

    private static final long serialVersionUID = 1L;
}