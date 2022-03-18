package ru.netology.Data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.DriverManager;


public class BaseGenerator {

    public BaseGenerator() {
    }

    @Data
    @RequiredArgsConstructor
    public class PaymentEntityInfo {
        private String id;
        private String amount;
        private String created;
        private String status;
        private String transaction_id;
    }

    @Data
    @RequiredArgsConstructor
    public class OrderEntityInfo {
        private String id;
        private String created;
        private String credit_id;
        private String payment_id;
    }

    @Data
    @RequiredArgsConstructor
    public class CreditRequestEntityInfo {
        private String id;
        private String bank_id;
        private String created;
        private String status;

    }


    private static String datasource = System.getProperty("datasource");

    @SneakyThrows
    public static void cleanDatabase() {
        var runner = new QueryRunner();
        var deleteFromOrder = "DELETE FROM order_entity;";
        var deleteFromCredit = "DELETE FROM credit_request_entity;";
        var deleteFromPayment = "DELETE FROM payment_entity;";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            runner.update(connection, deleteFromOrder);
            runner.update(connection, deleteFromCredit);
            runner.update(connection, deleteFromPayment);
        }
    }

    @SneakyThrows
    public static PaymentEntityInfo getPaymentInfo() {
        var runner = new QueryRunner();
        var paymentInfo = "SELECT * FROM payment_entity WHERE created = (SELECT MAX(created) FROM payment_entity);";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            return runner.query(connection, paymentInfo, new BeanHandler<>(PaymentEntityInfo.class));
        }
    }

    @SneakyThrows
    public static CreditRequestEntityInfo getCreditRequestInfo() {
        var runner = new QueryRunner();
        var creditRequestInfo = "SELECT * FROM credit_request_entity WHERE created = (SELECT MAX(created) FROM credit_request_entity);";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            return runner.query(connection, creditRequestInfo, new BeanHandler<>(CreditRequestEntityInfo.class));
        }
    }

    @SneakyThrows
    public static OrderEntityInfo getOrderInfo() {
        var runner = new QueryRunner();
        var orderInfo = "SELECT * FROM order_entity WHERE created = (SELECT MAX(created) FROM order_entity);";

        try (var connection = DriverManager.getConnection(
                datasource, "app", "pass")) {
            return runner.query(connection, orderInfo, new BeanHandler<>(OrderEntityInfo.class));
        }
    }

}
