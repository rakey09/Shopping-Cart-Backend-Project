package com.rakey.EazyShop.controller;

import com.rakey.EazyShop.dto.UserDto;
import com.rakey.EazyShop.exceptions.AlreadyExistsException;
import com.rakey.EazyShop.exceptions.ResourceNotFoundException;
import com.rakey.EazyShop.model.User;
import com.rakey.EazyShop.request.CreateUserRequest;
import com.rakey.EazyShop.request.UserUpdateRequest;
import com.rakey.EazyShop.response.ApiResponse;
import com.rakey.EazyShop.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {

    @Autowired
    private IUserService userService;


    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = userService.getUserById(userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Success",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = userService.createUser(request);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Create User Successfully",userDto));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request, @PathVariable Long userId){
        try {
            User user = userService.updateUser(request,userId);
            UserDto userDto = userService.convertUserToDto(user);
            return ResponseEntity.ok(new ApiResponse("Updated User Successfully",userDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
             userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("deleted User Successfully",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
}
