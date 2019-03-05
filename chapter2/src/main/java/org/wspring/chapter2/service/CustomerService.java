package org.wspring.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wspring.chapter2.helper.DatabaseHelper;
import org.wspring.chapter2.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION :
 *
 * @author ducf
 * @create 2019-03-05 上午 11:48
 */
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);


    /**
     * 获取客户列表
     *
     * @return
     */
    public List<Customer> getCustomerList() {
        String sql = "select *  from customer";
        List<Customer> customers = DatabaseHelper.queryEntityList(Customer.class, sql);
        return customers;
    }

    /**
     * 按条件,获取客户列表
     *
     * @param keyword
     * @return
     */
    public List<Customer> getCustomerList(String keyword) {
        return null;
    }

    /**
     * 获取客户
     *
     * @param id
     * @return
     */
    public Customer getCustomer(long id) {
        return null;
    }

    /**
     * 创建客户
     *
     * @param fieldMap
     * @return
     */
    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(Customer.class, fieldMap);
    }

    /**
     * 更新客户
     *
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    /**
     * 删除客户
     *
     * @param id
     * @return
     */
    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}
