package com.example.calorie.service;

import com.example.calorie.dao.FoodRepositoryDao;
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TestUserService {

    @Mock
    private UserRepositoryDao userRepositoryDao;

    @Mock
    private FoodRepositoryDao foodRepositoryDao;

    @InjectMocks
    private UserService userService;

    private User user;
    private Food food;

    @BeforeEach
    void setUp() {
        user = new User(1L, "John Doe");
        user.setProducts(new HashSet<>());
        food = new Food(1L, "Apple", 100);
        user.addProduct(food);
    }

    @Test
    void testAddFoodToUser() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));
        when(foodRepositoryDao.findById(1L)).thenReturn(Optional.of(food));
        when(userRepositoryDao.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.addFoodToUser(1L, 1L);

        assertNotNull(result);
        assertTrue(result.getProducts().contains(food));
        assertEquals(user, food.getUser());
        verify(userRepositoryDao, times(1)).save(user);
    }

    @Test
    void testAddFoodToUser_FoodNotFound() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));
        when(foodRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.addFoodToUser(1L, 1L));
        verify(userRepositoryDao, never()).save(any(User.class));
    }


    @Test
    void testDeleteUser() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(1L);

        assertEquals("delete", result);
        assertNull(food.getUser());
        assertNull(user.getProducts());
        verify(userRepositoryDao, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
        verify(userRepositoryDao, never()).delete(any(User.class));
    }

    @Test
    void testDeleteFoodByUser_FoodNotFound() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));
        when(foodRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteFoodByUser(1L, 1L));
        verify(userRepositoryDao, never()).save(any(User.class));
    }

    @Test
    void testDeleteFoodByUser_UserNotFound() {
        when(userRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteFoodByUser(1L, 1L));
        verify(foodRepositoryDao, never()).findById(1L);
        verify(userRepositoryDao, never()).save(any(User.class));
    }

    @Test
    void testUpdateUser() {
        User updatedUser = new User(1L, "Jane Doe");
        updatedUser.setProducts(new HashSet<>());
        updatedUser.addProduct(food);

        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));
        when(userRepositoryDao.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(1, result.getProducts().size());
        assertTrue(result.getProducts().contains(food));

        verify(userRepositoryDao, times(1)).save(user);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        User updatedUser = new User(1L, "Jane Doe");
        updatedUser.setProducts(new HashSet<>());
        updatedUser.addProduct(food);

        when(userRepositoryDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, updatedUser));
        verify(userRepositoryDao, never()).save(any(User.class));
    }

    @Test
    void testUpdateUser_ExistingProduct() {
        user.addProduct(food);
        Food newFood = new Food(1L, "Apple", 100);
        User updatedUser = new User(1L, "Jane Doe");
        updatedUser.setProducts(new HashSet<>());
        updatedUser.addProduct(newFood);

        when(userRepositoryDao.findById(1L)).thenReturn(Optional.of(user));
        when(userRepositoryDao.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser(1L, updatedUser);

        assertNotNull(result);
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(1, result.getProducts().size());
        assertTrue(result.getProducts().contains(food));
        assertEquals(user, food.getUser());
        verify(userRepositoryDao, times(1)).save(user);
    }
    @Test
    void testAddUser_WithProducts() {
        when(userRepositoryDao.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);  // Simulate assigning ID upon saving
            return savedUser;
        });

        User savedUser = userService.addUser(user);

        assertNotNull(savedUser);
        assertEquals(1L, savedUser.getId());
        assertEquals("John Doe", savedUser.getName());
        assertFalse(savedUser.getProducts().isEmpty());
        assertEquals(savedUser, food.getUser());
        verify(userRepositoryDao, times(1)).save(user);
    }

    @Test
    void testAddUser_WithoutProducts() {
        User userWithoutProducts = new User(2L, "Jane Doe");
        when(userRepositoryDao.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(2L);  // Simulate assigning ID upon saving
            return savedUser;
        });

        User savedUser = userService.addUser(userWithoutProducts);

        assertNotNull(savedUser);
        assertEquals(2L, savedUser.getId());
        assertEquals("Jane Doe", savedUser.getName());
        verify(userRepositoryDao, times(1)).save(userWithoutProducts);
    }
}
