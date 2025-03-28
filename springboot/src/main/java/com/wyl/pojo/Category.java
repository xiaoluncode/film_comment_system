package com.wyl.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
@作者：wyl
@创建时间：2025/1/31 4:37  
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@TableName("category")
public class Category {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
}
