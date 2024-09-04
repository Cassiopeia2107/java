package com.example.calorie.controller;

import com.example.calorie.model.User;
import com.example.calorie.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddUser() {
        User user = new User("John Doe");
        when(userService.addUser(user)).thenReturn(user);

        User result = userController.addUser(user);

        assertEquals(user, result);
        verify(userService, times(1)).addUser(user);
    }

    @Test
    void testAddUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("John Doe"));
        users.add(new User("Jane Smith"));
        when(userService.addUsers(users)).thenReturn(users);

        List<User> result = userController.addUsers(users);

        assertEquals(users, result);
        verify(userService, times(1)).addUsers(users);
    }

    @Test
    void testGetUser() {
        User user = new User("John Doe");
        when(userService.getUser(1L)).thenReturn(user);

        User result = userController.getUser(1L);

        assertEquals(user, result);
        verify(userService, times(1)).getUser(1L);
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User("Jane Smith");
        when(userService.updateUser(1L, updatedUser)).thenReturn(updatedUser);

        User result = userController.updateUser(1L, updatedUser);

        assertEquals(updatedUser, result);
        verify(userService, times(1)).updateUser(1L, updatedUser);
    }

//    @Test
//    void testDeleteUser() {
//        when(userService.deleteUser(1L)).thenReturn("User deleted successfully");
//
//        String result = userController.deleteUser(1L);
//
//        assertEquals("User deleted successfully", result);
//        verify(userService, times(1)).deleteUser(1L);
//    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("John Doe"));
        users.add(new User("Jane Smith"));
        when(userService.getAllUsers()).thenReturn(users);

        List<User> result = userController.getAllUsers();

        assertEquals(users, result);
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUsersByFood() {
        List<User> users = new ArrayList<>();
        users.add(new User("John Doe"));
        users.add(new User("Jane Smith"));
        when(userService.getAllUserByFood("Apple")).thenReturn(users);

        List<User> result = userController.getUsersByFood("Apple");

        assertEquals(users, result);
        verify(userService, times(1)).getAllUserByFood("Apple");
    }
}

