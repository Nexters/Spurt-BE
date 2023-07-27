package com.sirius.spurt.store.provider.jobgroup;

import com.sirius.spurt.common.meta.JobGroup;

public interface JobGroupProvider {
    void saveJobGroup(final String userId, final JobGroup jobGroup);
}
