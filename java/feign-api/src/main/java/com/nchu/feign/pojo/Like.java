package com.nchu.feign.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName like
 */
@AllArgsConstructor
@Data
public class Like implements Serializable {
    private Integer likeid;

    private Integer userid;

    private Date liketime;

    private static final long serialVersionUID = 1L;
}