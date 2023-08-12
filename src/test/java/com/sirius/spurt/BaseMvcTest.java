package com.sirius.spurt;

import static org.mockito.ArgumentMatchers.anyString;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirius.spurt.store.provider.auth.AuthProvider;
import com.sirius.spurt.store.provider.auth.vo.AuthVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class, SpringExtension.class})
public class BaseMvcTest {
    @Autowired private WebApplicationContext context;
    @Autowired protected ObjectMapper objectMapper;

    @MockBean private AuthProvider authProvider;
    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {
        AuthVo authVo = AuthVo.builder().userId("test").build();

        Mockito.when(authProvider.getUserId(anyString())).thenReturn(authVo);
        var mockMvcRequestBuilders =
                MockMvcRequestBuilders.get("http://example.com")
                        .header("Authorization", "Bearer <<전달받은토큰값>>");
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(context)
                        .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                        .defaultRequest(mockMvcRequestBuilders)
                        .alwaysDo(
                                MockMvcRestDocumentation.document(
                                        "{class-name}/{method-name}",
                                        Preprocessors.preprocessRequest(),
                                        Preprocessors.preprocessResponse(
                                                ResponseModifyingPreprocessors.replaceBinaryContent(),
                                                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                                Preprocessors.prettyPrint())))
                        .apply(
                                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                                        .uris()
                                        .withScheme("http")
                                        .withHost("localhost")
                                        .withPort(8080)
                                        .and()
                                        .snippets()
                                        .withDefaults(
                                                CliDocumentation.curlRequest(),
                                                HttpDocumentation.httpRequest(),
                                                HttpDocumentation.httpResponse(),
                                                AutoDocumentation.requestFields(),
                                                new CustomResponseFieldSnippet(),
                                                //
                                                // AutoDocumentation.responseFields(),
                                                AutoDocumentation.pathParameters(),
                                                AutoDocumentation.requestParameters(),
                                                AutoDocumentation.description(),
                                                AutoDocumentation.methodAndPath(),
                                                AutoDocumentation.section()))
                        .build();
    }
}
