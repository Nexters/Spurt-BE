package com.sirius.spurt.store.provider.user.vo;

import com.sirius.spurt.common.meta.JobGroup;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVo {
    private String userId;

    private JobGroup jobGroup;
}
