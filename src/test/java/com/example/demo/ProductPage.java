package com.example.demo;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class ProductPage {
    private String writeButtonSelector = "button .MarketServiceButton__text";

    public SelenideElement getWriteButton() {
        return $(writeButtonSelector);
    }
}
