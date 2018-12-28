package com.github.baifenghe.tools.handle;

import com.github.baifenghe.tools.common.constant.enums.BusinessEnum;
import com.github.baifenghe.tools.common.exception.BusinessException;
import com.github.baifenghe.tools.common.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author bfh
 * @since 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String defaultExceptionHandler(Exception e) {

        log.error("GlobalExceptionHandler#Exception: ", e);
        if (e instanceof BindException) {
            BindException validException = (BindException) e;
            return R.failed(BusinessEnum.ERROR.getCode(), "参数校验异常："
                    + validException.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), null);
        }
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return R.failed(businessException.getCode(), businessException.getMessage(), null);
        }
        return R.failed(BusinessEnum.ERROR.getCode(), "系统异常：" + e, null);

    }

}
