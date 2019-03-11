package org.wspring.chapter3.controller;

import org.wheel.framework.annotation.Action;
import org.wheel.framework.annotation.Controller;
import org.wheel.framework.annotation.Inject;
import org.wheel.framework.bean.Data;
import org.wheel.framework.bean.FileParam;
import org.wheel.framework.bean.Param;
import org.wheel.framework.bean.View;
import org.wspring.chapter3.model.Customer;
import org.wspring.chapter3.service.CustomerService;

import java.util.List;
import java.util.Map;

/**
 * DESCRIPTION : 客户controller
 *
 * @author ducf
 * @create 2019-03-06 上午 9:40
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    /**
     * 跳转到列表页
     *
     * @return
     */
    @Action("get:/customer")
    public View cuntomerList() {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }

    /**
     * 跳转到新增页
     *
     * @return
     */
    @Action("get:/go_customer_add")
    public View goCustomerAdd() {
        return new View("customer_create.jsp");
    }


    @Action("post:/customer_add")
    public Data cuntomerAdd(Param param) {
        Map<String, Object> fieldMap = param.getFieldMap();
        FileParam fileParam = param.getFile("photo");
        boolean customer = customerService.createCustomer(fieldMap,fileParam);
        return new Data(customer);
    }

}
