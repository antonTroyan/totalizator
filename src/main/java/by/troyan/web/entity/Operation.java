package by.troyan.web.entity;

import java.math.BigDecimal;

/**
 * Describe Operation. operations divided like input and output.
 * User could put some money to his account and withdraw them. <p>
 * Used BigDecimal type to raise accuracy.
 */

public class Operation {
    private String cardNumber;
    private String validityDate;
    private String cardCode;
    private BigDecimal amount;
    private int userId;
    private String operationType;

    public Operation(){}

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (userId != operation.userId) return false;
        if (cardNumber != null ? !cardNumber.equals(operation.cardNumber) : operation.cardNumber != null) return false;
        if (validityDate != null ? !validityDate.equals(operation.validityDate) : operation.validityDate != null)
            return false;
        if (cardCode != null ? !cardCode.equals(operation.cardCode) : operation.cardCode != null) return false;
        if (amount != null ? !amount.equals(operation.amount) : operation.amount != null) return false;
        return operationType != null ? operationType.equals(operation.operationType) : operation.operationType == null;
    }

    @Override
    public int hashCode() {
        int result = cardNumber != null ? cardNumber.hashCode() : 0;
        result = 31 * result + (validityDate != null ? validityDate.hashCode() : 0);
        result = 31 * result + (cardCode != null ? cardCode.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "cardNumber='" + cardNumber + '\'' +
                ", validityDate='" + validityDate + '\'' +
                ", cardCode='" + cardCode + '\'' +
                ", amount=" + amount +
                ", userId=" + userId +
                ", operationType='" + operationType + '\'' +
                '}';
    }
}
