package io.ctdev.framework.model;

public class Customer {

    private String email;
    private String password;
    private String invalidEmail;
    private String invalidPassword;
    private String emptyString;
    private String invalidPasswordRepeat;
    private String maidenName;
    private String id;
    private String basketTitle;
    private String soldOutProductPath;
    private String totalPriceSoldOutProduct;

    public String getBasketTitle() {
        return basketTitle;
    }

    public void setBasketTitle(String basketTitle) {
        this.basketTitle = basketTitle;
    }

    public String getSoldOutProductPath() {
        return soldOutProductPath;
    }

    public void setSoldOutProductPath(String soldOutProductPath) {
        this.soldOutProductPath = soldOutProductPath;
    }

    public String getTotalPriceSoldOutProduct() {
        return totalPriceSoldOutProduct;
    }

    public void setTotalPriceSoldOutProduct(String totalPriceSoldOutProduct) {
        this.totalPriceSoldOutProduct = totalPriceSoldOutProduct;
    }

    public String getInvalidPasswordRepeat() {
        return invalidPasswordRepeat;
    }

    public void setInvalidPasswordRepeat(String invalidPasswordRepeat) {
        this.invalidPasswordRepeat = invalidPasswordRepeat;
    }

    public String getMaidenName() {
        return maidenName;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvalidEmail() {
        return invalidEmail;
    }

    public void setInvalidEmail(String invalidEmail) {
        this.invalidEmail = invalidEmail;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(String invalidPassword) {
        this.invalidPassword = invalidPassword;
    }

    public String getEmptyString() {
        return emptyString;
    }

    public void setEmptyString(String emptyString) {
        this.emptyString = emptyString;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Customer() {
    }

    private Customer(final Builder builder) {
        email = builder.email;
        password = builder.password;
        invalidEmail = builder.invalidEmail;
        invalidPassword = builder.invalidPassword;
        emptyString = builder.emptyString;
        invalidPasswordRepeat = builder.invalidPasswordRepeat;
        maidenName = builder.maidenName;
        id = builder.id;
        basketTitle = builder.basketTitle;
        soldOutProductPath = builder.soldOutProductPath;
        totalPriceSoldOutProduct = builder.totalPriceSoldOutProduct;
    }

    public static final class Builder {
        private String emptyString;
        private String invalidEmail;
        private String invalidPassword;
        private String email;
        private String password;
        private String invalidPasswordRepeat;
        private String maidenName;
        private String id;
        private String basketTitle;
        private String soldOutProductPath;
        private String totalPriceSoldOutProduct;

        private Builder() {
        }

        public Builder withTotalPriceSoldOutProduct(final String val) {
            totalPriceSoldOutProduct = val;
            return this;
        }

        public Builder withSoldOutProductPath(final String val) {
            soldOutProductPath = val;
            return this;
        }

        public Builder withBasketTitle(final String val) {
            basketTitle = val;
            return this;
        }

        public Builder withInvalidPasswordRepeat(final String val) {
            invalidPasswordRepeat = val;
            return this;
        }

        public Builder withMaidenName(final String val) {
            maidenName = val;
            return this;
        }

        public Builder withId(final String val) {
            id = val;
            return this;
        }

        public Builder withName(final String val) {
            email = val;
            return this;
        }

        public Builder withPassword(final String val) {
            password = val;
            return this;
        }

        public Builder withInvalidEmail(final String val) {
            invalidEmail = val;
            return this;
        }

        public Builder withInvalidPassword(final String val) {
            invalidPassword = val;
            return this;
        }

        public Builder withEmptyString(final String val) {
            emptyString = val;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }

}
