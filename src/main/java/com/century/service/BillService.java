package com.century.service;

import com.century.dao.BillDAO;
import com.century.vo.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
