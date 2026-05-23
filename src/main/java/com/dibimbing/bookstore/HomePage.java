package com.dibimbing.bookstore;


import com.dibimbing.core.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;


public class HomePage extends BasePage {
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    @FindBy(xpath = "//*[@id='core']/div/div/div[2]/div[2]/div[1]/div")
    private WebElement titleHomePage;

    @FindBy(css = ".filter_sort-select--label")
    private WebElement filterSortPrice;

    @FindBy(xpath = "//a[normalize-space()='Sort By ASC']")
    private WebElement sortByASC;

    @FindBy(xpath = "//a[normalize-space()='Sort By DESC']")
    private WebElement sortByDESC;

    @FindBy(xpath = "//input[@id='search-input']")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@id='search-btn']")
    private WebElement searchButton;

    @FindBy(xpath = "//a[@id='navbarDropdown']")
    private WebElement dropdownUser;

    @FindBy(xpath = "//a[@id='profile']")
    private WebElement profileCta;

    @FindBy(xpath = "//a[@id='logout']")
    private WebElement logoutCta;

    @FindBy(xpath = "//a[contains(text(),'E-commerce Bookstore')]")
    private WebElement homepageEntryPoint;

    @FindBy(css = "h5[data-testid^='title-']")
    private List<WebElement> productTitles;

    @FindBy(css = "a[data-testid^='cart-']")
    private List<WebElement> addToCartButtons;

    @FindBy(css = ".badge")
    private WebElement cartBadge;

    @FindBy(css = "a[href='/bookstore/cart']")
    private WebElement cartButton;

    //cart
    @FindBy(xpath = "//h1[text()='Shopping Cart']")
    private WebElement shoppingCartTitle;

    @FindBy(css = "td.information")
    private WebElement bookTitle;

    @FindBy(id = "cartQty")
    private WebElement cartQtyInput;

    @FindBy(css = "span.fw-bold")
    private WebElement productPrice1;

    @FindBy(css = "a[data-testid='checkout']")
    private WebElement checkoutButton;

    //checkout
    @FindBy(xpath = "//h1[text()='Checkout']")
    private WebElement titleCheckoutPage;

    @FindBy(id = "name")
    private WebElement nameCustomer;

    @FindBy(id = "address")
    private WebElement addressCustomer;

    @FindBy(id = "card-name")
    private WebElement cardHolderName;

    @FindBy(id = "card-number")
    private WebElement creditCardNumber;

    @FindBy(id = "card-expiry-month")
    private WebElement expirationMonth;

    @FindBy(id = "card-expiry-year")
    private WebElement expirationYear;

    @FindBy(id = "card-cvc")
    private WebElement cvc;

    @FindBy(css = "button[data-testid='purchase']")
    private WebElement purchaseButton;

    //profile
    @FindBy(id = "flash")
    private WebElement successfulInfo;

    @FindBy(xpath = "//span[text()='JavaScript for Web Developers']")
    private WebElement bookTitleJavaScript;

    @FindBy(css = "span.badge.bg-dark")
    private WebElement totalPriceBadge;

    @FindBy(xpath = "//span[contains(@style,'crimson')]")
    private WebElement productPrice;

    private JavascriptExecutor js;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void verifySortIsDisplayed() {
        scrollToElement(filterSortPrice);
        waitForElementToBeVisible(filterSortPrice);
        filterSortPrice.click();
        scrollToElement(sortByASC);
        waitForElementToBeVisible(sortByASC);
        waitForElementToBeVisible(sortByDESC);
    }

    public void verifyDropdownUserIsDisplayed() {
        waitForElementToBeVisible(dropdownUser);
        jsClick(dropdownUser);
        waitForElementToBeVisible(profileCta);
        waitForElementToBeVisible(logoutCta);
    }

    public void verifySearchFunction(){
        scrollToElement(searchInput);
        waitForElementToBeVisible(searchInput);
        searchInput.sendKeys("The DevOps Handbook");
        jsClick(searchButton);
    }

    public void verifyPriceSortbyAsc(){
        scrollToElement(filterSortPrice);
        waitForElementToBeVisible(filterSortPrice);
        filterSortPrice.click();
        scrollToElement(sortByASC);
        jsClick(sortByASC);
    }

    public void verifyPriceSortbyDesc(){
        scrollToElement(filterSortPrice);
        waitForElementToBeVisible(filterSortPrice);
        filterSortPrice.click();
        scrollToElement(sortByDESC);
        jsClick(sortByDESC);
    }

    public List<Double> getProductPrice() {
        List<WebElement> priceElements = driver.findElements(
                By.cssSelector("[data-testid^='price-']"));
        List<Double> prices = new ArrayList<>();
        logger.info("[TEST] check priceElements: {}", priceElements);
        for (WebElement element : priceElements) {
            String text = element.getText()
                    .replace("€", "")
                    .trim();

            prices.add(Double.parseDouble(text));
        }
        return prices;
    }

    public void verifyLandingOnHomepage(){
        waitForElementToBeVisible(titleHomePage);
        scrollToElement(titleHomePage);
    }

    public void navigateToHomepage(){
        scrollToElement(homepageEntryPoint);
        waitForElementToBeVisible(homepageEntryPoint);
        jsClick(homepageEntryPoint);
    }

    public String getFirstProductName() {
        return productTitles.get(0).getText().trim();
    }

    public void addSingleProduct(){
        scrollToElement(addToCartButtons.get(0));
        waitForElementToBeVisible(addToCartButtons.get(0));
        addToCartButtons.get(0).click();
    }

    public void verifyCartPageAddSingleProduct(){
        scrollToElement(cartButton);
        waitForElementToBeVisible(cartButton);
        cartButton.click();
        waitForElementToBeVisible(shoppingCartTitle);
        shoppingCartTitle.isDisplayed();
        bookTitle.isDisplayed();
        bookTitle.getText().equals("JavaScript for Web Developers");
        logger.info("verify bookTitle");
        cartQtyInput.isDisplayed();
        cartQtyInput.getText().equals("1");
        logger.info("verify cartQtyInput");
        productPrice1.isDisplayed();
        productPrice1.getText().equals("1");
        logger.info("verify productPrice1");
    }

    public void proceedToLCheckout(){
        scrollToElement(checkoutButton);
        checkoutButton.isDisplayed();
        checkoutButton.click();
    }

    public void verifyCheckoutPageAddSingleProduct(){
        scrollToElement(titleCheckoutPage);
        waitForElementToBeVisible(titleCheckoutPage);
        titleCheckoutPage.getText().equals("Checkout");
        scrollToElement(nameCustomer);
        nameCustomer.sendKeys("Red");
        logger.info("berhasil input name");

        scrollToElement(addressCustomer);
        addressCustomer.sendKeys("jl. kalimantan");
        logger.info("berhasil input address");

        scrollToElement(cardHolderName);
        cardHolderName.sendKeys("TEST");
        logger.info("berhasil input cardHolderName");

        scrollToElement(creditCardNumber);
        creditCardNumber.sendKeys("4242424242424242");
        logger.info("berhasil input cardHolderName");

        scrollToElement(expirationMonth);
        expirationMonth.sendKeys("10");
        logger.info("berhasil cardHolderName ");

        scrollToElement(expirationYear);
        expirationYear.sendKeys("2028");
        logger.info("berhasil input expirationYear");

        scrollToElement(cvc);
        cvc.sendKeys("242");
        logger.info("berhasil input cvc");

        scrollToElement(purchaseButton);
        purchaseButton.click();
        wait.until(ExpectedConditions.visibilityOf(successfulInfo));
    }

    public void verifySuccesfulCreateOrderSingleProduct(){
        waitForElementToBeVisible(successfulInfo);
        scrollToElement(successfulInfo);
        successfulInfo.isDisplayed();
        scrollToElement(bookTitleJavaScript);
        bookTitleJavaScript.isDisplayed();
        bookTitleJavaScript.getText().equals("JavaScript for Web Developers");
        scrollToElement(totalPriceBadge);
        totalPriceBadge.getText().equals("40€");
    }

    public int getCartCount() {
        By badge = By.cssSelector(".badge");
        try {
            return Integer.parseInt(driver.findElement(badge).getText());
        } catch (Exception e) {
            return 0;
        }
    }
}
