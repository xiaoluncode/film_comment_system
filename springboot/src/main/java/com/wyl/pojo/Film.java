package com.wyl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Year;

/*
@作者：wyl
@创建时间：2025/1/31 5:33  
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@TableName("film")
public class Film {
    @TableId(type = IdType.AUTO)  // 添加此注解，使用数据库自增策略
    private Integer id;
    private String name;
    private String cover;
    private String descr;
    private Year year;
    private String director;
    private String actors;
    @TableField("category_id") // 映射数据库字段
    private Integer categoryId;
    private String country;
    private String language;
    private String date;
    private String duration;
    private String score;
    @TableField(exist = false)
    private String categoryName;  // 分类名称
    @TableField(exist = false)
    private Integer commentNum;
    @TableField(exist = false)
    private Double TotalScore;
}
