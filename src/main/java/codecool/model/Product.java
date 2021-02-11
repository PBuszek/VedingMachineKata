package codecool.model;

import java.math.BigDecimal;

public enum Product {
    COLA("1.0"),
    CHIPS("0.5"),
    CANDY("0.65"),
    NOTHING("-1.0");


    private final BigDecimal price;

    Product(String price) {
        this.price = new BigDecimal(price);
    }

    public BigDecimal getPrice() {
        return price;
    }
}
