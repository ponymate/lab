package com.mjw.lab.utils;


import com.mjw.lab.dao.pojo.Borrower;

public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<Borrower> LOCAL=new ThreadLocal<>();

    public static void set(Borrower user){LOCAL.set(user);}

    public static Borrower get(){return LOCAL.get();}

    public static void remove(){LOCAL.remove();}
}
