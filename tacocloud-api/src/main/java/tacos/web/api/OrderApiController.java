package tacos.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tacos.Order;
import tacos.data.OrderRepository;

@RestController
@RequestMapping(path = "/api/orders", produces = "application/json")
@CrossOrigin(origins = "*")
public class OrderApiController {

    private final OrderRepository orderRepo;

    @Autowired
    public OrderApiController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @GetMapping(produces = "application/json")
    public Iterable<Order> allOrders() {
        return orderRepo.findAll();
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Order postOrder(@RequestBody Order order) {
        return orderRepo.save(order);
    }
}
