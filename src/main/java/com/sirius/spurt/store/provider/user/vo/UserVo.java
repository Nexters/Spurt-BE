package com.sirius.spurt.store.provider.user.vo;

import com.sirius.spurt.common.meta.JobGroup;
import lombok.Data;

@Data
public class UserVo {
    private String userId;

    private JobGroup jobGroup;
}
