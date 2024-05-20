package com.example.calorie.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

/** The type User. */
@Entity
@Table(name = "users")
public class User {
  /** The Id. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private String name;

  @OneToMany(
      mappedBy = "user",
      cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private Set<Food> products = new HashSet<>();

  /** Instantiates a new User. */
  public User() {}

  /**
   * Instantiates a new User.
   *
   * @param name the name
   */
  public User(String name) {
    this.name = name;
  }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets products.
   *
   * @return the products
   */
  public Set<Food> getProducts() {
    return products;
  }

  /**
   * Sets products.
   *
   * @param products the products
   */
  public void setProducts(Set<Food> products) {
    this.products = products;
  }

  /**
   * Gets total calories.
   *
   * @return the total calories
   */
  @Transient
  public int getTotalCalories() {
    return products.stream().mapToInt(Food::getCalories).sum();
  }

  /**
   * Add product.
   *
   * @param product the product
   */
  public void addProduct(Food product) {
    products.add(product);
    product.setUser(this);
  }

  /**
   * Remove product.
   *
   * @param product the product
   */
  public void removeProduct(Food product) {
    products.remove(product);
    product.setUser(null);
  }
}
