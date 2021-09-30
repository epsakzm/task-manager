package com.taskagile.web.apis;

import com.taskagile.web.results.ApiResult;
import com.taskagile.web.results.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ApiResult> handle(RuntimeException ex) {
        String errorReferenceCode = UUID.randomUUID().toString();
        log.error("핸들링되지 않은 에러 발생 !", ex);
        log.error("errorCode = [{}]", errorReferenceCode);
        return Result.serverError("서버사이드 오류", errorReferenceCode);
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    protected ResponseEntity<ApiResult> handle(MaxUploadSizeExceededException ex) {
        return Result.failure("파일이 최대 크기를 초과하였습니다 !");
    }
}
