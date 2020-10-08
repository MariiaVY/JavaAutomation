package io.ctdev.framework.model;

public class Product {

    private String basketTitle;
    private String totalPriceSoldOutProduct;

    public String getBasketTitle() {
        return basketTitle;
    }

    public void setBasketTitle(String basketTitle) {
        this.basketTitle = basketTitle;
    }

    public String getTotalPriceSoldOutProduct() {
        return totalPriceSoldOutProduct;
    }

    public void setTotalPriceSoldOutProduct(String totalPriceSoldOutProduct) {
        this.totalPriceSoldOutProduct = totalPriceSoldOutProduct;
    }

    public static Product.Builder newBuilder() {
        return new Builder();
    }

    public Product() {
    }

    private Product(final Product.Builder builder) {
        basketTitle = builder.basketTitle;
        totalPriceSoldOutProduct = builder.totalPriceSoldOutProduct;
    }

    public static final class Builder {
        private String basketTitle;
        private String totalPriceSoldOutProduct;

        private Builder() {
        }

        public Product.Builder withTotalPriceSoldOutProduct(final String val) {
            totalPriceSoldOutProduct = val;
            return this;
        }

        public Product.Builder withBasketTitle(final String val) {
            basketTitle = val;
            return this;
        }
        public Product build() {
            return new Product(this);
        }
    }
}
