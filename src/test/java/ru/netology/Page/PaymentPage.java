package ru.netology.Page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.Data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class PaymentPage {
// поля на странице
    private SelenideElement fieldCardNumber = $("input[placeholder='0000 0000 0000 0000']");
    private SelenideElement fieldMonth = $("input[placeholder='08']");
    private SelenideElement fieldYear = $("input[placeholder='22']");
    private SelenideElement fieldName = $(byText("Владелец")).parent().$("input");
    private SelenideElement fieldCvv = $("input[placeholder='999']");
// кнопка продолжить
    private SelenideElement buttonContinue = $(withText("Продолжить"));
//    ошибки полей
    private SelenideElement fieldCardNumberError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    private SelenideElement fieldMonthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    private SelenideElement fieldYearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    private SelenideElement fieldNameError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    private SelenideElement fieldCvvError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");
// всплывающие сообщения
    private SelenideElement notificationApproved = $(".notification_status_ok");
    private SelenideElement notificationError = $(".notification_status_error");


//    продолжить
    public void pressButtonForContinue (){
        buttonContinue.click();
    }

//    пустая форма
    public void emptyForm() {
        buttonContinue.click();
        fieldCardNumberError.shouldBe(Condition.visible);
        fieldMonthError.shouldBe(Condition.visible);
        fieldYearError.shouldBe(Condition.visible);
        fieldNameError.shouldBe(Condition.visible);
        fieldCvvError.shouldBe(Condition.visible);
    }

//    операция одобрена
    public void approvedBank() {
        notificationApproved.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    //    операция отклонена
    public void rejectionBank() {
        notificationError.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void sendingValidData (DataHelper.CardInfo info) {
        fieldCardNumber.setValue(info.getCardNumber());
        fieldMonth.setValue(info.getMonth());
        fieldYear.setValue(info.getYear());
        fieldName.setValue(info.getName());
        fieldCvv.setValue(info.getCvv());
        buttonContinue.click();
    }

    public void sendingNotValidData (DataHelper.CardInfo info) {
        fieldCardNumber.setValue(info.getCardNumber());
        fieldMonth.setValue(info.getMonth());
        fieldYear.setValue(info.getYear());
        fieldName.setValue(info.getName());
        fieldCvv.setValue(info.getCvv());
        buttonContinue.click();
    }

    public void sendingValidDataWithFieldCardNumberError () {
        fieldCardNumberError.shouldBe(Condition.visible);
        fieldMonthError.shouldBe(Condition.hidden);
        fieldYearError.shouldBe(Condition.hidden);
        fieldNameError.shouldBe(Condition.hidden);
        fieldCvvError.shouldBe(Condition.hidden);
    }

    public void sendingValidDataWithFakerCardNumber () {
        notificationError.shouldBe(Condition.visible);
        fieldMonthError.shouldBe(Condition.hidden);
        fieldYearError.shouldBe(Condition.hidden);
        fieldNameError.shouldBe(Condition.hidden);
        fieldCvvError.shouldBe(Condition.hidden);
    }

    public void sendingValidDataWithFieldMonthError () {
        fieldCardNumberError.shouldBe(Condition.hidden);
        fieldMonthError.shouldBe(Condition.visible);
        fieldYearError.shouldBe(Condition.hidden);
        fieldNameError.shouldBe(Condition.hidden);
        fieldCvvError.shouldBe(Condition.hidden);
    }

    public void sendingValidDataWithFieldYearError () {
        fieldCardNumberError.shouldBe(Condition.hidden);
        fieldMonthError.shouldBe(Condition.hidden);
        fieldYearError.shouldBe(Condition.visible);
        fieldNameError.shouldBe(Condition.hidden);
        fieldCvvError.shouldBe(Condition.hidden);
    }

    public void sendingEmptyNameValidData (DataHelper.CardInfo info) {
        fieldCardNumber.setValue(info.getCardNumber());
        fieldMonth.setValue(info.getMonth());
        fieldYear.setValue(info.getYear());
        fieldCvv.setValue(info.getCvv());
        buttonContinue.click();
    }

    public void sendingValidDataWithFieldNameError () {
        fieldCardNumberError.shouldBe(Condition.hidden);
        fieldMonthError.shouldBe(Condition.hidden);
        fieldYearError.shouldBe(Condition.hidden);
        fieldNameError.shouldBe(Condition.visible);
        fieldCvvError.shouldBe(Condition.hidden);
    }

    public void sendingValidDataWithFieldCVVError () {
        fieldCardNumberError.shouldBe(Condition.hidden);
        fieldMonthError.shouldBe(Condition.hidden);
        fieldYearError.shouldBe(Condition.hidden);
        fieldNameError.shouldBe(Condition.hidden);
        fieldCvvError.shouldBe(Condition.visible);
    }

}
