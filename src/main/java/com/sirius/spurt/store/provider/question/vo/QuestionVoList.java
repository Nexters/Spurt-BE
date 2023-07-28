package com.sirius.spurt.store.provider.question.vo;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVoList {
    private List<QuestionVo> questions;
    private Pageable pageable;
    private Long totalCount;
    private Integer totalPage;
}
