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
import static ru.netology.Data.DataHelper.*;

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
//        Configuration.holdBrowserOpen = true;  // для открытия окна браузера
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
//        получить ответ на сайте что операция прошла
        paymentPage.approvedBank();
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

    }



    @Test
    @SneakyThrows
    @DisplayName("Покупка тура с кредитом валидной картой")
    public void shouldTrueFullFormWithCredit() {
        choosePage.buyWithCredit();
//        заполнение полей валидными данными
        var info = getApprovedCard();
        paymentPage.sendingValidData(info);
//        получить ответ на сайте что операция прошла
        paymentPage.approvedBank();
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


    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка не валидной картой")
    public void shouldTrueFullFormWithoutCreditWithDeclinedCard() {
        choosePage.buyCard();
//        заполнение поля номера карты не валидным номером карты, остальных полей валидными данными
        var info = getDeclinedCard();
        paymentPage.sendingNotValidData(info);
//        получить ответ на сайте что операция не прошла
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
//        получить ответ на сайте что операция не прошла
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

    @Test
    @DisplayName("Покупка картой без заполнения поля карты а остальные поля валидными данными")
    public void shouldEmptyFieldCardWithoutCredit() {
        choosePage.buyCard();
        var info = getEmptyCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка картой при заполнения поля карты одной цифрой а остальные поля валидными данными")
    public void shouldOneNumberInFieldCardNumberWithoutCredit() {
        choosePage.buyCard();
        var info = getOneNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка картой при заполнения поля карты 15 цифрами а остальные поля валидными данными")
    public void shouldFifteenNumberInFieldCardNumberWithoutCredit() {
        choosePage.buyCard();
        var info = getFifteenNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка неизвестной картой при заполнения поля карты а остальные поля валидными данными")
    public void shouldUnknownCardInFieldCardNumberWithoutCredit() {
        choosePage.buyCard();
        var info = getFakerNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFakerCardNumber();
    }

    @Test
    @DisplayName("Покупка картой без заполнения поля месяц а остальные поля валидными данными")
    public void shouldEmptyFieldMonthWithoutCredit() {
        choosePage.buyCard();
        var info = getEmptyMonth();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка картой c заполнением поля месяц одной цифрой а остальные поля валидными данными")
    public void shouldOneNumberInFieldMonthWithoutCredit() {
        choosePage.buyCard();
        var info = getOneNumberMonth();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка картой в поле месяц предыдущий от текущего а остальные поля валидными данными")
    public void shouldFieldWithPreviousMonthWithoutCredit() {
        choosePage.buyCard();
        var info = getPreviousMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка картой в поле месяц в верном формате вести нулевой (не существующий) месяц" +
            " а остальные поля валидными данными")
    public void shouldFieldWithZeroMonthWithoutCredit() {
        choosePage.buyCard();
        var info = getZeroMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка картой в поле месяц в верном формате вести тринадцатый (не существующий) месяц" +
            " а остальные поля валидными данными")
    public void shouldFieldWithThirteenMonthWithoutCredit() {
        choosePage.buyCard();
        var info = getThirteenMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка картой без заполнения поля год а остальные поля валидными данными")
    public void shouldEmptyFieldYearWithoutCredit() {
        choosePage.buyCard();
        var info = getEmptyYear();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка картой заполнение поля год, предыдущим годом от текущего" +
            " а остальные поля валидными данными")
    public void shouldPreviousYearFieldYearWithoutCredit() {
        choosePage.buyCard();
        var info = getPreviousYearInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка картой заполнение поля год, на шесть лет больше чем текущий" +
            " а остальные поля валидными данными")
    public void shouldPlusSixYearFieldYearWithoutCredit() {
        choosePage.buyCard();
        var info = getPlusSixYearInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка картой при пустом поле владелец а остальные поля валидными данными")
    public void shouldEmptyFieldNameWithoutCredit() {
        choosePage.buyCard();
        var info = getApprovedCard();
        paymentPage.sendingEmptyNameValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка картой при заполнении поля владелец пробелом а остальные поля валидными данными")
    public void shouldSpaceFieldNameWithoutCredit() {
        choosePage.buyCard();
        var info = getSpaceName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка картой при заполнении поля владелец спец. символами" +
            " а остальные поля валидными данными")
    public void shouldSpecialSymbolInFieldNameWithoutCredit() {
        choosePage.buyCard();
        var info = getSpecialSymbolInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка картой при заполнении поля владелец цифрами" +
            " а остальные поля валидными данными")
    public void shouldNumberInFieldNameWithoutCredit() {
        choosePage.buyCard();
        var info = getNumberInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка картой при заполнении поля владелец латинским алфавитом" +
            " а остальные поля валидными данными")
    public void shouldEnglishNameInFieldNameWithoutCredit() {
        choosePage.buyCard();
        var info = getEnglishNameInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка картой поле CVV пустое" +
            " а остальные поля валидными данными")
    public void shouldEmptyCVVInFieldCVVWithoutCredit() {
        choosePage.buyCard();
        var info = getEmptyCVVInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка картой поле CVV одним числом" +
            " а остальные поля валидными данными")
    public void shouldOneNumberInFieldCVVWithoutCredit() {
        choosePage.buyCard();
        var info = getOneNumberInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка картой поле CVV двумя числами" +
            " а остальные поля валидными данными")
    public void shouldTwoNumberInFieldCVVWithoutCredit() {
        choosePage.buyCard();
        var info = getOTwoNumberInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }


    @Test
    @DisplayName("Покупка в кредит без заполнения поля карты а остальные поля валидными данными")
    public void shouldEmptyFieldCardWithCredit() {
        choosePage.buyWithCredit();
        var info = getEmptyCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка в кредит при заполнения поля карты одной цифрой а остальные поля валидными данными")
    public void shouldOneNumberInFieldCardNumberWithCredit() {
        choosePage.buyWithCredit();
        var info = getOneNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка в кредит при заполнения поля карты 15 цифрами а остальные поля валидными данными")
    public void shouldFifteenNumberInFieldCardNumberWithCredit() {
        choosePage.buyWithCredit();
        var info = getFifteenNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка в кредит неизвестной картой при заполнения поля карты а остальные поля валидными данными")
    public void shouldUnknownCardInFieldCardNumberWithCredit() {
        choosePage.buyWithCredit();
        var info = getFakerNumberCardNumber();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFakerCardNumber();
    }

    @Test
    @DisplayName("Покупка в кредит без заполнения поля месяц а остальные поля валидными данными")
    public void shouldEmptyFieldMonthWithCredit() {
        choosePage.buyWithCredit();
        var info = getEmptyMonth();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит c заполнением поля месяц одной цифрой а остальные поля валидными данными")
    public void shouldOneNumberInFieldMonthWithCredit() {
        choosePage.buyWithCredit();
        var info = getOneNumberMonth();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит в поле месяц предыдущий от текущего а остальные поля валидными данными")
    public void shouldFieldWithPreviousMonthWithCredit() {
        choosePage.buyWithCredit();
        var info = getPreviousMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит в поле месяц в верном формате вести нулевой (не существующий) месяц" +
            " а остальные поля валидными данными")
    public void shouldFieldWithZeroMonthWithCredit() {
        choosePage.buyWithCredit();
        var info = getZeroMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит в поле месяц в верном формате вести тринадцатый (не существующий) месяц" +
            " а остальные поля валидными данными")
    public void shouldFieldWithThirteenMonthWithCredit() {
        choosePage.buyWithCredit();
        var info = getThirteenMonthInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка в кредит без заполнения поля год а остальные поля валидными данными")
    public void shouldEmptyFieldYearWithCredit() {
        choosePage.buyWithCredit();
        var info = getEmptyYear();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка в кредит, заполнение поля год, предыдущим годом от текущего" +
            " а остальные поля валидными данными")
    public void shouldPreviousYearFieldYearWithCredit() {
        choosePage.buyWithCredit();
        var info = getPreviousYearInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка в кредит, заполнение поля год, на шесть лет больше чем текущий" +
            " а остальные поля валидными данными")
    public void shouldPlusSixYearFieldYearWithCredit() {
        choosePage.buyWithCredit();
        var info = getPlusSixYearInField();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка в кредит, при пустом поле владелец а остальные поля валидными данными")
    public void shouldEmptyFieldNameWithCredit() {
        choosePage.buyWithCredit();
        var info = getApprovedCard();
        paymentPage.sendingEmptyNameValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит, при заполнении поля владелец пробелом а остальные поля валидными данными")
    public void shouldSpaceFieldNameWithCredit() {
        choosePage.buyWithCredit();
        var info = getSpaceName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит, при заполнении поля владелец спец. символами" +
            " а остальные поля валидными данными")
    public void shouldSpecialSymbolInFieldNameWithCredit() {
        choosePage.buyWithCredit();
        var info = getSpecialSymbolInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит, при заполнении поля владелец цифрами" +
            " а остальные поля валидными данными")
    public void shouldNumberInFieldNameWithCredit() {
        choosePage.buyWithCredit();
        var info = getNumberInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит, при заполнении поля владелец латинским алфавитом" +
            " а остальные поля валидными данными")
    public void shouldEnglishNameInFieldNameWithCredit() {
        choosePage.buyWithCredit();
        var info = getEnglishNameInFieldName();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка в кредит, поле CVV пустое" +
            " а остальные поля валидными данными")
    public void shouldEmptyCVVInFieldCVVWithCredit() {
        choosePage.buyWithCredit();
        var info = getEmptyCVVInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка в кредит, поле CVV одним числом" +
            " а остальные поля валидными данными")
    public void shouldOneNumberInFieldCVVWithCredit() {
        choosePage.buyWithCredit();
        var info = getOneNumberInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка в кредит, поле CVV двумя числами" +
            " а остальные поля валидными данными")
    public void shouldTwoNumberInFieldCVVWithCredit() {
        choosePage.buyWithCredit();
        var info = getOTwoNumberInFieldCVV();
        paymentPage.sendingValidData(info);
        paymentPage.sendingValidDataWithFieldCVVError();
    }


}
