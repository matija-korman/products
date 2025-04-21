Demo Spring Boot application for managing products and product reviews.

Starts application with H2 databases that stores data to ```./data``` folder.
Database data can be viewed by using H2 console at 'http://localhost:8080/h2-console/' with JDBC url ```jdbc:h2:file:./data/products```.

App has three endpoints:

- Create product
    ```
    curl --location 'http://localhost:8080/products?content-type=application' \
    --header 'Content-Type: application/json' \
    --data '{
        "code":"PROD00000000010",
        "name": "Some product",
        "priceEur": 10.00,
        "description": "Great new product"
    }'
    ```

- Get products with optional filters by code and name
    ```
    curl --location 'http://localhost:8080/products?code=PROD&name=Super&page=0&size=2'
    ```

- Get three most popular products based on average rating
    ```
    curl --location 'http://localhost:8080/products/popular'
    ```
