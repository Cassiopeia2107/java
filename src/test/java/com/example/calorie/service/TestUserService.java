package com.example.calorie.service;

import com.example.calorie.dao.UserRepositoryDao;
import com.example.calorie.model.Food;
import com.example.calorie.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Arrays;
import java.util.Collections;
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
    @Test
    void testGetUserNotFound() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.getUser(1L));
        verify(userRepositoryDao, times(1)).findById(1L);
    }
    @Test
    void testUpdateUserNotFound() {
        User updatedUser = new User(1L, "Jane Doe");
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, updatedUser));
        verify(userRepositoryDao, times(1)).findById(1L);
    }
    @Test
    void testDeleteUserNotFound() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepositoryDao, times(1)).findById(1L);
    }
    @Test
    void testAddUsersEmptyList() {
        List<User> users = Collections.emptyList();

        List<User> result = userService.addUsers(users);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepositoryDao, never()).save(any(User.class));
    }
    @Test
    void testUpdateUser_WithProducts() {
        User existingUser = new User(1L, "John Doe");
        Food product1 = new Food(1L, "Apple", 100);
        Food product2 = new Food(2L, "Banana", 80);

        User updatedUser = new User(1L, "John Doe");
        updatedUser.addProduct(product1);
        updatedUser.addProduct(product2);

        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepositoryDao.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(2, result.getProducts().size());
        assertTrue(result.getProducts().contains(product1));
        assertTrue(result.getProducts().contains(product2));
        assertEquals(result, product1.getUser());
        assertEquals(result, product2.getUser());
        verify(userRepositoryDao, times(1)).save(result);
    }
    @Test
    void testDeleteUser_WithProducts() {
        User existingUser = new User(1L, "John Doe");
        Food product1 = new Food(1L, "Apple", 100);
        Food product2 = new Food(2L, "Banana", 80);
        existingUser.addProduct(product1);
        existingUser.addProduct(product2);

        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(existingUser));

        String result = userService.deleteUser(1L);

        assertEquals("delete", result);
        assertNull(product1.getUser());
        assertNull(product2.getUser());
        verify(userRepositoryDao, times(1)).delete(existingUser);
    }
}