package com.dontleaveme.forliveme.persistence.annotation;

import com.dontleaveme.forliveme.persistence.model.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    /**
     * Controller 메서드가 특정 파라미터를 지원하는지 판단
     * 1) @LoginUser 어노테이션이 붙어있는지
     * 2) 파라미터 클래스타입 SessionUser.class인지
     *   ==>  1) && 2) ? true : false
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        boolean isLoginUserAnnotation = methodParameter.getParameterAnnotation(LoginUser.class) != null;
        //SessionUser 클래스 타입의 파라미터에 @LoginUser 어노테이션이 사용되었는가?
        boolean isUserClass = SessionUser.class.equals(methodParameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        return httpSession.getAttribute("user"); // 세션에서 유저 객체 정보 가져옴
    }
}
