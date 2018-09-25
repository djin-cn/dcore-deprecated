package me.djin.study.frame.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import me.djin.study.exception.ApplicationException;
import me.djin.study.frame.model.OutputModel;


/**
 * 全局异常处理类
 *
 * @author djin
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 400:验证异常
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(MethodArgumentNotValidException.class)
    OutputModel handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        LOGGER.info(e.getMessage(), e);
        StringBuilder stringBuilder = new StringBuilder();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            stringBuilder.append(fieldError.getField()).append(":").
                    append(fieldError.getDefaultMessage()).append(",");
        }
        return new OutputModel("2", stringBuilder.substring(0, stringBuilder.lastIndexOf(",")).toString());
    }

    /**
     * 400:一般在@ResponseBody接收JSON参数无法解析时发生
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(HttpMessageNotReadableException.class)
    OutputModel handleNotReadableException(HttpMessageNotReadableException e) {
        LOGGER.info(e.getMessage(), e);
        return new OutputModel("2", e.getCause() == null ? e.getMessage() : e.getCause().getMessage());
    }

    /**
     * 400:一般在@RequestParam未获取到参数时发生
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(MissingServletRequestParameterException.class)
    OutputModel handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        LOGGER.info(e.getMessage(), e);
        return new OutputModel("2", e.getMessage());
    }
    
    /**
     * 系统异常处理
     * @param e
     * @return
     */
    @SuppressWarnings("rawtypes")
	@ExceptionHandler(Exception.class)
    OutputModel handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        if(e instanceof ApplicationException) {
        	return new OutputModel(((ApplicationException)e).getCode(), e.getMessage());
        }else {
        	return new OutputModel("0", e.getMessage());
        }
    }
}
