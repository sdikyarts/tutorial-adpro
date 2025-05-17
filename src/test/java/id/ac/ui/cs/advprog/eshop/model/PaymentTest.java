package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class PaymentTest {
    private List<Order> orders;
    private Payment payment;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        this.orders = new ArrayList<>();
        this.paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment("pay123", "VOUCHER", "PENDING", paymentData);
    }

    @Test
    void testConstructorAndGetters() {
        assertEquals("pay123", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testSetStatus() {
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetMethod() {
        payment.setMethod("BANK_TRANSFER");
        assertEquals("BANK_TRANSFER", payment.getMethod());
    }

    @Test
    void testSetPaymentData() {
        Map<String, String> newData = new HashMap<>();
        newData.put("bankName", "BNI");
        newData.put("referenceCode", "REF123456");
        payment.setPaymentData(newData);
        assertEquals("BNI", payment.getPaymentData().get("bankName"));
        assertEquals("REF123456", payment.getPaymentData().get("referenceCode"));
    }
}
