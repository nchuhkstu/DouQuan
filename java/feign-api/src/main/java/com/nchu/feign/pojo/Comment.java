package com.nchu.feign.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName comment
 */
@Data
public class Comment implements Serializable {
    private Integer commentid;

    private Integer userid;

    private String content;

    private Date commenttime;

    private static final long serialVersionUID = 1L;
}