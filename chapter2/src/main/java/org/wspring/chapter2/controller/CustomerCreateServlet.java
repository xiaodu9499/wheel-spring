package org.wspring.chapter2.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * DESCRIPTION : 创建客户
 *
 * @author ducf
 * @create 2019-03-05 上午 11:44
 */
@WebServlet("/customer_create")
public class CustomerCreateServlet extends HttpServlet {

    // 进入创建页面
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    // 保存客户
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
