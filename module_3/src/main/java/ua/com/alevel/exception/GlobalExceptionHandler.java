package ua.com.alevel.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public ModelAndView defaultErrorHandler(RuntimeException exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("showMessage", true);
        mav.addObject("errorMessage", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
