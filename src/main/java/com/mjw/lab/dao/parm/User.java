package com.mjw.lab.dao.parm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String name;

    private Boolean sex = null;

    private String password;

    private String type;

    private String major;

    private String tutorName;

    private String department;

    private String professionTitle;

    private String professionDirection;

    private String companyName;

    private Long tutorId;

    private Long telephone;

    private Integer age;
}
