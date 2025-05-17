package test.java.id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("car-123");
        car.setCarName("Toyota Camry");
        car.setCarQuantity(3);
    }

    @Test
    void testCreateCar() {
        when(carRepository.create(any(Car.class))).thenReturn(car);
        Car createdCar = carService.create(car);
        verify(carRepository, times(1)).create(car);
        assertEquals(car.getCarId(), createdCar.getCarId());
        assertEquals(car.getCarName(), createdCar.getCarName());
        assertEquals(car.getCarQuantity(), createdCar.getCarQuantity());
    }

    @Test
    void testFindAllCars() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> carIterator = carList.iterator();
        when(carRepository.findAll()).thenReturn(carIterator);
        List<Car> foundCars = carService.findAll();
        verify(carRepository, times(1)).findAll();
        assertEquals(1, foundCars.size());
        assertEquals(car.getCarId(), foundCars.get(0).getCarId());
    }

    @Test
    void testUpdateCar() {
        when(carRepository.update(eq(car.getCarId()), any(Car.class))).thenReturn(car);
        carService.update(car.getCarId(), car);
        verify(carRepository, times(1)).update(car.getCarId(), car);
    }

    @Test
    void testDeleteCarById() {
        doNothing().when(carRepository).delete(car.getCarId());
        carService.deleteCarById(car.getCarId());
        verify(carRepository, times(1)).delete(car.getCarId());
    }
}
