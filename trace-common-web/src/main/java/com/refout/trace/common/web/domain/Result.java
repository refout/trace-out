package com.refout.trace.common.web.domain;

import com.refout.trace.common.util.JsonUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;


/**
 * HTTP响应结果
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/15 19:05
 */
public record Result(int code, String msg, Object data) {

    /**
     * 根据状态码和消息创建一个新的 Result 对象
     *
     * @param code 状态码
     * @param msg  消息
     */
    public Result(int code, String msg) {
        this(code, msg, null);
    }

    /**
     * 返回 JSON 格式化字符串
     *
     * @return JSON 格式化字符串
     */
    public String toJson() {
        return JsonUtil.toJson(this);
    }

    /**
     * 创建一个失败的 Result 对象
     *
     * @param code 状态码
     * @param msg  消息
     * @return 失败的 Result 对象
     */
    @Contract("_, _ -> new")
    public static @NotNull Result fault(int code, String msg) {
        return new Result(code, msg);
    }

    /**
     * 创建一个默认状态码的失败的 Result 对象
     *
     * @param code 状态码
     * @return 失败的 Result 对象
     */
    @Contract("_ -> new")
    public static @NotNull Result fault(int code) {
        return fault(code, null);
    }

    /**
     * 创建一个成功的 Result 对象
     *
     * @param msg  消息
     * @param data 结果数据
     * @return 成功的 Result 对象
     */
    @Contract("_, _ -> new")
    public static @NotNull Result success(String msg, Object data) {
        return new Result(HttpStatus.OK.value(), msg, data);
    }

    /**
     * 创建一个默认消息的成功的 Result 对象
     *
     * @param data 结果数据
     * @return 成功的 Result 对象
     */
    @Contract("_ -> new")
    public static @NotNull Result success(Object data) {
        return success(null, data);
    }

    @Contract("-> new")
    public static @NotNull Result success() {
        return success(null, null);
    }

}