package org.wheel.framework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wheel.framework.annotation.Transaction;
import org.wheel.framework.helper.DatabaseHelper;

import java.lang.reflect.Method;

/**
 * DESCRIPTION : 事物代理
 *
 * @author ducf
 * @create 2019-03-07 下午 6:34
 */
public class TransactionProxy implements Proxy {

    private static final Logger log = LoggerFactory.getLogger(TransactionProxy.class);

    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result;

        Boolean flag = FLAG_HOLDER.get();
        Method method = proxyChain.getTargetMethod();

        if (!flag && method.isAnnotationPresent(Transaction.class)) {
            FLAG_HOLDER.set(true);
            try {
                DatabaseHelper.beginTransaction();
                log.debug("开始事物...");

                result = proxyChain.doProxyChain();

                DatabaseHelper.commitTransaction();
                log.debug("提交事务...");
            } catch (Exception e) {
                DatabaseHelper.rollbackTransaction();
                log.debug("回滚事物...");
                throw e;
            } finally {
                FLAG_HOLDER.remove();
            }
        } else {
            result = proxyChain.doProxyChain();
        }

        return result;
    }
}
