package ru.netology.Data;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class DataHelper {
    private static Faker fakerEn = new Faker(new Locale("en"));
    private static Faker fakerRu = new Faker(new Locale("ru"));

    private static String approvedCard = "4444 4444 4444 4441";
    private static String declinedCard = "4444 4444 4444 4442";

    private DataHelper() {
    }



    private static String getValidMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getPreviousMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    private static String getZeroMonth() {
        return "00";
    }

    private static String getThirteenMonth() {
        return "13";
    }

    private static String getValidYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getValidYearPlusOne() {
        return LocalDate.now().plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }
    private static String getPreviousYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getPlusSixYear() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }

    private static String getName() {
        return fakerRu.name().lastName() + " " + fakerRu.name().firstName();
    }

    private static String getNameEn() {
        return fakerEn.name().lastName() + " " + fakerEn.name().firstName();
    }

    private static String getCVV() { return fakerEn.numerify("###"); }

    private static String getTwoNumber() {
        return fakerEn.numerify("##");
    }

    private static String getOneNumber() {
        return fakerEn.numerify("#");
    }

    private static String getFakerNumberCard() {
        return fakerRu.business().creditCardNumber();
    }

    private static String getFifteenNumber() {
        return approvedCard.substring(0,18);
    }

    private static String getEmptyField() { return " "; }

    private static String getSpecialSymbol() {
        return "@#$%^&*()~-+/*?><|";
    }

    public static CardInfo getApprovedCard() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getDeclinedCard() {
        return new CardInfo(declinedCard, getValidMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getEmptyCardNumber() {
        return new CardInfo(getEmptyField(), getValidMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getOneNumberCardNumber() {
        return new CardInfo(getOneNumber(), getValidMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getFifteenNumberCardNumber() {
        return new CardInfo(getFifteenNumber(), getValidMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getFakerNumberCardNumber() {
        return new CardInfo(getFakerNumberCard(), getValidMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getEmptyMonth() {
        return new CardInfo(approvedCard, getEmptyField(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getOneNumberMonth() {
        return new CardInfo(approvedCard, getOneNumber(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getPreviousMonthInField() {
        return new CardInfo(approvedCard, getPreviousMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getZeroMonthInField() {
        return new CardInfo(approvedCard, getZeroMonth(), getValidYearPlusOne(), getName(), getCVV());
    }

    public static CardInfo getThirteenMonthInField() {
        return new CardInfo(approvedCard, getThirteenMonth(), getValidYear(), getName(), getCVV());
    }

    public static CardInfo getEmptyYear() {
        return new CardInfo(approvedCard, getValidMonth(), getEmptyField(), getName(), getCVV());
    }

    public static CardInfo getPreviousYearInField() {
        return new CardInfo(approvedCard, getValidMonth(), getPreviousYear(), getName(), getCVV());
    }

    public static CardInfo getPlusSixYearInField() {
        return new CardInfo(approvedCard, getValidMonth(), getPlusSixYear(), getName(), getCVV());
    }

    public static CardInfo getSpaceName() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getEmptyField(), getCVV());
    }

    public static CardInfo getSpecialSymbolInFieldName() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getSpecialSymbol(), getCVV());
    }

    public static CardInfo getNumberInFieldName() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getCVV(), getCVV());
    }

    public static CardInfo getEnglishNameInFieldName() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getNameEn(), getCVV());
    }

    public static CardInfo getEmptyCVVInFieldCVV() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getName(), getEmptyField());
    }

    public static CardInfo getOneNumberInFieldCVV() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getName(), getOneNumber());
    }

    public static CardInfo getOTwoNumberInFieldCVV() {
        return new CardInfo(approvedCard, getValidMonth(), getValidYear(), getName(), getTwoNumber());
    }

    @Value
    public static class CardInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String name;
        private String cvv;
    }


}
