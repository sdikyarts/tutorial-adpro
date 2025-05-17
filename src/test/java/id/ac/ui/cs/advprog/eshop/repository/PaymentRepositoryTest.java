package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        Map<String, String> data1 = Map.of("voucherCode", "ESHOP1234ABC5678");
        payment1 = new Payment("pay1", "VOUCHER", "SUCCESS", data1);

        Map<String, String> data2 = Map.of("bankName", "BNI", "referenceCode", "REF123456");
        payment2 = new Payment("pay2", "BANK_TRANSFER", "PENDING", data2);
    }

    @Test
    void testSaveAndRetrievePayment_HappyPath() {
        paymentRepository.save(payment1);
        Payment found = paymentRepository.findById("pay1");
        assertNotNull(found);
        assertEquals("pay1", found.getId());
        assertEquals("VOUCHER", found.getMethod());
    }

    @Test
    void testSaveDuplicatePayment_UnhappyPath() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment1); // try to save again
        List<Payment> all = paymentRepository.findAll();
        assertEquals(1, all.size(), "Duplicate payment should not be added");
    }

    @Test
    void testFindById_NotFound_UnhappyPath() {
        Payment found = paymentRepository.findById("not_exist");
        assertNull(found, "Should return null if payment not found");
    }

    @Test
    void testFindAllPayments_HappyPath() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);
        List<Payment> all = paymentRepository.findAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(payment1));
        assertTrue(all.contains(payment2));
    }

    @Test
    void testFindAllPayments_Empty_UnhappyPath() {
        List<Payment> all = paymentRepository.findAll();
        assertTrue(all.isEmpty(), "Should return empty list if no payments");
    }
}
