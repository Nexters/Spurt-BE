package com.sirius.spurt.custom;

import static capital.scalable.restdocs.OperationAttributeHelper.getConstraintReader;
import static capital.scalable.restdocs.OperationAttributeHelper.getHandlerMethod;
import static capital.scalable.restdocs.OperationAttributeHelper.getJavadocReader;
import static capital.scalable.restdocs.OperationAttributeHelper.getObjectMapper;
import static capital.scalable.restdocs.OperationAttributeHelper.getTranslationResolver;
import static capital.scalable.restdocs.OperationAttributeHelper.getTypeMapping;
import static capital.scalable.restdocs.util.FieldDescriptorUtil.assertAllDocumented;

import capital.scalable.restdocs.constraints.ConstraintReader;
import capital.scalable.restdocs.i18n.SnippetTranslationResolver;
import capital.scalable.restdocs.jackson.FieldDescriptors;
import capital.scalable.restdocs.jackson.FieldDocumentationGenerator;
import capital.scalable.restdocs.jackson.TypeMapping;
import capital.scalable.restdocs.javadoc.JavadocReader;
import capital.scalable.restdocs.payload.JacksonFieldProcessingException;
import capital.scalable.restdocs.section.SectionSupport;
import capital.scalable.restdocs.snippet.StandardTableSnippet;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.restdocs.operation.Operation;
import org.springframework.web.method.HandlerMethod;

public abstract class CustomAbstractJacksonFieldSnippet extends StandardTableSnippet
        implements SectionSupport {

    private static Class<?> SCALA_TRAVERSABLE;

    static {
        try {
            SCALA_TRAVERSABLE = Class.forName("scala.collection.Traversable");
        } catch (ClassNotFoundException ignored) {
            // It's fine to not be available outside of Scala projects.
        }
    }

    protected CustomAbstractJacksonFieldSnippet(String snippetName, Map<String, Object> attributes) {
        super(snippetName, attributes);
    }

    protected FieldDescriptors createFieldDescriptors(
            Operation operation, HandlerMethod handlerMethod) {
        ObjectMapper objectMapper = getObjectMapper(operation);

        JavadocReader javadocReader = getJavadocReader(operation);
        ConstraintReader constraintReader = getConstraintReader(operation);
        SnippetTranslationResolver translationResolver = getTranslationResolver(operation);
        TypeMapping typeMapping = getTypeMapping(operation);
        JsonProperty.Access skipAcessor = getSkipAcessor();

        Type[] types = getType(handlerMethod);
        if (types == null || types.length == 0) {
            return new FieldDescriptors();
        }

        try {
            FieldDocumentationGenerator generator =
                    new FieldDocumentationGenerator(
                            objectMapper.writer(),
                            objectMapper.getDeserializationConfig(),
                            javadocReader,
                            constraintReader,
                            typeMapping,
                            translationResolver,
                            skipAcessor);
            FieldDescriptors fieldDescriptors =
                    generator.generateDocumentation(types, objectMapper.getTypeFactory());

            FieldDescriptors customFieldDescriptors = new FieldDescriptors();
            fieldDescriptors.values().stream()
                    .filter(fieldDescriptor -> !"userId".equals(fieldDescriptor.getPath()))
                    .forEach(
                            fieldDescriptor ->
                                    customFieldDescriptors.putIfAbsent(fieldDescriptor.getPath(), fieldDescriptor));

            if (shouldFailOnUndocumentedFields()) {
                assertAllDocumented(
                        customFieldDescriptors.values(),
                        translationResolver.translate(getHeaderKey(operation)).toLowerCase());
            }
            return customFieldDescriptors;
        } catch (JsonMappingException e) {
            throw new JacksonFieldProcessingException("Error while parsing fields", e);
        }
    }

    protected abstract Type[] getType(HandlerMethod method);

    protected abstract boolean shouldFailOnUndocumentedFields();

    protected JsonProperty.Access getSkipAcessor() {
        return null;
    }

    protected boolean isCollection(Class<?> type) {
        return Collection.class.isAssignableFrom(type)
                || Stream.class.isAssignableFrom(type)
                || (SCALA_TRAVERSABLE != null && SCALA_TRAVERSABLE.isAssignableFrom(type));
    }

    @Override
    public String getFileName() {
        return getSnippetName();
    }

    @Override
    public boolean hasContent(Operation operation) {
        Type[] type = getType(getHandlerMethod(operation));
        return type != null && type.length > 0;
    }
}
