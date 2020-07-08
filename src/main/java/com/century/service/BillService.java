package com.century.service;

import com.century.dao.BillDAO;
import com.century.vo.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BillService {
    @Resource
    private BillDAO billDAO;

    public Bill queryUserById(int id){
        return billDAO.queryUserById(id);
    }
    public int addBill(Bill bill){
        return billDAO.addBill(bill);
    }

    public int queryTicketIdByUserId(int userId){
        return billDAO.queryTicketIdByUserId(userId);
    }

    public List<Bill> queryBillByUserId(int userId){
        return billDAO.queryBillByUserId(userId);
    }
}
