package com.century.controller;

import com.century.service.UserService;
import com.century.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @ResponseBody
    @RequestMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user");
        //User user =  userService.queryUserById(1);
        Map<String,Object> userMap = new HashMap<String, Object>();
        User user = new User();
        user.setAge(12);
        user.setName("fasd");
        userMap.put("user", user);
        modelAndView.addObject("user",user);
        return modelAndView;
    }
}
