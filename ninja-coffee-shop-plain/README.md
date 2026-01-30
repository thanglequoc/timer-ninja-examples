# Ninja Coffee Shop Plain Java Example

This is a plain Java implementation of the Ninja Coffee Shop project, integrated with the [Timer Ninja library](https://github.com/ThangLeQuoc/timer-ninja) to track method execution times.

## How to Run

1. **Build the project**

   Navigate to the project directory:
   ```sh
   cd ninja-coffee-shop-plain
   ```
   Build the project using Maven:
   ```sh
   ./mvnw clean package
   ```

2. **Start the application**

   Run the application (adjust the main class if needed):
   ```sh
   java -cp target/classes io.github.thanglequoc.ninja_coffee_shop_plain.NinjaCoffeeShopPlainApplication
   ```
   The server will start on port 8080 by default.

## How to Use (API Endpoints)

After starting the project, you can use the following API endpoints (see also the Postman collection in `resource/Timer-Ninja-Coffeeshop.postman_collection.json`):

### 1. Place Order
- **Endpoint:** `POST http://localhost:8080/order`
- **Body:**
  ```json
  {
    "itemId": 2,
    "isHot": false,
    "sweetness": 3,
    "size": "s",
    "customerName": "thanglequoc"
  }
  ```

### 2. Brew Coffee
- **Endpoint:** `POST http://localhost:8080/brew`

### 3. Pay
- **Endpoint:** `POST http://localhost:8080/pay`
- **Body:**
  ```json
  {
    "orderId": "ORDER-0",
    "paymentChannel": "CASH",
    "amount": 12
  }
  ```

### 4. Check Material
- **Endpoint:** `GET http://localhost:8080/checkMaterial`

### 5. Check Revenue
- **Endpoint:** `GET http://localhost:8080/checkRevenue`

You can use Postman or any HTTP client to interact with these endpoints. The recommended flow is:

1. Place Order
2. Brew Coffee
3. Pay

Or, you can check material and revenue at any time.

---

For more details, see the Postman collection: `resource/Timer-Ninja-Coffeeshop.postman_collection.json`.
