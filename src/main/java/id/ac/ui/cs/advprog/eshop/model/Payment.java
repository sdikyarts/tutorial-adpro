package id.ac.ui.cs.advprog.eshop.model;

import java.util.List;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import enums.OrderStatus;

@Builder
@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {}

    public Payment(String id, String method, String status, Map<String, String> paymentData) {}
}
