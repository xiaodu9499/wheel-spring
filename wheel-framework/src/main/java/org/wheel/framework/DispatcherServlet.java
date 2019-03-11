package org.wheel.framework;

import org.wheel.framework.bean.Data;
import org.wheel.framework.bean.Handler;
import org.wheel.framework.bean.Param;
import org.wheel.framework.bean.View;
import org.wheel.framework.helper.*;
import org.wheel.framework.util.JsonUtil;
import org.wheel.framework.util.ReflectionUtil;
import org.wheel.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * DESCRIPTION : 请求转发器
 *
 * @author ducf
 * @create 2019-03-05 下午 8:16
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        // 初始化相关Helper类
        HelperLoader.init();
        // 获取ServletContext 对象(用于注册 Servlet)
        ServletContext servletContext = servletConfig.getServletContext();
        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册处理静态资源的默认Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
        Uploadhelper.init(servletContext);
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ServletHelper.init(req, res);
        try {
            // 获取请求方法与请求路径
            String requestMethod = req.getMethod().toLowerCase();
            String requestPath = req.getPathInfo();

            if (requestPath.equals("/favicon.ico")) {
                return;
            }

            // 获取Action处理器
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                // 获取Controller类机器Bean实例
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);
                // 创建请求参数对象
                Param param;
                if (Uploadhelper.isMultipart(req)) {
                    param = Uploadhelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }
                // 处理返回值
                Object result;
                Method actionMethod = handler.getActionMethod();
                if (param.isEmpty()) {
                    // 无参函数
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    // 有参函数
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                // 处理返回值
                if (result instanceof View) {
                    //返回视图对象
                    handleViewResult((View) result, req, res);

                } else if (result instanceof Data) {
                    // 返回json数据
                    handleDataResult((Data) result, res);
                }
            }
        } finally {
            ServletHelper.destory();
        }
    }

    /*处理返回json的请求*/
    private void handleDataResult(Data result, HttpServletResponse res) throws IOException {
        Data data = (Data) result;
        Object model = data.getModel();
        if (model != null) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            PrintWriter writer = res.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }

    /*处理返回视图的请求*/
    private void handleViewResult(View result, HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        View view = (View) result;
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                res.sendRedirect(req.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req, res);
            }
        }
    }
}









