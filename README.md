# Timer Ninja project examples
Example projects integrated with [Timer Ninja library](https://github.com/thanglequoc/timer-ninja) for your setup reference. Please go into each directory to find the instruction how to run.

At the end of the guide, you will be able to see the execution time it took for every method annotated with `@TimerNinjaTracker` annotation.

```shell
2023-04-07T14:24:17.158+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          : Timer Ninja trace context id: f98a88d7-78ea-4a44-9fef-b5756ae10ba8
2023-04-07T14:24:17.161+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          : Trace timestamp: 2023-04-07T07:24:16.335Z
2023-04-07T14:24:17.161+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          : {===== Start of trace context id: f98a88d7-78ea-4a44-9fef-b5756ae10ba8 =====}
2023-04-07T14:24:17.165+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          : public User createNewUser(User user) - 820 ms
2023-04-07T14:24:17.165+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          :   |-- private void validateUserAlreadyExist(String username) - 507000 µs
2023-04-07T14:24:17.165+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          :     |-- public User getUserByName(String username) - 0 ms
2023-04-07T14:24:17.165+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          :   |-- private static boolean isValidUserName(String username) - 9 ms
2023-04-07T14:24:17.165+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          :   |-- public User createUser(User user) - 302 ms
2023-04-07T14:24:17.166+07:00  INFO 28903 --- [nio-8080-exec-2] c.g.t.timerninja.TimerNinjaUtil          : {====== End of trace context id: f98a88d7-78ea-4a44-9fef-b5756ae10ba8 ======}
```



## List of example
- [Ninja Coffee Shop Plain Java](ninja-coffee-shop-plain): Plain Java implementation using Maven, demonstrates core Timer Ninja usage without Spring Boot.
- [Ninja Coffee Shop Gradle](ninja-coffee-shop-gradle): Java project using Gradle, similar to the plain version but with Gradle build system.
- [Ninja Coffee Shop Maven](ninja-coffee-shop-maven): Java project using Maven, structured for Maven builds and dependency management.
- [Plain Java with Gradle build](timer-ninja-plain-java-gradle): Minimal plain Java example with Gradle

---

## Ninja Coffee Shop Example Usage

After starting any Ninja Coffee Shop project, you can use the following API endpoints (see also the Postman collection in `resource/Timer-Ninja-Coffeeshop.postman_collection.json`):

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

#### Recommended Flow
1. Place Order
2. Brew Coffee
3. Pay

Or, you can check material and revenue at any time.

---

For more details, see the Postman collection: `resource/Timer-Ninja-Coffeeshop.postman_collection.json`.
