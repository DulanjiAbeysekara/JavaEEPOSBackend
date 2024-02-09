package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class ItemDTO {
    private String code;
    private String description;
    private int qtyOnHand;
    private Double unitPrice;
}
