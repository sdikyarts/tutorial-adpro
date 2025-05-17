package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import java.util.List;

public interface PaymentService {
    void addPaymentByVoucher(String orderId, String voucherCode);
    void addPaymentByBankTransfer(String orderId, String bankName, String referenceCode);
    Payment setStatus(String paymentId, String status);
    Payment getPayment(String paymentId);
    List<Payment> getAllPayments();
}
