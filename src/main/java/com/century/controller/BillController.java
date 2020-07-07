package com.century.controller;

import com.century.service.BillService;
import com.century.service.UserService;
import com.century.vo.Bill;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Resource
    private BillService billService;
    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping("/buyTicket")
    public String buyTicket(@RequestBody Map<String, Object> objectMap, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String userName = "";
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userName")){
                    userName = cookie.getValue();
                }
            }
        }
        String airCode = (String)objectMap.get("airCode");
        int userId = userService.queryIdByUserName(userName);
        Bill bill = new Bill();
        bill.setAirCode(airCode);
        bill.setUserId(userId);
        int ok = billService.addBill(bill);
        if(ok > 0){
            return airCode;
        }
        return "空";
    }
}
