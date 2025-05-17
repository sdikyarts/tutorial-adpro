package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = "pay" + System.nanoTime(); // or use UUID
        String status = "PENDING";

        if ("VOUCHER".equals(method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (isValidVoucher(voucherCode)) {
                status = "SUCCESS";
            } else {
                status = "REJECTED";
            }
        } else if ("BANK_TRANSFER".equals(method)) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");
            if (bankName == null || bankName.isEmpty() || referenceCode == null || referenceCode.isEmpty()) {
                status = "REJECTED";
            }
        }

        Payment payment = new Payment(paymentId, method, status, paymentData);
        paymentRepository.save(payment);
        return payment;
    }

    public Payment setStatus(String paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment == null) {
            throw new IllegalArgumentException("Payment not found");
        }
        if (!"SUCCESS".equals(status) && !"REJECTED".equals(status) && !"PENDING".equals(status)) {
            throw new IllegalArgumentException("Invalid status");
        }
        payment.setStatus(status);
        paymentRepository.save(payment);
        return payment;
    }

    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private boolean isValidVoucher(String code) {
        if (code == null) return false;
        if (code.length() != 16) return false;
        if (!code.startsWith("ESHOP")) return false;
        int digitCount = 0;
        for (char c : code.toCharArray()) {
            if (Character.isDigit(c)) digitCount++;
        }
        return digitCount == 8;
    }

    @Override
    public void addPaymentByVoucher(String orderId, String voucherCode) {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", voucherCode);
        Order order = new Order(orderId, new ArrayList<>(), System.currentTimeMillis(), "dummyAuthor");
        addPayment(order, "VOUCHER", paymentData);
    }

    @Override
    public void addPaymentByBankTransfer(String orderId, String bankName, String referenceCode) {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("bankName", bankName);
        paymentData.put("referenceCode", referenceCode);
        Order order = new Order(orderId, new ArrayList<>(), System.currentTimeMillis(), "dummyAuthor");
        addPayment(order, "BANK_TRANSFER", paymentData);
    }
}
