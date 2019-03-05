package org.wspring.chapter2.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wspring.chapter2.helper.DatabaseHelper;
import org.wspring.chapter2.model.Customer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION : 测试用例
 *
 * @author ducf
 * @create 2019-03-05 下午 12:21
 */
public class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new CustomerService();
    }

    /**
     * 初始化数据库
     *
     * @throws IOException
     */
    @Before
    public void init() throws IOException {
        DatabaseHelper.exeuteSqlFile("sql/customer_init.sql");
    }


    @Test
    public void getCustomerList() {
        List<Customer> customerList = customerService.getCustomerList();
        Assert.assertEquals(2, customerList.size());
    }

    @Test
    public void getCustomer() {
        long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    @Test
    public void createCustomer() {
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("name", "customer100");
        fieldMap.put("contact", "John");
        fieldMap.put("telephone", "13512345678");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomer() {
        long id = 1;
        Map<String, Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("contact", "Eric");
        boolean result = customerService.updateCustomer(id, fieldMap);
        Assert.assertTrue(result);
    }

    @Test
    public void deleteCustomer() {
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);
    }
}