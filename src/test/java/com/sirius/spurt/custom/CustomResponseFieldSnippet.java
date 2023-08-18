package com.sirius.spurt.custom;

import static capital.scalable.restdocs.util.TypeUtil.firstGenericType;

import capital.scalable.restdocs.payload.JacksonResponseFieldSnippet;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.springframework.web.method.HandlerMethod;

public class CustomResponseFieldSnippet extends JacksonResponseFieldSnippet {
    @Override
    protected Type[] getType(final HandlerMethod method) {
        var originalResponse = super.getType(method);
        Class<?> methodReturnType = method.getReturnType().getParameterType();
        if ("com.sirius.spurt.service.controller.RestResponse"
                .equals(methodReturnType.getCanonicalName())) {
            Type type = firstGenericType(method.getReturnType());
            Type returnType;
            if (type instanceof ParameterizedType) {
                returnType = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                returnType = type;
            }
            return returnType == null ? null : new Type[] {returnType};
        }
        return originalResponse;
    }
}
