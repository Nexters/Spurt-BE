package com.sirius.spurt.service.controller.question;

import com.sirius.spurt.common.meta.Category;
import com.sirius.spurt.common.meta.JobGroup;
import com.sirius.spurt.common.resolver.user.LoginUser;
import com.sirius.spurt.common.resolver.user.NonLoginUser;
import com.sirius.spurt.service.business.question.DeleteQuestionBusiness;
import com.sirius.spurt.service.business.question.GetQuestionBusiness;
import com.sirius.spurt.service.business.question.PutPinQuestionBusiness;
import com.sirius.spurt.service.business.question.PutQuestionBusiness;
import com.sirius.spurt.service.business.question.RandomQuestionBusiness;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final SaveQuestionBusiness saveQuestionBusiness;
    private final DeleteQuestionBusiness deleteQuestionBusiness;
    private final PutQuestionBusiness putQuestionBusiness;
    private final GetQuestionBusiness getQuestionBusiness;
    private final RandomQuestionBusiness randomQuestionBusiness;
    private final RetrieveQuestionBusiness retrieveQuestionBusiness;
    private final PutPinQuestionBusiness putPinQuestionBusiness;

    /**
     * @param loginUser
     * @param dto
     * @return
     * @title pin 고정/비고정
     */
    @PutMapping("/question/pin")
    public RestResponse<PutPinQuestionBusiness.Result> questionPin(
            LoginUser loginUser, @RequestBody PutPinQuestionBusiness.Dto dto) {
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(putPinQuestionBusiness.execute(dto));
    }

    /**
     * 로그인 하면 같은직군으로 조회 <br>
     * 비로그인시 랜덤 조회
     *
     * @param count 랜덤 질문 갯수
     * @return
     * @title 같은 직군 질문 랜덤 조회
     */
    @GetMapping("/question/random")
    public RestResponse<RandomQuestionBusiness.Result> questionRandom(
            NonLoginUser nonLoginUser,
            @RequestParam(name = "category") Category category,
            @RequestParam(name = "offset", defaultValue = "4") String count) {

        RandomQuestionBusiness.Dto dto =
                RandomQuestionBusiness.Dto.builder()
                        .userId(nonLoginUser.getUserId())
                        .count(Integer.valueOf(count))
                        .category(category)
                        .build();
        return RestResponse.success(randomQuestionBusiness.execute(dto));
    }

    /**
     * @param dto
     * @return
     * @title 질문 삭제
     */
    @DeleteMapping("/question")
    public RestResponse<DeleteQuestionBusiness.Result> questionDelete(
            LoginUser loginUser, @RequestBody DeleteQuestionBusiness.Dto dto) {
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(deleteQuestionBusiness.execute(dto));
    }

    /**
     * @param dto
     * @return
     * @title 질문 수정
     */
    @PutMapping("/question")
    public RestResponse<PutQuestionBusiness.Result> questionPut(
            LoginUser loginUser, @RequestBody PutQuestionBusiness.Dto dto) {
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(putQuestionBusiness.execute(dto));
    }

    /**
     * @param questionId 질문 ID
     * @return
     * @title 질문 단건 조회
     */
    @GetMapping("/question/{questionId}")
    public RestResponse<GetQuestionBusiness.Result> questionGet(
            LoginUser loginUser, @PathVariable("questionId") String questionId) {
        GetQuestionBusiness.Dto dto =
                GetQuestionBusiness.Dto.builder().questionId(Long.parseLong(questionId)).build();
        return RestResponse.success(getQuestionBusiness.execute(dto));
    }
    /**
     * @param dto
     * @return
     * @title 질문 저장
     */
    @PostMapping("/question")
    public RestResponse<SaveQuestionBusiness.Result> questionSave(
            LoginUser loginUser, @RequestBody SaveQuestionBusiness.Dto dto) {
        dto.setUserId(loginUser.getUserId());
        return RestResponse.success(saveQuestionBusiness.execute(dto));
    }

    /**
     * @param subject
     * @param jobGroup 직군
     * @param category 카테고리
     * @param pinIndicator 10분노트 여부 true/fasle
     * @param myQuestionIndicator 내 질문만 조회 true/fasle
     * @param offset 페이지 (시작 0)
     * @param size size
     * @return
     * @title 질문 조회
     */
    @GetMapping("/question")
    public RestResponse<RetrieveQuestionBusiness.Result> questionRetrieve(
            LoginUser loginUser,
            @RequestParam(name = "subject", required = false) String subject,
            @RequestParam(name = "jobGroup", required = false) JobGroup jobGroup,
            @RequestParam(name = "category", required = false) Category category,
            @RequestParam(name = "pinIndicator", required = false) Boolean pinIndicator,
            @RequestParam(name = "myQuestionIndicator", defaultValue = "true") String myQuestionIndicator,
            @RequestParam(name = "offset", defaultValue = "1") String offset,
            @RequestParam(name = "size", defaultValue = "10") String size) {
        RetrieveQuestionBusiness.Dto dto =
                RetrieveQuestionBusiness.Dto.builder()
                        .userId(loginUser.getUserId())
                        .subject(subject)
                        .jobGroup(jobGroup)
                        .category(category)
                        .pinIndicator(pinIndicator)
                        .myQuestionIndicator(Boolean.valueOf(myQuestionIndicator))
                        .size(size)
                        .offset(Integer.parseInt(offset) - 1)
                        .build();
        return RestResponse.success(retrieveQuestionBusiness.execute(dto));
    }
}
