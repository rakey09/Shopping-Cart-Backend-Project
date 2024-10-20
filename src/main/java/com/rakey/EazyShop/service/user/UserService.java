package com.rakey.EazyShop.service.user;

import com.rakey.EazyShop.exceptions.AlreadyExistsException;
import com.rakey.EazyShop.exceptions.ResourceNotFoundException;
import com.rakey.EazyShop.model.User;
import com.rakey.EazyShop.repository.UserRepository;
import com.rakey.EazyShop.request.CreateUserRequest;
import com.rakey.EazyShop.request.UserUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req ->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                }).orElseThrow(() ->new AlreadyExistsException(request.getEmail()+" Already Exists"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(()->new ResourceNotFoundException("User Not Found!"));
    }

    @Override
    public void deleteUser(Long userId) {
       userRepository.findById(userId).ifPresentOrElse(userRepository::delete,()->{
           throw new ResourceNotFoundException("User Not Found");
       });
    }
}
