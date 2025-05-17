package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = mock(Order.class);
        when(order.getId()).thenReturn("order1");
    }

    @Test
    void testAddPaymentByVoucher_HappyPath() {
        Map<String, String> paymentData = Map.of("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay1", "VOUCHER", "SUCCESS", paymentData);

        when(paymentRepository.findById("pay1")).thenReturn(null);
        doNothing().when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(result);
        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentByVoucher_UnhappyPath_InvalidVoucher() {
        Map<String, String> paymentData = Map.of("voucherCode", "INVALIDCODE");
        Payment payment = new Payment("pay2", "VOUCHER", "REJECTED", paymentData);

        when(paymentRepository.findById("pay2")).thenReturn(null);
        doNothing().when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentByBankTransfer_HappyPath() {
        Map<String, String> paymentData = Map.of("bankName", "BNI", "referenceCode", "REF123456");
        Payment payment = new Payment("pay3", "BANK_TRANSFER", "PENDING", paymentData);

        when(paymentRepository.findById("pay3")).thenReturn(null);
        doNothing().when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testAddPaymentByBankTransfer_UnhappyPath_MissingData() {
        Map<String, String> paymentData = Map.of("bankName", "", "referenceCode", "");
        Payment payment = new Payment("pay4", "BANK_TRANSFER", "REJECTED", paymentData);

        when(paymentRepository.findById("pay4")).thenReturn(null);
        doNothing().when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", paymentData);

        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatus_HappyPath_Success() {
        Map<String, String> paymentData = Map.of("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay5", "VOUCHER", "PENDING", paymentData);

        when(paymentRepository.findById("pay5")).thenReturn(payment);
        doNothing().when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        assertEquals("SUCCESS", result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
    }

    @Test
    void testSetStatus_UnhappyPath_InvalidStatus() {
        Map<String, String> paymentData = Map.of("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay6", "VOUCHER", "PENDING", paymentData);

        when(paymentRepository.findById("pay6")).thenReturn(payment);

        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "INVALID_STATUS"));
    }

    @Test
    void testGetPayment_HappyPath() {
        Map<String, String> paymentData = Map.of("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment("pay7", "VOUCHER", "SUCCESS", paymentData);

        when(paymentRepository.findById("pay7")).thenReturn(payment);

        Payment result = paymentService.getPayment("pay7");
        assertNotNull(result);
        assertEquals("pay7", result.getId());
    }

    @Test
    void testGetPayment_UnhappyPath_NotFound() {
        when(paymentRepository.findById("not_exist")).thenReturn(null);

        Payment result = paymentService.getPayment("not_exist");
        assertNull(result);
    }

    @Test
    void testGetAllPayments_HappyPath() {
        List<Payment> payments = new ArrayList<>();
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> result = paymentService.getAllPayments();
        assertNotNull(result);
        assertEquals(payments, result);
    }
}
