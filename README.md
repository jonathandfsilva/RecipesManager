# Java API Recipes Manager

This document provides detailed information on how to use the endpoints of a Java API implemented with the following REST Controller.

## RecipesController

The `RecipesController` class is a REST controller responsible for handling recipe-related operations. It provides endpoints to perform CRUD (Create, Read, Update, Delete) operations on recipes.

### Prerequisites

Before using the endpoints, ensure that you have the following prerequisites in place:

- Java Development Kit (JDK) 17 installed
- IDE (Integrated Development Environment) installed (e.g., IntelliJ, Eclipse)
- Maven build tool installed
- Database setup and connection details

### Endpoint Details

The following are the available endpoints provided by the `RecipesController`:

#### 1. Get Recipe by ID

- Endpoint: `GET /recipes/{id}`
- Description: Retrieves a recipe by its ID.
- Request Parameters:
    - `id` (path variable): The ID of the recipe to retrieve.
- Response:
    - HTTP Status: `200 OK` if the recipe is found.
    - JSON object representing the recipe.
- Example:
    - `GET /recipes/1`

#### 2. Create a Recipe

- Endpoint: `POST /recipes`
- Description: Creates a new recipe.
- Request Body:
    - JSON object representing the recipe to be created.
- Response:
    - HTTP Status: `200 OK` if the recipe is created successfully.
    - JSON object representing the created recipe.
- Example:
    - `POST /recipes`
    - Request Body:
      ```json
      {
          "title": "Chocolate Cake",
          "vegetarian": true,
          "servings": 8,
          "ingredients": ["flour", "sugar", "cocoa powder", "eggs", "butter"],
          "instructions": "1. Mix all ingredients..."
      }
      ```

#### 3. Update a Recipe

- Endpoint: `PUT /recipes/{id}`
- Description: Updates an existing recipe.
- Request Parameters:
    - `id` (path variable): The ID of the recipe to update.
- Request Body:
    - JSON object representing the updated recipe.
- Response:
    - HTTP Status: `200 OK` if the recipe is updated successfully.
    - JSON object representing the updated recipe.
- Example:
    - `PUT /recipes/1`
    - Request Body:
      ```json
      {
          "title": "Updated Chocolate Cake",
          "vegetarian": true,
          "servings": 10,
          "ingredients": ["flour", "sugar", "cocoa powder", "eggs", "butter"],
          "instructions": "1. Mix all ingredients..."
      }
      ```

#### 4. Delete a Recipe

- Endpoint: `DELETE /recipes/{id}`
- Description: Deletes a recipe by its ID.
- Request Parameters:
    - `id` (path variable): The ID of the recipe to delete.
- Response:
    - HTTP Status: `204 No Content` if the recipe is deleted successfully.
- Example:
    - `DELETE /recipes/1`

#### 5. Filter Recipes

- Endpoint: `GET /recipes/filter`
- Description: Filters recipes based on specific criteria.
- Request Parameters:
    - `page` (query parameter): The page number (starting from 0) for pagination.
    - `size` (query parameter): The number of recipes per page.
- Request Body:
    - JSON object containing filtering criteria.
- Response:
    - HTTP Status: `200 OK` if there are matching recipes.
    - JSON object containing the filtered recipes.
- Example:
    - `GET /recipes

/filter?page=0&size=10`
- Request Body:
  ```json
  {
      "title": "chocolate",
      "vegetarian": true,
      "servings": 4,
      "ingredients": ["cocoa powder", "sugar"],
      "instructions": ["Mix all ingredients...", "Bake for 30 minutes..."]
  }
  ```

### Setting Up and Running the API

To set up and run the API locally, follow these steps:

1. Clone the repository or download the source code.
2. Open the project in your preferred IDE.
3. Build the project using Maven or Gradle.
4. Configure the database connection details in the repository (if required).
5. Run the application from the IDE or using the command-line interface.
6. The API will be accessible at `http://localhost:8080/recipes`.

### Error Handling

The API handles errors in the following scenarios:

- If a recipe with the specified ID is not found, the API returns a `404 Not Found` status.
- If an invalid request is made (e.g., missing required fields), the API returns a `400 Bad Request` status.
- If an internal server error occurs, the API returns a `500 Internal Server Error` status.

### Dependencies

The `RecipesController` relies on the following dependencies:

- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate Validator

Make sure to include these dependencies in your project's `pom.xml` or `build.gradle` file.

### Conclusion

This readme provides an overview of the endpoints available in the `RecipesController` of the Java API. By following the instructions and examples provided, you can use these endpoints to perform various operations on recipes.