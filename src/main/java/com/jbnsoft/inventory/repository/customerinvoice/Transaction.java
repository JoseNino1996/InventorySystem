package com.jbnsoft.inventory.repository.customerinvoice;

public enum Transaction {
     CREATE("create"),DELETE("delete"), UPDATE("update");

     Transaction(String transactionType) {
          this.transactionType = transactionType;
     }

     private String transactionType;

     public String getTransactionType() {
          return transactionType;
     }
}
