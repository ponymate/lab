package com.mjw.lab.dao.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrower {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String name;

    private Boolean sex;

    private String password;

    private String type;

    private Long telephone;

    private Integer age;

    private boolean exist = true;

}
