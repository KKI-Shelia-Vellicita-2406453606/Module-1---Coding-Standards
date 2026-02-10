## Reflection 1

In implementing eshop's product management features, I focused on several Clean Code principles to ensure long-term maintainability. I used meaningful, intention-revealing names for methods such as findById and delete to ensure the code remains readable for other developers. I also applied the Single Responsibility Principle by separating the logic into four distinct packages: controller, model, repository, and service. In this structure:
- The Model layer represents the problem domain and data.
- The Controller manages HTTP requests/responses and forwards tasks to the service layer.
- The Service layer implements the core business logic.
- The Repository layer handles data operations, currently using an ArrayList to simulate a database.

Additionally, I strictly adhered to the Model-View-Controller (MVC) pattern to maintain a clean separation of concerns. By placing the data structure in the model package, I ensured that the problem domain remains isolated from the application logic. I applied Lombok's @Getter and @Setter annotations to the Product class, which significantly reduced boilerplate code and improved scannability.

Regarding Secure Coding, I utilized Spring's @PathVariable and @ModelAttribute to safely map user-provided data from URLs and forms directly into the model. This approach is more secure than manual string parsing, as it leverages Spring's built-in handling of request parameters. However, I identified a potential area for improvement: implementing input validation in the Product model to prevent invalid data, such as negative quantities or empty names, from being saved. Furthermore, by implementing the Edit and Delete features on separate Git branches, I maintained a clean and traceable project history, which is essential for collaborative development and fulfills the module's version control requirements.
