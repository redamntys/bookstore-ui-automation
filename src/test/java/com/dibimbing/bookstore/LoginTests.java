package com.dibimbing.bookstore;

import com.dibimbing.core.BaseTests;
import com.dibimbing.core.DriverManager;
import com.dibimbing.core.RetryAnalyzer;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginTests extends BaseTests {
    private static final Logger logger = LogManager.getLogger(LoginTests.class);

    @Test(priority = 1, groups = {"smoke"}, description = "Test successful login", retryAnalyzer = RetryAnalyzer.class)
    public void testLoginSuccess() {
        logger.info("Starting login test with valid user credentials");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("bookstoreUrl"));

        logger.info("User logs in using valid user credentials");
        loginPage.login(config.getProperty("successUser"), config.getProperty("passwordUser"));

        logger.info("Verify that the user successfully logs in and can view the Profile page");
        Assert.assertTrue(loginPage.verifyLoginSuccess(),
                "User should be able to see the Profile page after logging in with valid credentials");
        logger.info("testLoginSuccess: Passed!");
    }

    @Test(priority = 2, groups = {"smoke"}, description = "Test failed login", retryAnalyzer = RetryAnalyzer.class)
    public void testLoginFailed() {
        logger.info("Starting login test with valid user credentials");
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("bookstoreUrl"));

        logger.info("User logs in using failed user credentials");
        loginPage.login(config.getProperty("failedUser2"), config.getProperty("passwordUser"));

        logger.info("Verify that the error message is displayed");
        Assert.assertTrue(loginPage.verifyLoginFailed(),
                "User should be able to see the error message and can't login");
        logger.info("testLoginFailed : Passed!");
    }
}
