package com.century.service;

import com.century.dao.UserDAO;
import com.century.vo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService {
    @Resource
    private UserDAO userDAO;

    public User queryUserById(int id){
        return userDAO.queryUserById(id);
    }
    public int updatePassword(User user){return userDAO.updatePassword(user);}
    public String queryPasswordByUserName(String userName){return userDAO.queryPasswordByUserName(userName);}
    public User queryInfoByUserName(String userName){return userDAO.queryInfoByUserName(userName);}
    public int queryIdByUserName(String userName){return userDAO.queryIdByUserName(userName);}
    public String login(String userName, String userPwd) {
        if (userName==null||"".equals(userName))
            return "用户名不能为空";
        if (userPwd==null||"".equals(userPwd))
            return"用户密码不能为空";
        User user=userDAO.queryUserByName(userName);
        if (null==user)
            return "用户不存在，用户未注册";
        if (!userPwd.equals(user.getPassword()))
            return "用户名与密码不匹配";
        return "用户登录成功";
    }

    public String register(User user){
        String name = user.getName();
        String password = user.getPassword();
        String phone = user.getPhone();
        String email = user.getEmail();
        if (name==null||"".equals(name)){
            return "用户名不能为空";
        }
        if (password==null||"".equals(password)){
            return "请输入密码";
        }
        if (phone==null||"".equals(phone)){
            return "请输入电话号码";
        }
        if (email==null||"".equals(email)){
            return "请输入邮箱";
        }
        Date date =new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        String date1 = sdf.format(date);
        user.setDate(date1);
        int a = userDAO.insert(user);
        if (a>=0)
        {
            return "用户注册成功";
        }
        return "注册失败";
    }
}
