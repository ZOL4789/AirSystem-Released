package com.century.controller;

import com.century.service.UserService;
import com.century.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/updatePassword")
    public String updatePassword(String oldPassword,String newPassword, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        User user=new User();
        String userName = "";
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userName")){
                    userName = cookie.getValue();
                }
            }
        }
        user.setName(userName);
        user.setPassword(newPassword);
        if(oldPassword.equals(userService.queryPasswordByUserName(userName))){
            userService.updatePassword(user);
            return "false";
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/getPersonalInfo")
    public User getPersonalInfo(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String userName = "";
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userName")){
                    userName = cookie.getValue();
                }
            }
        }
        User user = userService.queryInfoByUserName(userName);
        return user;
    }


    @ResponseBody
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userName")){
                    cookie.setMaxAge(0);            //删除cookie
                }
            }
        }
    }

    @ResponseBody
    @RequestMapping("/getUserName")
    public String getUserName(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String userName = "";
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userName")){
                    userName = cookie.getValue();
                }
            }
        }
        return userName;
    }

    @RequestMapping("/login")
    public ModelAndView login(String user_Name, String user_Password, HttpServletResponse response, HttpSession session){
        String loginResult=userService.login(user_Name,user_Password);
        if (loginResult.equals("用户登录成功")){
            Cookie cookie = new Cookie("userName",user_Name);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
            session.removeAttribute("result");
            RedirectView redirectView=new RedirectView("../jsp/home.jsp",true,true);
            ModelAndView modelAndView=new ModelAndView(redirectView);
            return modelAndView;
        }
        session.setAttribute("result",loginResult);
        RedirectView redirectView2=new RedirectView("../jsp/login.jsp",true,true);
        ModelAndView modelAndView=new ModelAndView(redirectView2);
        return modelAndView;
    }

    @RequestMapping("/register")
    public ModelAndView register(String userName, String password, String phone,String email,HttpSession session){
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        user.setPhone(phone);
        user.setEmail(email);
        String rjresult=userService.register(user);
        if (rjresult.equals("用户注册成功")){
            session.setAttribute("result",rjresult);
            RedirectView redirectView=new RedirectView("../jsp/login.jsp",true,true);
            ModelAndView modelAndView=new ModelAndView(redirectView);
            return modelAndView;
        }
        session.setAttribute("result2",rjresult);
        RedirectView redirectView2=new RedirectView("../jsp/register.jsp",true,true);
        ModelAndView modelAndView=new ModelAndView(redirectView2);
        return modelAndView;
    }

}
