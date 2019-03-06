package org.wspring.chapter3.controller;

import org.wheel.framework.annotation.Action;
import org.wheel.framework.annotation.Controller;
import org.wheel.framework.annotation.Inject;
import org.wheel.framework.bean.Param;
import org.wheel.framework.bean.View;
import org.wspring.chapter3.model.Customer;
import org.wspring.chapter3.service.CustomerService;

import java.util.List;

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

    @Action("get:/customer")
    public View cuntomerList(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addModel("customerList", customerList);
    }

}
