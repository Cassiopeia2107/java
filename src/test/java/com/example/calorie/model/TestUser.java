package com.example.calorie.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John Doe");
    }

    @Test
    void testConstructors() {
        User user1 = new User();
        assertNotNull(user1);

        User user2 = new User("Jane Smith");
        assertEquals("Jane Smith", user2.getName());

        User user3 = new User(1L, "Bob Johnson");
        assertEquals(1L, user3.getId());
        assertEquals("Bob Johnson", user3.getName());
    }

    @Test
    void testGettersAndSetters() {
        user.setId(1L);
        assertEquals(1L, user.getId());

        user.setName("Alice Brown");
        assertEquals("Alice Brown", user.getName());

        Set<Food> products = new HashSet<>();
        products.add(new Food(1L, "Apple", 100));
        products.add(new Food(2L, "Banana", 80));
        user.setProducts(products);
        assertEquals(products, user.getProducts());
    }

    @Test
    void testTotalCalories() {
        Food apple = new Food(1L, "Apple", 100);
        Food banana = new Food(2L, "Banana", 80);
        user.addProduct(apple);
        user.addProduct(banana);

        int totalCalories = user.getTotalCalories();
        assertEquals(180, totalCalories);
    }

    @Test
    void testAddProduct() {
        Food apple = new Food(1L, "Apple", 100);
        user.addProduct(apple);

        assertTrue(user.getProducts().contains(apple));
        assertEquals(user, apple.getUser());
    }

    @Test
    void testRemoveProduct() {
        Food apple = new Food(1L, "Apple", 100);
        user.addProduct(apple);
        user.removeProduct(apple);

        assertFalse(user.getProducts().contains(apple));
        assertNull(apple.getUser());
    }
}