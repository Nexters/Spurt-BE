package com.sirius.spurt.service.controller.question;

import com.sirius.spurt.service.business.question.GetQuestionBusiness;
import com.sirius.spurt.service.business.question.RetrieveQuestionBusiness;
import com.sirius.spurt.service.business.question.SaveQuestionBusiness;
import com.sirius.spurt.service.controller.RestResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final GetQuestionBusiness getQuestionBusiness;
    private final RetrieveQuestionBusiness retrieveQuestionBusiness;

    /**
     * @param questionId 질문 ID
     * @return
     * @title 질문 단건 조회 api
     */
    @GetMapping("/question/{questionId}")
    public RestResponse<GetQuestionBusiness.Result> questionGet(
            @PathVariable("questionId") String questionId) {
        GetQuestionBusiness.Dto dto = GetQuestionBusiness.Dto.builder().questionId(questionId).build();
        return RestResponse.success(getQuestionBusiness.execute(dto));
    }
    /**
     * @param dto
     * @return
     * @title 질문 저장 api
     */
    @PostMapping("/question")
    public RestResponse<SaveQuestionBusiness.Result> questionSave(
            @RequestBody SaveQuestionBusiness.Dto dto) {
        return RestResponse.success(saveQuestionBusiness.execute(dto));
    }

    /**
     * @param subject
     * @param jobGroup 직군
     * @param category 카테고리
     * @param pinIndecator 10분노트 여부 true/fasle
     * @param myQuestionIndecator 내 질문만 조회 true/fasle
     * @return
     * @title 질문 검색 api
     */
    @GetMapping("/question")
    public RestResponse<RetrieveQuestionBusiness.Result> questionRetrieve(
            @RequestParam(name = "subject") String subject,
            @RequestParam(name = "jobGroup") String jobGroup,
            @RequestParam(name = "category") String category,
            @RequestParam(name = "pinIndecator", defaultValue = "false") String pinIndecator,
            @RequestParam(name = "myQuestionIndecator", defaultValue = "true")
                    String myQuestionIndecator) {

        RetrieveQuestionBusiness.Dto dto =
                RetrieveQuestionBusiness.Dto.builder()
                        .userId("testUser")
                        .subject(subject)
                        .jobGroup(jobGroup)
                        .category(category)
                        .pinIndecator(pinIndecator)
                        .myQuestionIndecator(myQuestionIndecator)
                        .build();
        return RestResponse.success(retrieveQuestionBusiness.execute(dto));
    }
}
