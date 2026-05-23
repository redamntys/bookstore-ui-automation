package com.dibimbing.bookstore;


import com.dibimbing.core.BaseTests;
import com.dibimbing.core.DriverManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class HomePageTests extends BaseTests {
    private static final Logger logger = LogManager.getLogger(HomePageTests.class);

    @Test(priority = 1, groups = {"smoke"}, description = "Test homepage elements visibility")
    public void testVerifyHomepage() {
        HomePage homePage = new HomePage(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("bookstoreUrl"));

        logger.info("User login menggunakan credential success user");
        loginPage.login(config.getProperty("successUser"), config.getProperty("passwordUser"));

        logger.info("Verify user can see the homepage");
        homePage.navigateToHomepage();

        homePage.verifyLandingOnHomepage();
        homePage.verifyDropdownUserIsDisplayed();
        homePage.verifySortIsDisplayed();
        homePage.verifySearchFunction();
    }

    @Test(priority = 1, groups = {"smoke"}, description = "Test sort product price asc")
    public void testSortPriceByAsc(){

        HomePage homePage = new HomePage(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("bookstoreUrl"));

        logger.info("User login menggunakan credential success user");
        loginPage.login(config.getProperty("successUser"), config.getProperty("passwordUser"));

        homePage.navigateToHomepage();
        logger.info("Successfully navigated to homepage");

        homePage.verifyLandingOnHomepage();
        logger.info("Successfully verified landing on homepage");

        homePage.verifyPriceSortbyAsc();
        logger.info("Successfully verified product sorting by ascending price");

        List<Double> actualPrices = homePage.getProductPrice();
        logger.info("Actual sorted price: {}", actualPrices);

        logger.info("Applying sort : Price(sort by ASC)");
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        expectedPrices.sort(Comparator.naturalOrder()); // sort ASCENDING

        logger.info("Expected sorted price: {} ", expectedPrices);

        Assert.assertEquals(actualPrices, expectedPrices,
                "Products are not sorted by ascending price");

        logger.info("[HOMEPAGE] testSortPriceByAsc : Passed!");
    }

    @Test(priority = 1, groups = {"smoke"}, description = "Test sort product price desc")
    public void testSortPriceByDesc(){

        HomePage homePage = new HomePage(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("bookstoreUrl"));

        logger.info("User login menggunakan credential success user");
        loginPage.login(config.getProperty("successUser"), config.getProperty("passwordUser"));

        homePage.navigateToHomepage();
        logger.info("Successfully navigated to homepage");

        homePage.verifyLandingOnHomepage();
        logger.info("Successfully verified landing on homepage");

        homePage.verifyPriceSortbyDesc();
        logger.info("Successfully verified product sorting by descending price");

        List<Double> actualPrices = homePage.getProductPrice();
        logger.info("Actual sorted price: {}", actualPrices);

        logger.info("Applying sort : Price(sort by DESC)");
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        expectedPrices.sort(Comparator.reverseOrder()); // sort DESCENDING

        logger.info("Expected sorted price: {} ", expectedPrices);

        Assert.assertEquals(actualPrices, expectedPrices,
                "Products are not sorted by ascending price");

        logger.info("[HOMEPAGE] testSortPriceByDesc : Passed!");
    }

    @Test(priority = 1, groups = {"smoke"}, description = "Verify successful single product order creation")
    public void testSingleProduct(){

        HomePage homePage = new HomePage(DriverManager.getDriver());
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.openLoginPage(config.getProperty("bookstoreUrl"));

        logger.info("User login menggunakan credential success user");
        loginPage.login(config.getProperty("successUser"), config.getProperty("passwordUser"));

        homePage.navigateToHomepage();
        logger.info("Successfully navigated to homepage");

        homePage.verifyLandingOnHomepage();
        logger.info("Successfully verified landing on homepage");

        String productName = homePage.getFirstProductName();
        logger.info("Selected product = {}", productName);

        int beforeCount = homePage.getCartCount();
        logger.info("Before add to cart = {}" , beforeCount);

        homePage.addSingleProduct();
        logger.info("Add product to cart = {}", productName);

        int afterCount = homePage.getCartCount();
        logger.info("After add product to cart = {}" , afterCount);

        Assert.assertEquals(
                afterCount,
                beforeCount + 1,
                "Cart badge count did not increase"
        );

        homePage.verifyCartPageAddSingleProduct();
        logger.info("Successfully verified checkout page");

        homePage.proceedToLCheckout();
        logger.info("sucessful click button proceed to checkout");

        homePage.verifyCheckoutPageAddSingleProduct();
        logger.info("Successfully verified checkout page");

        homePage.verifySuccesfulCreateOrderSingleProduct();
        logger.info("verify successful create order single product");

        logger.info("[HOMEPAGE] testSingleProduct : Passed!");
    }

}
