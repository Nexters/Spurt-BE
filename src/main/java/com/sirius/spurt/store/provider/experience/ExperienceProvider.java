package com.sirius.spurt.store.provider.experience;

import com.sirius.spurt.store.provider.experience.vo.ExperienceVo;
import com.sirius.spurt.store.provider.experience.vo.ExperienceVoList;

public interface ExperienceProvider {
    ExperienceVo saveExperience(
            final String title,
            final String content,
            final String startDate,
            final String endDate,
            final String link,
            final String userId);

    void updateExperience(
            final Long experienceId,
            final String title,
            final String content,
            final String startDate,
            final String endDate,
            final String link,
            final String userId);

    void deleteExperience(final Long experienceId, final String userId);

    ExperienceVoList getAllExperience(final String userId);

    ExperienceVo getExperience(final Long experienceId, final String userId);
}
