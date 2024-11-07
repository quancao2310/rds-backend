package com.example.regionaldelicacy.enums;

public enum PaymentMethod {
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    PAYPAL("PAYPAL"),
    CASH_ON_DELIVERY("CASH_ON_DELIVERY");

    private String paymentMethod;

    PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
}
