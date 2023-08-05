package com.sirius.spurt.store.provider.user;

import com.sirius.spurt.store.provider.user.vo.UserVo;

public interface UserProvider {
    boolean checkUserExists(String userId);

    UserVo getUserInfo(String userId);
}
