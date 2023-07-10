package com.sirius.spurt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.service.controller.SampleController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {SampleControllerTest.class})
public class SampleControllerTest extends BaceMvcTest {
    @MockBean private SampleController smapleController;

    @Test
    void 기본테스트() throws Exception {
        this.mockMvc
                .perform(get("/v1/sample/1234").param("sample", "1234"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
