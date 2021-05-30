package quick.pager.pcloud.response;

import java.util.List;
import lombok.Data;
import quick.pager.pcloud.constants.ResponseStatus;

import java.io.Serializable;

/**
 * 数据响应类
 *
 * @param <T>
 * @author siguiyang
 */
@Data
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 473372815866107289L;
    /**
     * 数据响应吗
     */
    private int code;
    /**
     * 响应消息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;

    private long timestamp = System.currentTimeMillis();

    /**
     * 分页总数
     */
    private long total;

    private ResponseResult() {
    }

    private ResponseResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(T data) {
        this.data = data;
    }

    public static <T> ResponseResult<List<T>> toSuccess(List<T> data, long total) {
        ResponseResult<List<T>> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseStatus.SUCCESS);
        responseResult.setMsg(ResponseStatus.SUCCESS_MSG);
        responseResult.setTotal(total);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> toSuccess(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseStatus.SUCCESS);
        responseResult.setMsg(ResponseStatus.SUCCESS_MSG);
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> toSuccess() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(ResponseStatus.SUCCESS);
        responseResult.setMsg(ResponseStatus.SUCCESS_MSG);
        return responseResult;
    }

    public static <T> ResponseResult<T> toError(int code, String msg) {
        return new ResponseResult<>(code, msg);
    }

    public static <T> ResponseResult<T> toError(String msg) {
        return toError(ResponseStatus.EXCEPTION_CODE, msg);
    }

    /**
     * 验证code是否成功
     */
    public boolean check() {
        return ResponseStatus.SUCCESS == this.getCode();
    }
}
