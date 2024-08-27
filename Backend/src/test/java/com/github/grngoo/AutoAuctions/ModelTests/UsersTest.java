package com.github.grngoo.AutoAuctions.ModelTests;

import com.github.grngoo.AutoAuctions.Models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsersTest {

    private Users user;

    @BeforeEach
    public void setUp() {
        user = new Users("johndoe", "password123", "johndoe@example.com",
                "1234567890", "12345", "USA");
    }

    @Test
    @DisplayName("Test getUsername method")
    public void testGetUsername() {
        assertEquals("johndoe", user.getUsername(), "Username should be johndoe");
    }

    @Test
    @DisplayName("Test getPassword method")
    public void testGetPassword() {
        assertEquals("password123", user.getPassword(), "Password should be password123");
    }

    @Test
    @DisplayName("Test getEmail method")
    public void testGetEmail() {
        assertEquals("johndoe@example.com", user.getEmail(), "Email should be johndoe@example.com");
    }

    @Test
    @DisplayName("Test getTelephone method")
    public void testGetTelephone() {
        assertEquals("1234567890", user.getTelephone(), "Telephone should be 1234567890");
    }

    @Test
    @DisplayName("Test getPostal_code method")
    public void testGetPostalCode() {
        assertEquals("12345", user.getPostal_code());
    }

    @Test
    @DisplayName("Test getCountry method")
    public void testGetCountry() {
        assertEquals("USA", user.getCountry());
    }

    @Test
    @DisplayName("Test setUsername method")
    public void testSetUsername() {
        user.setUsername("newusername");
        assertEquals("newusername", user.getUsername());
    }

    @Test
    @DisplayName("Test setPassword method")
    public void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    @DisplayName("Test setEmail method")
    public void testSetEmail() {
        user.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Test setTelephone method")
    public void testSetTelephone() {
        user.setTelephone("0987654321");
        assertEquals("0987654321", user.getTelephone());
    }

    @Test
    @DisplayName("Test setPostal_code method")
    public void testSetPostalCode() {
        user.setPostal_code("54321");
        assertEquals("54321", user.getPostal_code());
    }

    @Test
    @DisplayName("Test setCountry method")
    public void testSetCountry() {
        user.setCountry("Canada");
        assertEquals("Canada", user.getCountry(), "Country should be updated to Canada");
    }

    @Test
    @DisplayName("Test toString method")
    public void testToString() {
        String expected = "User Details: \n" +
                "Username: johndoe\n" +
                "Password: ***********\n" +
                "Email: jo*****************\n" +
                "Telephone: 12********\n" +
                "Postal Code: 12***\n" +
                "Country: USA";
        assertEquals(expected, user.toString(), "toString method output should match the expected format");
    }
}
