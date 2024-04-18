package com.nchu.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @TableName like
 */
@AllArgsConstructor
@TableName(value ="`like`")
@Data
public class Like implements Serializable {
    @TableId
    private Integer likeid;

    private Integer userid;

    private Date liketime;

    private static final long serialVersionUID = 1L;
}