package com.mjw.lab.utils;

import com.mjw.lab.dao.pojo.DeviceAdm;

public class AdmThreadLocal {

    private AdmThreadLocal(){}

    private static final ThreadLocal<DeviceAdm> LOCAL=new ThreadLocal<>();

    public static void set(DeviceAdm user){LOCAL.set(user);}

    public static DeviceAdm get(){return LOCAL.get();}

    public static void remove(){LOCAL.remove();}
}
