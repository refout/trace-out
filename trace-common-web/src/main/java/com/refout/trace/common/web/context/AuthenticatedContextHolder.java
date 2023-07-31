package com.refout.trace.common.web.context;

import com.refout.trace.common.system.domain.authenticated.Authenticated;
import org.springframework.util.Assert;

/**
 * 用户认证信息上下文
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/28 22:43
 */
public class AuthenticatedContextHolder {

    /**
     * {@link ThreadLocal}
     */
    private static final ThreadLocal<Authenticated> contextHolder = new ThreadLocal<>();


    /**
     * Clears the current context.
     */
    public static void clearContext() {
        contextHolder.remove();
    }

    /**
     * Obtains the current context.
     *
     * @return a context (never <code>null</code> - create a default implementation if
     * necessary)
     */
    public static Authenticated getContext() {
        return contextHolder.get();
    }

    /**
     * Sets the current context.
     *
     * @param context to the new argument (should never be <code>null</code>, although
     *                implementations must check if <code>null</code> has been passed and throw an
     *                <code>IllegalArgumentException</code> in such cases)
     */
    public static void setContext(Authenticated context) {
        Assert.notNull(context, "Only non-null Authenticated instances are permitted");
        contextHolder.set(context);
    }

}