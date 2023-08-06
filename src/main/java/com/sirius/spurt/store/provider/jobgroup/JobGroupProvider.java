package com.sirius.spurt.store.provider.jobgroup;

import com.sirius.spurt.common.meta.JobGroup;

public interface JobGroupProvider {
    void saveJobGroup(final String userId, final String email, final JobGroup jobGroup);

    void updateJobGroup(final String userId, final String email, final JobGroup jobGroup);
}
