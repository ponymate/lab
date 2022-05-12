package com.mjw.lab.service.impl;

import com.mjw.lab.dao.parm.User;
import com.mjw.lab.dao.pojo.Borrower;
import com.mjw.lab.dao.vo.Result;
import com.mjw.lab.service.BorrowerService;
import com.mjw.lab.service.SelfService;
import com.mjw.lab.utils.PermissionUtils;
import com.mjw.lab.utils.UserThreadLocal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author:Ponymate
 * @Description:
 * @Date:Created in 16:48 2022/4/12
 * @Modified By:
 */
@Service
public class SelfServiceImpl implements SelfService {

    @Autowired
    BorrowerService borrowerService;

    @Autowired
    PermissionUtils permissionUtils;

    @Override
    public Result getBorrowerHome(Long id) {
        Borrower borrower = UserThreadLocal.get();
        return Result.success(borrower);
    }

    @Override
    public Result updateBorrower(Long id, User borrower) {
        Borrower user = UserThreadLocal.get();
        String type = user.getType();
        if(permissionUtils.isNotSelf(id))
            return Result.fail(1005,"Insufficient permissions");
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        BeanUtils.copyProperties(borrower,user1);
        switch (type) {
            case "teacher":
                return borrowerService.updateTeacher(borrower);
            case "student":
                return borrowerService.updateStudent(borrower);
            case "outsider":
                return borrowerService.updateOutsider(borrower);
            default:
                return Result.fail(1006, "type is wrong");
        }
    }

    @Override
    public Result deleteBorrower(Long id, String type) {
        switch (type) {
            case "teacher":
                return borrowerService.deleteTeacher(id);
            case "student":
                return borrowerService.deleteStudent(id);
            case "outsider":
                return borrowerService.deleteOutsider(id);
            default:
                return Result.fail(1006, "type is wrong");
        }
    }




}
