package io.ctdev.framework.model;

public class Customer {

    private String email;
    private String password;
    private String invalidEmail;
    private String invalidPassword;
    private String emptyString;

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
    }

    public static final class Builder {
        private String emptyString;
        private String invalidEmail;
        private String invalidPassword;
        private String email;
        private String password;

        private Builder() {
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
