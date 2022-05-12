package com.mjw.lab.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceAdm {

    private Long id;

    private Boolean sex;

    private String name;

    private String password;

    private boolean exist = true;

    private boolean isAdm = false;

}
