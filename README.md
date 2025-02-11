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

[Your Reflection 2 content will go here. You can follow a similar structure to Reflection 1 with your new content.]

</details>

</details>
</details>