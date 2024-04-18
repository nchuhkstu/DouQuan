package com.nchu.feign.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user_article
 */

@Data
@AllArgsConstructor
public class UserArticle implements Serializable {
    private Integer userid;

    private Integer articleid;

    private static final long serialVersionUID = 1L;
}