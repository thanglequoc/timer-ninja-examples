package io.github.thanglequoc.ninja_coffee_shop_gradle.dto;

public class PaymentRequest {
    private PaymentChannel paymentChannel;
    private int amount;
    private String orderId;
    private String cardNumber;
    private String cardHolderName;

    public PaymentChannel getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(PaymentChannel paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
