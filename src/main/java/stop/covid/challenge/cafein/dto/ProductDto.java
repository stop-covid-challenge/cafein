package stop.covid.challenge.cafein.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import stop.covid.challenge.cafein.domain.model.Product;

@Getter
@NoArgsConstructor
public class ProductDto {
    private String title;
    private String productImage;
    private int price;
    private int amount;

    public Product toEntity() {
        return Product.builder()
            .title(title)
            .productImage(productImage)
            .price(price)
            .amount(amount)
            .build();
    }
}
