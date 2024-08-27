package com.github.grngoo.AutoAuctions.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity Class mapped to the Model table in database.
 * Consists of the columns for the represented entity.
 *
 * @author danielmunteanu
 */
@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telephone", nullable = false)
    private String telephone;

    @Column(name = "postal_code", nullable = false)
    private String postal_code;

    @Column(name = "country", nullable = false)
    private String country;

    /**
     * Default constructor for JPA.
     */
    public Users() {}

    /**
     * Constructs a new {@code Users} object with the specified details.
     *
     * @param username   the username of the user
     * @param password   the password of the user
     * @param email      the email address of the user
     * @param telephone  the telephone number of the user
     * @param postal_code the postal code of the user
     * @param country    the country of the user
     */
    public Users(String username, String password, String email,
                 String telephone, String postal_code, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.postal_code = postal_code;
        this.country = country;
    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the email address of the user.
     *
     * @return the email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the telephone number of the user.
     *
     * @return the telephone number of the user
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Returns the postal code of the user.
     *
     * @return the postal code of the user
     */
    public String getPostal_code() {
        return postal_code;
    }

    /**
     * Returns the country of the user.
     *
     * @return the country of the user
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the username of the user.
     *
     * @param username the new username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new email address of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the telephone number of the user.
     *
     * @param telephone the new telephone number of the user
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * Sets the postal code of the user.
     *
     * @param postal_code the new postal code of the user
     */
    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    /**
     * Sets the country of the user.
     *
     * @param country the new country of the user
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Returns a string representation of the user, with sensitive data masked.
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return ("User Details: \n" +
                "Username: " + username + "\n" +
                "Password: " + password.replaceAll(".", "*") + "\n" +
                "Email: " + (email.substring(0, 2) + "*".repeat(email.length() - 2)) + "\n" +
                "Telephone: " + (telephone.substring(0, 2) + "*".repeat(telephone.length() - 2)) + "\n" +
                "Postal Code: " + (postal_code.substring(0, 2) + "*".repeat(postal_code.length() - 2)) + "\n" +
                "Country: " + country);
    }
}
