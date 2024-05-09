package com.example.demo;

import com.codeborne.selenide.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CommunityPageTest {

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x1100";
        Configuration.headless = false;
        Configuration.timeout = 10000;
    }

    @BeforeEach
    public void setUp() {
        Configuration.browserCapabilities = new ChromeOptions().addArguments("--no-sandbox", "--disable-dev-shm-usage");
        open("https://vk.com/club225299895");
    }

    @Test
    public void testSubscribeButton() {
        $("#page_actions .FlatButton--primary").click();
        $("div.VkIdSignIn").shouldBe(visible);
    }

    @Test
    public void testDetailedInformationLink() {
        $(byText("Подробная информация")).click();
        $(".group-info-box").shouldBe(visible);
        $(".group-info-box__header .group-info-box__title").shouldHave(text("Подробная информация"));
    }

    @Test
    public void testSubscribersLinkLeadsToSignIn() {
        $(byText("Подписчики")).click();
        $("div.VkIdSignIn").shouldBe(visible);
    }

    @Test
    public void testContactsPanel() {
        $(byText("Контакты")).click();
        $(".box_layout[data-testid='box_layout']").shouldBe(visible);
        $(".box_title").shouldHave(text("Контакты"));
        $("button.FlatButton--primary").click();
        $(".box_layout[data-testid='box_layout']").shouldNotBe(visible);
    }

    @Test
    public void testShowAllServices() {
        $(byText("Показать все 1")).click();

        String expectedUrl = "https://vk.com/uslugi-225299895?screen=group";
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        $("#market_items_count").shouldHave(text("1"));
        $(".market_row_img_placeholder").shouldBe(visible);
    }

    @Test
    public void testProductClickOpensProductDetails() {
        $(byText("For testing test")).click();
        $(".ItemCardLayout__content").shouldBe(visible);
        $("h1.ItemName").shouldHave(text("For testing test"));
        $(".ItemPrice-module__actual--lyqkb").shouldHave(text("бесплатно"));
        $(byText("Написать")).shouldBe(visible);
    }

    @Test
    public void testAllPostsLinkNavigatesCorrectly() {
        $(byText("Все записи")).click();
        String expectedUrl = "https://vk.com/wall-225299895";
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlToBe(expectedUrl));
        $(".page_block_header_inner._header_inner[data-testid='header']").shouldHave(text("Все записи"));
        $("#fw_summary").shouldBe(visible);
    }

    @Test
    public void testCommunityPostsDoubleClicked() {
        SelenideElement communityPostsLink = $(byText("Записи сообщества"));
        communityPostsLink.click();
        communityPostsLink.click();

        $(".page_block_header_inner._header_inner[data-testid='header']")
                .shouldHave(text("Записи сообщества"));
        $("#fw_summary").shouldBe(visible);
    }


}
