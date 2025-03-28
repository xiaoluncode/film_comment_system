package com.wyl.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
@作者：wyl
@描述：
@创建时间：2025/1/7 18:17  
*/
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Notice {
    private Integer id;
    private String title;
    private String content;
    private String time;
}
