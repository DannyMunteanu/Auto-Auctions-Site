package com.github.grngoo.AutoAuctions.DTOs;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for user details.
 * This class represents user information required for registration and login operations.
 *
 * @author danielmunteanu
 */
public class UsersDto {

    /**
     * The username of the user.
     * This field is required and cannot be blank.
     */
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * The password of the user.
     * This field is required and cannot be blank.
     */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * The email address of the user.
     * This field is optional.
     */
    private String email;

    /**
     * The telephone number of the user.
     * This field is optional.
     */
    private String telephone;

    /**
     * The postal code of the user.
     * This field is optional.
     */
    private String postal_code;

    /**
     * The country of the user.
     * This field is optional.
     */
    private String country;

    /**
     * Gets the username of the user.
     *
     * @return the username of the user
     */
    public @NotBlank(message = "Username is required") String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the username to set
     */
    public void setUsername(@NotBlank(message = "Username is required") String username) {
        this.username = username;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public @NotBlank(message = "Password is required") String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the password to set
     */
    public void setPassword(@NotBlank(message = "Password is required") String password) {
        this.password = password;
    }

    /**
     * Gets the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the telephone number of the user.
     *
     * @return the telephone number of the user
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Sets the telephone number of the user.
     *
     * @param telephone the telephone number to set
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Gets the postal code of the user.
     *
     * @return the postal code of the user
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Sets the postal code of the user.
     *
     * @param postal_code the postal code to set
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Gets the country of the user.
     *
     * @return the country of the user
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country of the user.
     *
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
