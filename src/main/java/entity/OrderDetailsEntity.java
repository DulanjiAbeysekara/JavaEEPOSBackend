package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDetailsEntity {
    private String oid;
    private String itemCode;
    private int qty;
    private Double unitPrice;
}
