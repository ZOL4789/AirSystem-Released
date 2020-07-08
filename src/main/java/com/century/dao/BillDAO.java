package com.century.dao;

import com.century.vo.Bill;

import java.util.List;

public interface BillDAO {
    Bill queryUserById(int id);
    int addBill(Bill bill);
    int queryTicketIdByUserId(int userId);
    List<Bill> queryBillByUserId(int userId);
}
