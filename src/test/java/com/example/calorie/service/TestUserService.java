package com.example.calorie.service;

import com.example.calorie.dao.UserRepositoryDao;
import com.example.calorie.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryDao userRepositoryDao;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John Doe");
    }

    @Test
    void testAddUser() {
        when(userRepositoryDao.save(any(User.class))).thenReturn(user);

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals(user, savedUser);
        verify(userRepositoryDao, times(1)).save(user);
    }

    @Test
    void testGetUser() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));

        User foundUser = userService.getUser(1L);

        assertNotNull(foundUser);
        assertEquals(user, foundUser);
        verify(userRepositoryDao, times(1)).findById(1L);
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User(1L, "Jane Doe");
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));
        when(userRepositoryDao.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        verify(userRepositoryDao, times(1)).save(result);
    }

    @Test
    void testDeleteUser() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(1L);

        assertEquals("delete", result);
        verify(userRepositoryDao, times(1)).delete(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Arrays.asList(user, new User(2L, "Jane Smith"));
        when(userRepositoryDao.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(users, result);
        verify(userRepositoryDao, times(1)).findAll();
    }

    @Test
    void testGetAllUserByFood() {
        List<User> users = Arrays.asList(user, new User(2L, "Jane Smith"));
        String foodName = "Apple";
        when(userRepositoryDao.findAllUserByFood(foodName)).thenReturn(users);

        List<User> result = userService.getAllUserByFood(foodName);

        assertNotNull(result);
        assertEquals(users, result);
        verify(userRepositoryDao, times(1)).findAllUserByFood(foodName);
    }

    @Test
    void testAddUsers() {
        List<User> users = Arrays.asList(user, new User(2L, "Jane Smith"));
        when(userRepositoryDao.save(any(User.class))).thenReturn(user);

        List<User> result = userService.addUsers(users);

        assertNotNull(result);
        assertEquals(users.size(), result.size());
        verify(userRepositoryDao, times(users.size())).save(any(User.class));
    }
}