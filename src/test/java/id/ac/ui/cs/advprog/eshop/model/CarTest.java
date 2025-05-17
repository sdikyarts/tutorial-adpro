package test.java.id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    Car car;

    @BeforeEach
    void setUp() {
        this.car = new Car();
        this.car.setCarId("car-123");
        this.car.setCarName("Toyota Camry");
        this.car.setCarQuantity(3);
    }

    @Test
    void testGetCarId() {
        assertEquals("car-123", this.car.getCarId());
    }

    @Test
    void testGetCarName() {
        assertEquals("Toyota Camry", this.car.getCarName());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(3, this.car.getCarQuantity());
    }
}
