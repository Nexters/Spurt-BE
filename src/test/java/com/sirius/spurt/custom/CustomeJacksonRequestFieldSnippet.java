package com.sirius.spurt.custom;

import static capital.scalable.restdocs.SnippetRegistry.AUTO_REQUEST_FIELDS;
import static capital.scalable.restdocs.util.TypeUtil.firstGenericType;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import org.springframework.core.MethodParameter;
import org.springframework.restdocs.operation.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.method.HandlerMethod;

public class CustomeJacksonRequestFieldSnippet extends CustomAbstractJacksonFieldSnippet {
    private final Type requestBodyType;
    private final boolean failOnUndocumentedFields;

    public CustomeJacksonRequestFieldSnippet() {
        this(null, false);
    }

    public CustomeJacksonRequestFieldSnippet(Type requestBodyType, boolean failOnUndocumentedFields) {
        super(AUTO_REQUEST_FIELDS, null);
        this.requestBodyType = requestBodyType;
        this.failOnUndocumentedFields = failOnUndocumentedFields;
    }

    public CustomeJacksonRequestFieldSnippet requestBodyAsType(Type requestBodyType) {
        return new CustomeJacksonRequestFieldSnippet(requestBodyType, failOnUndocumentedFields);
    }

    public CustomeJacksonRequestFieldSnippet failOnUndocumentedFields(
            boolean failOnUndocumentedFields) {
        return new CustomeJacksonRequestFieldSnippet(requestBodyType, failOnUndocumentedFields);
    }

    @Override
    protected Type[] getType(HandlerMethod method) {
        if (requestBodyType != null) {
            return new Type[] {requestBodyType};
        }
        for (MethodParameter param : method.getMethodParameters()) {
            if (isRequestBody(param)) {
                return new Type[] {getType(param)};
            }
        }
        return null;
    }

    private boolean isRequestBody(MethodParameter param) {
        return param.getParameterAnnotation(RequestBody.class) != null;
    }

    private Type getType(final MethodParameter param) {
        if (isCollection(param.getParameterType())) {
            return (GenericArrayType) () -> firstGenericType(param);
        } else {
            return param.getParameterType();
        }
    }

    @Override
    public String getHeaderKey(Operation operation) {
        return "request-fields";
    }

    @Override
    protected boolean shouldFailOnUndocumentedFields() {
        return failOnUndocumentedFields;
    }

    @Override
    protected String[] getTranslationKeys() {
        return new String[] {"th-path", "th-type", "th-optional", "th-description", "no-request-body"};
    }

    @Override
    protected JsonProperty.Access getSkipAcessor() {
        return JsonProperty.Access.READ_ONLY;
    }

    @Override
    public boolean isMergeable() {
        return true;
    }
}
