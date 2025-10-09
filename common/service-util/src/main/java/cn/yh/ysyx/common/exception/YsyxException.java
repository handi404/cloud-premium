package cn.yh.ysyx.common.exception;

import cn.yh.ysyx.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class YsyxException extends RuntimeException{

    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param message
     * @param code
     */
    public YsyxException(String message, Integer code) {
        super(message);
        this.code = code;
    }


    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public YsyxException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

}
