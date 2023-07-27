package com.sirius.spurt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.service.business.user.CheckUserExistsBusiness;
import com.sirius.spurt.service.controller.user.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {UserController.class})
public class UserControllerTest extends BaseMvcTest {
    @MockBean private CheckUserExistsBusiness checkUserExistsBusiness;

    @Test
    void 유저_존재_확인() throws Exception {
        CheckUserExistsBusiness.Dto dto =
                CheckUserExistsBusiness.Dto.builder().userId("test-admin").build();
        CheckUserExistsBusiness.Result result =
                CheckUserExistsBusiness.Result.builder().isUserExists(false).build();
        when(checkUserExistsBusiness.execute(dto)).thenReturn(result);
        this.mockMvc
                .perform(get("/v1/user/exist").requestAttr("userId", "test-admin"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
