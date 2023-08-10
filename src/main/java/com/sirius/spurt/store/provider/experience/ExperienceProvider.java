package com.sirius.spurt.store.provider.experience;

public interface ExperienceProvider {
    void saveExperience(
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
}
