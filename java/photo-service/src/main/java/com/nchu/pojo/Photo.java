package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName photo
 */
@TableName(value ="photo")
@Data
public class Photo implements Serializable {
    @TableId
    private Integer photoid;

    private String url;

    private Date time;

    private static final long serialVersionUID = 1L;
}