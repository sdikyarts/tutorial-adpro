# Advanced Programming
#### Nama: Yasmine Putri V.
#### NPM: 2206081862

<details>
<summary><h2>Module 1 - Coding Standards</h2></summary>

<details>
<summary><h3>Reflection 1</h3></summary>

In implementing the edit and delete features for the E-Shop application, I have applied several clean code principles and secure coding practices:

<details>
<summary><h4>Clean Code Principles Applied</h4></summary>

1. **Meaningful Names**
   - Variables and methods are named descriptively (e.g., `productService`, `createProduct`, `editProduct`)
   - Proper naming conventions are followed for clarity (e.g., camelCase for methods like `findAll()`, `editProductPage()`)
   - Examples from code:
     ```java
     public class ProductController {
         private ProductService productService;
         public String createProductPage(Model model) { ... }
     }
     ```

2. **Functions and Methods**
   - Functions and methods are small and focused (e.g., separate methods for create, edit, delete)
   - Each method has a single responsibility (e.g., `create()` only handles product creation)
   - Examples from code:
     ```java
     public class ProductServiceImpl {
         public Product create(Product product) {
             productRepository.create(product);
             return product;
         }

         public void delete(String id) {
             productRepository.delete(id);
         }
     }
     ```

3. **Comments**
   - Code is self-documenting through clear method and variable names
   - Spring annotations provide clear intent (e.g., `@Controller`, `@Service`, `@Repository`)
   - Example:
     ```java
     @Controller
     @RequestMapping("/product")
     public class ProductController { ... }
     ```

4. **Objects and Data Structures**
   - Proper use of interfaces (ProductService) and implementations (ProductServiceImpl)
   - Clear separation of concerns between Repository, Service, and Controller
   - Example:
     ```java
     public interface ProductService {
         public Product create(Product product);
         public List<Product> findAll();
     }
     ```

5. **Error Handling**
   - Null checks in repository methods
   - Proper return types for operations that might fail
   - Example:
     ```java
     public Product findById(String id) {
         for (Product product : productData) {
             if (product.getProductId().equals(id)) {
                 return product;
             }
         }
         return null;
     }
     ```
</details>

<details>
<summary><h4>Secure Coding Practices</h4></summary>

1. **Input Validation**
   - We validate user input to prevent SQL injection, used in Repository, Service, and Controller
   - Example:
     ```java
     @PostMapping("/create")
     public String createProduct(@ModelAttribute("product") Product product, Model model) {
         productService.create(product);
         return "redirect:/product";
     }
     ```
2. **Output Encoding**
   - We encode user input to prevent XSS attacks, used in Controller
   - Example:
     ```java
     @GetMapping("/edit/{id}")
     public String editProductPage(@PathVariable("id") String id, Model model) {
         Product product = productService.findById(id);
         model.addAttribute("product", product);
         return "editProduct";
     }
     ```
</details>

<details>
<summary><h4>Areas for Improvement</h4></summary>

1. **Error Handling**
   - Implement more robust error handling for cases like product not found
   - Add proper validation messages for user input
</details>

<details>
<summary><h3>Reflection 2</h3></summary>

1. After Writing Unit Tests
The process ensures better code reliability by catching issues early. How many unit tests per class? It depends on the class's complexity. Aim for at least one test per method or major functionality, covering edge cases, typical cases, and failure scenarios. How to ensure tests are enough? Use code coverage as a metric. It measures the percentage of source code executed during testing (e.g., lines, branches, or methods). Tools like JaCoCo or Clover can help. Aim for high coverage (e.g., 80%+), but focus on meaningful tests, not just hitting a number. Does 100% code coverage mean no bugs? No. 100% coverage means all code paths were executed, but it doesn’t guarantee correct behavior, edge cases, or integration issues. Bugs can still exist due to logic errors or untested scenarios.
2. Creating CreateProductFunctionalTest.java and a New Functional Test Suite
Cleanliness of the new functional test suite: Reusing the same setup procedures and instance variables as in HomePageFunctionalTest.java (e.g., serverPort, testBaseUrl, setupTest) introduces potential issues.
Will it reduce code quality? Yes, due to code duplication. Copy-pasting setup logic across test classes violates the DRY (Don’t Repeat Yourself) principle, making the codebase harder to maintain.
Potential clean code issues:
Duplication: Repeated setup code in multiple test classes increases maintenance effort. If the setup logic changes, you’d need to update all classes.
Scalability: Adding more test suites with the same approach will bloat the codebase with redundant code.
Readability: Duplicated code makes it harder to understand the unique purpose of each test class.
Improvements:
Extract a Base Test Class: Create an abstract base class (e.g., BaseFunctionalTest) with shared setup logic, instance variables (serverPort, testBaseUrl), and the @BeforeEach method. Both HomePageFunctionalTest and the new test suite can extend this class.
New Test Suite: The new class (e.g., ProductListFunctionalTest) can extend BaseFunctionalTest and focus only on verifying the product list count, avoiding duplication.

</details>

</details>
</details>
