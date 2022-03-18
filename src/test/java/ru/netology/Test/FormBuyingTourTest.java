package ru.netology.Test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.Page.ChoosePage;
import ru.netology.Page.PaymentPage;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.Data.BaseGenerator.*;
import static ru.netology.Data.DataHelper.getApprovedCard;
import static ru.netology.Data.DataHelper.getDeclinedCard;

public class FormBuyingTourTest {
    ChoosePage choosePage = new ChoosePage();
    PaymentPage paymentPage = new PaymentPage();

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {

        SelenideLogger.removeListener("allure");
        cleanDatabase();
    }

    @BeforeEach
    void openPage() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:8080");
    }


    @Test
    @SneakyThrows
    @DisplayName("Покупка валидной картой")
    public void shouldTrueFullFormWithoutCredit() {
        choosePage.buyCard();
//        заполнение полей валидными данными
        var info = getApprovedCard();
        paymentPage.sendingValidData(info);
//        время на запись в базу данных
        TimeUnit.SECONDS.sleep(10);
//        получить ответ что запись в базе прошла
        var expected = "APPROVED";
        var paymentInfo = getPaymentInfo();
        var orderInfo = getOrderInfo();
//        проверка статуса карты ожидаемого и в базе
        assertEquals(expected, paymentInfo.getStatus());
//        проверка соответствия в базе данных id в таблице покупок и в таблице заявок:
        assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
//        получить ответ на сайте что операция прошла
        paymentPage.approvedBank();
    }



    @Test
    @SneakyThrows
    @DisplayName("Покупка тура с кредитом валидной картой")
    public void shouldTrueFullFormWithCredit() {
        choosePage.buyWithCredit();
//        заполнение полей валидными данными
        var info = getApprovedCard();
        paymentPage.sendingValidData(info);
//        время на запись в базу данных
        TimeUnit.SECONDS.sleep(10);
//        вводим переменные для провеки
        var expected = "APPROVED";
        var creditRequestInfo = getCreditRequestInfo();
        var orderInfo = getOrderInfo();
//        проверка статуса карты ожидаемого и в базе
        assertEquals(expected, creditRequestInfo.getStatus());
//        Проверка соответствия в базе данных id в таблице запросов кредита и в таблице заявок:
        assertEquals(creditRequestInfo.getBank_id(), orderInfo.getCredit_id());
//        получить ответ на сайте что операция прошла
        paymentPage.approvedBank();

    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка не валидной картой")
    public void shouldTrueFullFormWithoutCreditWithDeclinedCard() {
        choosePage.buyCard();
//        заполнение поля номера карты не валидным номером карты, остальных полей валидными данными
        var info = getDeclinedCard();
        paymentPage.sendingNotValidData(info);
//        время на запись в базу данных
        TimeUnit.SECONDS.sleep(10);
//        вводим переменные для провеки
        var expected = "DECLINED";
        var paymentInfo = getPaymentInfo();
        var orderInfo = getOrderInfo();
//        проверка статуса карты ожидаемого и в базе
        assertEquals(expected, paymentInfo.getStatus());
//        проверка соответствия в базе данных id в таблице запросов кредита и в таблице заявок:
        assertEquals(paymentInfo.getTransaction_id(), orderInfo.getPayment_id());
//        получить ответ на сайте что операция прошла
        paymentPage.rejectionBank();
    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка тура с кредитом не валидной картой")
    public void shouldTrueFullFormWithCreditWithDeclinedCard() {
        choosePage.buyWithCredit();
//        заполнение поля номера карты не валидным номером карты, остальных полей валидными данными
        var info = getDeclinedCard();
        paymentPage.sendingNotValidData(info);
//        время на запись в базу данных
        TimeUnit.SECONDS.sleep(10);
//        вводим переменные для провеки
        var expected = "DECLINED";
        var creditRequestInfo = getCreditRequestInfo();
        var orderInfo = getOrderInfo();
//        проверка статуса карты ожидаемого и в базе
        assertEquals(expected, creditRequestInfo.getStatus());
//        Проверка соответствия в базе данных id в таблице запросов кредита и в таблице заявок:
        assertEquals(creditRequestInfo.getBank_id(), orderInfo.getCredit_id());
//        получить ответ на сайте что операция прошла
        paymentPage.rejectionBank();
    }

    @Test
    @DisplayName("Покупка картой без заполнения полей")
    public void shouldEmptyFormWithoutCredit() {
        choosePage.buyCard();
        paymentPage.emptyForm();
    }

    @Test
    @DisplayName("Покупка картой без заполнения полей с кредитом")
    public void shouldEmptyFormWithCredit() {
        choosePage.buyWithCredit();
        paymentPage.emptyForm();
    }


}
