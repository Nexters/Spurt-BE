package com.sirius.spurt.store.provider.user;

import com.sirius.spurt.store.provider.user.vo.UserVo;

public interface UserProvider {
    boolean checkUserExists(final String userId);

    UserVo getUserInfo(final String userId);

    boolean checkHasPined(final String userId);

    boolean checkHasPosted(final String userId);

    void deleteUser(final String userId);
}
