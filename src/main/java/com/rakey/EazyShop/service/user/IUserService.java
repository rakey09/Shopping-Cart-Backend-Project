package com.rakey.EazyShop.service.user;

import com.rakey.EazyShop.model.User;
import com.rakey.EazyShop.request.CreateUserRequest;
import com.rakey.EazyShop.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);
}
