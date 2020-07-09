package com.century.controller;

import com.century.service.BillService;
import com.century.service.TicketService;
import com.century.service.UserService;
import com.century.vo.Bill;
import com.century.vo.Ticket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Resource
    private BillService billService;
    @Resource
    private UserService userService;
    @Resource
    private TicketService ticketService;

    //保存用户订单到数据库中
    @ResponseBody
    @RequestMapping("/buyTicket")
    public boolean buyTicket(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String userName = "";
        String airCode = "";
        if(cookies != null) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("userName")){
                    userName = cookie.getValue();       //获取用户名
                }
                if(cookie.getName().equals("ticket")){
                    String ticketStr = cookie.getValue();
                    airCode = ticketStr.split("&")[1];      //获取用户所点击的航班号
                }
            }
        }
        if (!userName.equals("") && !airCode.equals("")) {
            int ticketId = ticketService.queryIdByAirCode(airCode);
            int userId = userService.queryIdByUserName(userName);
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String dateSimp = simpleDateFormat.format(date);
            Bill bill = new Bill();
            bill.setTicketId(ticketId);
            bill.setUserId(userId);
            bill.setDate(dateSimp);
            int ok = billService.addBill(bill);
            if(ok > 0){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    //获取用户对应的订单
    @ResponseBody
    @RequestMapping("/getBills")
    public Map<String, Object> getBill(HttpServletRequest request){
        Map<String, Object> mapPara = new HashMap<String, Object>();
        Cookie[] cookies = request.getCookies();
        String userName = "";
        if(cookies != null){
            for(Cookie cookie: cookies){
                if(cookie.getName().equals("userName")){
                    userName = cookie.getValue();
                }
            }
        }
        int userId = userService.queryIdByUserName(userName);
        List<Bill> billList = billService.queryBillByUserId(userId);
        if(billList.size() > 0) {
            List<Ticket> ticketList = new ArrayList<Ticket>();
            for (Bill bill : billList) {
                int ticketId = bill.getTicketId();
                Ticket ticket = ticketService.queryTicketById(ticketId);
                ticket.setDate(ticket.getDate().split(" ")[0]);
                ticketList.add(ticket);
            }
            //添加到Map中，返回便于前台接收
            mapPara.put("ticket", ticketList);
            mapPara.put("bill", billList);
        }
        return mapPara;
    }

    //保存用户所点击的航班信息到Cookie中
    @ResponseBody
    @RequestMapping("/setBillToBuy")
    public void setBillToBuy(HttpServletResponse response,@RequestBody Map<String, Object> mapParam){
        String airCode = (String)mapParam.get("airCode");
        String startTime = (String)mapParam.get("startTime");
        String arriveTime = (String)mapParam.get("arriveTime");
        Map<String, Object> mapCondition = new HashMap<String, Object>();
        mapCondition.put("airCode", airCode);
        mapCondition.put("startTime", startTime);
        mapCondition.put("arriveTime", arriveTime);
        Ticket ticket  = ticketService.queryTicketByMapCSA(mapCondition);
        if(ticket != null){
            String company = ticket.getCompany();
            String startDrome = ticket.getstartDrome();
            String arriveDrome = ticket.getArriveDrome();
            String week = ticket.getWeek();
            try {
                String companyEncode = URLEncoder.encode(company,"utf-8");      //中文转码，因为中文保存到Cookie中乱码
                String startDromeEncode = URLEncoder.encode(startDrome,"utf-8");
                String arriveDromeEncode = URLEncoder.encode(arriveDrome,"utf-8");
                String weekEncode = URLEncoder.encode(week,"utf-8");
                String date = ticket.getDate();
                String ticketStr = companyEncode+"&"+
                        ticket.getAirCode()+"&"+
                        startDromeEncode+"&"+
                        arriveDromeEncode+"&"+
                        ticket.getStartTime()+"&"+
                        ticket.getArriveTime()+"&"+
                        ticket.getMode()+"&"+
                        ticket.getAirStop()+"&"+
                        weekEncode+"&"+
                        date.split(" ")[0];
                Cookie cookie = new Cookie("ticket", ticketStr);
                cookie.setMaxAge(60*60*24*7);       //设置七天有效期
                cookie.setPath("/");
                //cookie.setHttpOnly(true);
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }


    //获取用户所点击的航班信息
    @ResponseBody
    @RequestMapping("/getBillToBuy")
    public Ticket getBillToBuy(HttpServletRequest request){
        Ticket ticket = new Ticket();
        String ticketStr = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("ticket")){
                    ticketStr = cookie.getValue();
                }
            }
        }
        if(!ticketStr.equals("")){
            String[] ticketParam = ticketStr.split("&");        //ticket的每个属性以&组成字符串保存在Cookie中，取单个属性
            String companyDecode = ticketParam[0];
            String ariCode = ticketParam[1];
            String startDromeDecode = ticketParam[2];
            String arriveDromeDecode = ticketParam[3];
            String startTime = ticketParam[4];
            String arriveTime = ticketParam[5];
            String mode = ticketParam[6];
            String airStop = ticketParam[7];
            String weekDecode = ticketParam[8];
            String date = ticketParam[9];
            String company = null;
            String startDrome = null;
            String arriveDrome = null;
            String week = null;
            try {
                company = URLDecoder.decode(companyDecode,"utf-8");     //中文转码
                startDrome = URLDecoder.decode(startDromeDecode,"utf-8");
                arriveDrome = URLDecoder.decode(arriveDromeDecode,"utf-8");
                week = URLDecoder.decode(weekDecode,"utf-8");
                ticket.setCompany(company);
                ticket.setAirCode(ariCode);
                ticket.setStartDrome(startDrome);
                ticket.setArriveDrome(arriveDrome);
                ticket.setStartTime(startTime);
                ticket.setArriveTime(arriveTime);
                ticket.setMode(mode);
                ticket.setAirStop(airStop);
                ticket.setWeek(week);
                ticket.setDate(date);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            ticket.setCompany("");
            ticket.setAirCode("");
            ticket.setStartDrome("");
            ticket.setArriveDrome("");
            ticket.setStartTime("");
            ticket.setArriveTime("");
            ticket.setMode("");
            ticket.setAirStop("");
            ticket.setWeek("");
            ticket.setDate("");
        }
        return ticket;
    }
}
