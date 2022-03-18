package ru.netology.Page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class ChoosePage {
//    кнопки на странице
    private SelenideElement buttonBuy = $(withText("Купить"));
    private SelenideElement buttonBuyWithCredit = $(withText("Купить в кредит"));
//    заголовки появляющиеся после нажатия кнопок
    private SelenideElement buttonBuyHeading = $(withText("Оплата по карте"));
    private SelenideElement buttonBuyWithCreditHeading = $(withText("Кредит по данным карты"));

    public void buyCard() {
        buttonBuy.click();
        buttonBuyHeading.shouldBe(Condition.visible);
    }

    public void buyWithCredit() {
        buttonBuyWithCredit.click();
        buttonBuyWithCreditHeading.shouldBe(Condition.visible);
    }

}
