package test.java.id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    @InjectMocks
    CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarId("car-123");
        car.setCarName("Toyota Camry");
        car.setCarQuantity(3);
        carRepository.create(car);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car.getCarId(), savedCar.getCarId());
        assertEquals(car.getCarName(), savedCar.getCarName());
        assertEquals(car.getCarQuantity(), savedCar.getCarQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Car> carIterator = carRepository.findAll();
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneCar() {
        Car car1 = new Car();
        car1.setCarId("car-1");
        car1.setCarName("Toyota");
        car1.setCarQuantity(2);
        carRepository.create(car1);

        Car car2 = new Car();
        car2.setCarId("car-2");
        car2.setCarName("Honda");
        car2.setCarQuantity(4);
        carRepository.create(car2);

        Iterator<Car> carIterator = carRepository.findAll();
        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertEquals(car1.getCarId(), savedCar.getCarId());
        savedCar = carIterator.next();
        assertEquals(car2.getCarId(), savedCar.getCarId());
        assertFalse(carIterator.hasNext());
    }

    @Test
    void testFindById_existingCar() {
        Car car = new Car();
        car.setCarId("id1");
        car.setCarName("Car 1");
        car.setCarQuantity(10);
        carRepository.create(car);

        Car found = carRepository.findById("id1");
        assertNotNull(found);
        assertEquals("id1", found.getCarId());
    }

    @Test
    void testFindById_nonExistingCar() {
        Car found = carRepository.findById("nonexistent");
        assertNull(found);
    }

    @Test
    void testUpdate_existingCar() {
        Car car = new Car();
        car.setCarId("id2");
        car.setCarName("Car 2");
        car.setCarQuantity(20);
        carRepository.create(car);

        Car updated = new Car();
        updated.setCarName("Updated Name");
        updated.setCarQuantity(99);

        Car result = carRepository.update("id2", updated);
        assertNotNull(result);
        assertEquals("id2", result.getCarId());
        assertEquals("Updated Name", result.getCarName());
        assertEquals(99, result.getCarQuantity());
    }

    @Test
    void testUpdate_nonExistingCar() {
        Car updated = new Car();
        updated.setCarName("Doesn't Matter");
        updated.setCarQuantity(1);

        Car result = carRepository.update("nonexistent", updated);
        assertNull(result);
    }

    @Test
    void testDelete_existingCar() {
        Car car = new Car();
        car.setCarId("id3");
        car.setCarName("Car 3");
        car.setCarQuantity(30);
        carRepository.create(car);

        carRepository.delete("id3");
        assertNull(carRepository.findById("id3"));
    }

    @Test
    void testDelete_nonExistingCar() {
        carRepository.delete("nonexistent");
    }
}
