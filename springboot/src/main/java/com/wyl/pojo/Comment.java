package com.wyl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
@作者：wyl
@创建时间：2025/1/31 10:16  
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer filmId;
    private Double score;
    private String comment;
    private Integer userId;
    private String time;
    private String type;
    @TableField(exist = false)
    private String filmName;
    @TableField(exist = false)
    private String userName;


}
