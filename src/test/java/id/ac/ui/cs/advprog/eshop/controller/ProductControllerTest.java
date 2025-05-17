package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    @Mock
    private ProductService service;
    @Mock
    private CarService carservice;
    @Mock
    private Model model;
    private ProductController productController;
    private MockMvc mockMvc;
    private Product product;
    private Car car;

    @BeforeEach
    void setUp() {
        productController = new ProductController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        car = new Car();
        car.setCarId("car-123");
        car.setCarName("Toyota Camry");
        car.setCarQuantity(3);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("createProduct", viewName);
    }

    @Test
    void testCreateProductPost() {
        String viewName = productController.createProductPost(product, model);
        verify(service).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(service.findAll()).thenReturn(products);
        String viewName = productController.productListPage(model);
        verify(service).findAll();
        verify(model).addAttribute("products", products);
        assertEquals("productList", viewName);
    }

    @Test
    void testEditProductPage() {
        when(service.findById(product.getProductId())).thenReturn(product);
        String viewName = productController.editProductPage(product.getProductId(), model);
        verify(service).findById(product.getProductId());
        verify(model).addAttribute("product", product);
        assertEquals("editProduct", viewName);
    }

    @Test
    void testEditProductPost() {
        String viewName = productController.editProductPost(product, model);
        verify(service).update(product.getProductId(), product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct(product.getProductId());
        verify(service).deleteProductById(product.getProductId());
        assertEquals("redirect:list", viewName);
    }

    // CarController tests
    @Test
    void testCreateCarPage() {
        CarController carController = new CarController(service, carservice);
        String viewName = carController.createCarPage(model);
        verify(model).addAttribute(eq("car"), any(Car.class));
        assertEquals("createCar", viewName);
    }

    @Test
    void testCreateCarPost() {
        CarController carController = new CarController(service, carservice);
        String viewName = carController.createCarPost(car, model);
        verify(carservice).create(car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testCarListPage() {
        CarController carController = new CarController(service, carservice);
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        when(carservice.findAll()).thenReturn(cars);
        String viewName = carController.carListPage(model);
        verify(carservice).findAll();
        verify(model).addAttribute("cars", cars);
        assertEquals("carList", viewName);
    }

    @Test
    void testEditCarPage() {
        CarController carController = new CarController(service, carservice);
        when(carservice.findById(car.getCarId())).thenReturn(car);
        String viewName = carController.editCarPage(car.getCarId(), model);
        verify(carservice).findById(car.getCarId());
        verify(model).addAttribute("car", car);
        assertEquals("editCar", viewName);
    }

    @Test
    void testEditCarPost() {
        CarController carController = new CarController(service, carservice);
        String viewName = carController.editCarPost(car, model);
        verify(carservice).update(car.getCarId(), car);
        assertEquals("redirect:listCar", viewName);
    }

    @Test
    void testDeleteCar() {
        CarController carController = new CarController(service, carservice);
        String viewName = carController.deleteCar(car.getCarId());
        verify(carservice).deleteCarById(car.getCarId());
        assertEquals("redirect:listCar", viewName);
    }
}
