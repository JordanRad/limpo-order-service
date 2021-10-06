package limpo.orderservice.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrdersPage {

    private List<Order> orders;

    private int from;

    private int to;

    private int page;

    private int total;

}
