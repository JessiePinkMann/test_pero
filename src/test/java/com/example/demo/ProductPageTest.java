package com.example.demo;

import com.codeborne.selenide.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductPageTest {
    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x1000";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--no-sandbox", "--disable-dev-shm-usage");
        open("https://vk.com/club225299895?w=product-225299895_10044406");
    }

    @Test
    public void testWriteButtonVisibility() {
        ProductPage productPage = new ProductPage();
        productPage.getWriteButton().shouldBe(visible);
    }

    @Test
    public void testDialogAfterWriteButtonClick() {
        ProductPage productPage = page(ProductPage.class);
        productPage.getWriteButton().click();
        $("div.popup_box_container").shouldBe(visible);
    }

    @Test
    public void testShareButton() {
        open("https://vk.com/club225299895?w=product-225299895_10044406");
        $x("//button[@data-action-name='share']").click();
        $("div.UnauthActionBox--vkid").shouldBe(visible);
        $("button.UnauthActionBox__login").shouldBe(visible);
        $("button.UnauthActionBox__join").shouldBe(visible);
    }

    @Test
    public void testLikeButton() {
        $x("//button[@data-action-name='like']").click();
        $("div.VkIdSignIn").shouldBe(visible);
    }

    @Test
    public void testFavoriteButton() {
        $x("//button[@data-action-name='favorite']").click();
        $("div.VkIdSignIn").shouldBe(visible);
    }

    @Test
    public void testWishlistButton() {
        $x("//button[@data-action-name='wishlist']").click();
        $("div.VkIdSignIn").shouldBe(visible);
    }


    @Test
    public void testCopyUrlButton() {
        SelenideElement copyButton = $x("//button[@data-action-name='copyUrl']");
        copyButton.click();

        String expectedUrl = "https://vk.com/club225299895?w=product-225299895_10044406";
        String clipboardText = Selenide.clipboard().getText();
        assertEquals(expectedUrl, clipboardText, "Ссылка в буфере обмена не соответствует ожидаемой");

        $("div.VkIdSignIn").shouldBe(visible);
    }

    @Test
    public void testSubscribeButton() {
        $$(".vkuiButton").findBy(text("Подписаться")).click();
        $("div.VkIdSignIn").shouldBe(visible);
    }


    @Test
    public void testGoToStoreButton() {
        $$(".vkuiButton").findBy(text("Перейти в магазин")).click();
        sleep(1000);
        switchTo().window(1);
        String currentUrl = WebDriverRunner.url();
        assertEquals("https://vk.com/uslugi-225299895?screen=market_item", currentUrl);
        closeWindow();
        switchTo().window(0);
    }

    @Test
    public void testMarketItemCardNavigation() {
        $("a[href='/club225299895']").click();
        $("h1.page_name").shouldHave(text("Test public for test"));
        $("#page_layout").shouldBe(visible);
        $("#side_bar").shouldBe(visible);
        $("#quick_login").shouldBe(visible);
    }

    @Test
    public void testNoPhotoPlaceholderVisibility() {
        $(".ItemGallery__main .ItemGallery__placeholder").shouldBe(visible);
        $(".ItemGallery__placeholderTitle").shouldHave(text("Нет фото"));
    }

    @Test
    public void testAddPhotoButtonLeadsToSignIn() {
        $("svg.vkuiIcon--add_square_outline_28").click();
        $("div.VkIdSignIn").shouldBe(visible);
    }



}
