package org.wspring.chapter3.controller;

import org.wheel.framework.annotation.Action;
import org.wheel.framework.annotation.Controller;
import org.wheel.framework.bean.Param;
import org.wheel.framework.bean.View;

/**
 * DESCRIPTION : 主页
 *
 * @author ducf
 * @create 2019-03-06 下午 3:15
 */
@Controller
public class HomeController {

    @Action("get:/")
    public View welcome(Param param) {
        return goHome();
    }

    private View goHome() {
        return new View("home.jsp");
    }

}
