package com.sirius.spurt;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.service.business.user.CheckUserExistsBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPinedBusiness;
import com.sirius.spurt.service.business.user.CheckUserHasPostedBusiness;
import com.sirius.spurt.service.business.user.DeleteUserBusiness;
import com.sirius.spurt.service.business.user.UserInfoBusiness;
import com.sirius.spurt.service.controller.user.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {UserController.class})
public class UserControllerTest extends BaseMvcTest {
    @MockBean private CheckUserExistsBusiness checkUserExistsBusiness;
    @MockBean private CheckUserHasPinedBusiness checkUserHasPinedBusiness;
    @MockBean private CheckUserHasPostedBusiness checkUserHasPostedBusiness;
    @MockBean private UserInfoBusiness userInfoBusiness;
    @MockBean private DeleteUserBusiness deleteUserBusiness;

    @Test
    void 유저_정보_조회() throws Exception {
        UserInfoBusiness.Result result =
                UserInfoBusiness.Result.builder().userId("wieribwer").jobGroup(JobGroup.DEVELOPER).build();
        when(userInfoBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(get("/v1/user/info").principal(this.mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 유저_존재_확인() throws Exception {
        CheckUserExistsBusiness.Result result =
                CheckUserExistsBusiness.Result.builder().isUserExists(false).build();
        when(checkUserExistsBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(get("/v1/user/exist").principal(this.mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 유저_최초_핀고정_확인() throws Exception {
        CheckUserHasPinedBusiness.Result result =
                CheckUserHasPinedBusiness.Result.builder().hasPined(false).build();
        when(checkUserHasPinedBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(get("/v1/user/pin").principal(this.mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 유저_최초_질문_답변_작성_확인() throws Exception {
        CheckUserHasPostedBusiness.Result result =
                CheckUserHasPostedBusiness.Result.builder().hasPosted(false).build();
        when(checkUserHasPostedBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(get("/v1/user/posting").principal(this.mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void 유저_삭제_확인() throws Exception {
        DeleteUserBusiness.Result result = new DeleteUserBusiness.Result();
        when(deleteUserBusiness.execute(any())).thenReturn(result);
        this.mockMvc
                .perform(delete("/v1/user").principal(this.mockPrincipal))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
