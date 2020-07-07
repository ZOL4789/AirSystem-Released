package com.century.dao;

import com.century.vo.Bill;

public interface BillDAO {
    Bill queryUserById(int id);
    int addBill(Bill bill);
}
