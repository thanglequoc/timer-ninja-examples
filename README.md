# Timer Ninja project examples
Example projects integrated with [Timer Ninja library](https://github.com/thanglequoc/timer-ninja) for your setup reference. Please go into each directory to find the instruction how to run.

At the end of the guide, you will be able to see the execution time it took for every method annotated with `@TimerNinjaTracker` annotation.

```shell
2026-01-30T22:34:48.770+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          : Timer Ninja trace context id: 1237dd33-587e-451b-9442-139755fdbe13 | Trace timestamp: 2026-01-30T15:34:44.763Z
2026-01-30T22:34:48.770+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          : {===== Start of trace context id: 1237dd33-587e-451b-9442-139755fdbe13 =====}
2026-01-30T22:34:48.771+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          : public Beverage makeDrink() - 4007 ms
2026-01-30T22:34:48.771+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          :   |-- public Beverage makeDrink(OrderRequest order) - Args: [order={OrderRequest{orderID='ORDER-0', itemId=1, isHot=false, sweetness=3, size='s', customerName='thanglequoc'}}] - 4007 ms
2026-01-30T22:34:48.771+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          :     |-- public Beverage getDrinkType(int id) - 0 ms
2026-01-30T22:34:48.771+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          :     |-- public void makeIce() - 2001 ms
2026-01-30T22:34:48.771+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          :     |-- public void consumeIceServings(int amount) - Args: [amount={1}] - 0 ms
2026-01-30T22:34:48.771+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          :     |-- public void grindBeans() - 2005 ms
2026-01-30T22:34:48.771+07:00  INFO 74425 --- [ninja-coffee-shop-gradle] [nio-8080-exec-3] i.g.t.timerninja.TimerNinjaUtil          : {====== End of trace context id: 1237dd33-587e-451b-9442-139755fdbe13 ======}
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
