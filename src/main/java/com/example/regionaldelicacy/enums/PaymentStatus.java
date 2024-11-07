package com.example.regionaldelicacy.enums;

public enum PaymentStatus {
    PENDING("PENDING"),
    PAID("PAID");

    private String paymentStatus;

    PaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
