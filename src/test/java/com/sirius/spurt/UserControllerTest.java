package com.sirius.spurt;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.service.business.user.CheckUserExistsBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPostedBusiness;
import com.sirius.spurt.service.controller.user.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {UserController.class})
public class UserControllerTest extends BaseMvcTest {
    @MockBean private CheckUserExistsBusiness checkUserExistsBusiness;
    @MockBean private CheckUserHasPinedBusiness checkUserHasPinedBusiness;
    @MockBean private CheckUserHasPostedBusiness checkUserHasPostedBusiness;

    @Test
    void 유저_존재_확인() throws Exception {
        CheckUserExistsBusiness.Dto dto =
                CheckUserExistsBusiness.Dto.builder().userId("test-admin").build();
        CheckUserExistsBusiness.Result result =
                CheckUserExistsBusiness.Result.builder().isUserExists(false).build();
        when(checkUserExistsBusiness.execute(dto)).thenReturn(result);
        this.mockMvc.perform(get("/v1/user/exist")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void 유저_최초_핀고정_확인() throws Exception {
        CheckUserHasPinedBusiness.Dto dto =
                CheckUserHasPinedBusiness.Dto.builder().userId("admin").build();
        CheckUserHasPinedBusiness.Result result =
                CheckUserHasPinedBusiness.Result.builder().hasPined(false).build();
        when(checkUserHasPinedBusiness.execute(dto)).thenReturn(result);
        this.mockMvc.perform(get("/v1/user/pin")).andExpect(status().isOk()).andDo(print());
    }

    @Test
    void 유저_최초_질문_답변_작성_확인() throws Exception {
        CheckUserHasPostedBusiness.Dto dto =
                CheckUserHasPostedBusiness.Dto.builder().userId("admin").build();
        CheckUserHasPostedBusiness.Result result =
                CheckUserHasPostedBusiness.Result.builder().hasPosted(false).build();
        when(checkUserHasPostedBusiness.execute(dto)).thenReturn(result);
        this.mockMvc.perform(get("/v1/user/posting")).andExpect(status().isOk()).andDo(print());
    }
}
