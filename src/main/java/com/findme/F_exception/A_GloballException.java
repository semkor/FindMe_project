package com.findme.F_exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class A_GloballException {
    private final static Logger log = LoggerFactory.getLogger(A_GloballException.class);

    //400 - не правильный / не корректный запрос
    @ExceptionHandler(value = BadRequestException.class)
    public ModelAndView badRequestHandler(BadRequestException badRequest) {
    log.error("BadRequestException:  ",badRequest.getStackTrace());
            ModelAndView modelAndView = new ModelAndView("5.1_page400");
            modelAndView.addObject("error", badRequest.getMessage());
        return modelAndView;
    }

    //401 - не авторизован
    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView unauthorizedHandler(UnauthorizedException unauthorized) {
    log.error("UnauthorizedException:  ", unauthorized.getStackTrace());
            ModelAndView modelAndView = new ModelAndView("5.2_page401");
                modelAndView.addObject("error", unauthorized.getMessage());
        return modelAndView;
    }

    //404 - не найдено
    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView notFoundHandler(NotFoundException notFound) {
    log.error("NotFoundException:  ", notFound.getStackTrace());
            ModelAndView modelAndView = new ModelAndView("5.3_page404");
                modelAndView.addObject("error", notFound.getMessage());
        return modelAndView;
    }

    //500 - внутренняя ошибка сервера
    @ExceptionHandler(value = InternalServerException.class)
    public ModelAndView internalServerException(InternalServerException internalServer) {
    log.error("InternalServerException:  ", internalServer.getStackTrace());
            ModelAndView modelAndView = new ModelAndView("5.4_page500");
            modelAndView.addObject("error", internalServer.getMessage());
        return modelAndView;
    }

    // 412 - условие ложно
    @ExceptionHandler(LimitationException.class)
    public ResponseEntity<String> limitationExceptionHandler(LimitationException limit) {
            log.error("LimitationException:  ", limit.getStackTrace());
        return new ResponseEntity<String>(limit.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    // - остальные все ошибки
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception e) {
    log.error("Exception:  ", e.getStackTrace());
            ModelAndView modelAndView = new ModelAndView("5.5_exception");
            modelAndView.addObject("error", e.getMessage());
        return modelAndView;
    }
}
