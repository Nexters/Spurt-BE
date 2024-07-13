package com.sirius.spurt;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sirius.spurt.common.auth.PrincipalDetails;
import com.sirius.spurt.common.jwt.JwtUtils;
import com.sirius.spurt.common.jwt.MockSpringSecurityFilter;
import com.sirius.spurt.custom.CustomRequestFieldSnippet;
import com.sirius.spurt.custom.CustomResponseFieldSnippet;
import com.sirius.spurt.test.UserTest;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, MockitoExtension.class, SpringExtension.class})
public class BaseMvcTest implements UserTest {
    @Autowired private WebApplicationContext context;
    @Autowired protected ObjectMapper objectMapper;

    @MockBean private JwtUtils jwtUtils;

    protected MockMvc mockMvc;
    protected Principal mockPrincipal;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider restDocumentation) throws Exception {
        setUpMockUser();
        var mockMvcRequestBuilders =
                MockMvcRequestBuilders.get("https://api.spurtapp.com")
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
                                                // ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                                Preprocessors.prettyPrint())))
                        .apply(springSecurity(new MockSpringSecurityFilter()))
                        .apply(
                                MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                                        .uris()
                                        .withScheme("https")
                                        .withHost("api.spurtapp.com")
                                        .withPort(443)
                                        .and()
                                        .snippets()
                                        .withDefaults(
                                                CliDocumentation.curlRequest(),
                                                HttpDocumentation.httpRequest(),
                                                HttpDocumentation.httpResponse(),
                                                new CustomRequestFieldSnippet(),
                                                // AutoDocumentation.requestFields(),
                                                new CustomResponseFieldSnippet(),
                                                // AutoDocumentation.responseFields(),
                                                AutoDocumentation.pathParameters(),
                                                AutoDocumentation.requestParameters(),
                                                AutoDocumentation.description(),
                                                AutoDocumentation.methodAndPath(),
                                                AutoDocumentation.section()))
                        .build();
    }

    private void setUpMockUser() {
        PrincipalDetails principalDetails = PrincipalDetails.builder().userEntity(TEST_USER).build();
        this.mockPrincipal =
                new UsernamePasswordAuthenticationToken(
                        principalDetails, null, principalDetails.getAuthorities());
    }
}
