# E-Bank Application Backend

Welcome to the backend repository for the E-Bank application developed for Bank Solutions. This application provides a secure and intuitive platform for users to manage their bank accounts and perform financial operations online.

## User Stories

### Account Management

- **Create Bank Accounts**
  As a user, I want to create a new bank account (checking, savings, etc.) to manage my finances.
  - Account Type (Checking, Savings, etc.)
  - Initial Balance
  - Creation Date

- **View Balances and Transaction History**
  As a user, I want to view the balance and transaction history of my accounts to track my expenses and income.
  - Transaction ID
  - Date and Time
  - Amount
  - Transaction Type (Credit, Debit)
  - Description

- **Close Accounts**
  As a user, I want to close a bank account, specifying the reason for closure.

### Card Management

- **View Card Information**
  As a user, I want to view my card details (number, expiration date) to know the specifics of my card.
  - Card Number
  - Expiration Date
  - Card Type (Debit, Credit)

- **Activate and Deactivate Cards**
  As a user, I want to activate or deactivate my card for transaction security.

- **Block Lost or Stolen Cards**
  As a user, I want to block my card in case of loss or theft to prevent unauthorized use, specifying the reason for blocking.

### Money Transfers

- **Internal Transfers between Accounts**
  As a user, I want to transfer money between my own accounts, specifying the amount and description.

- **External Transfers to Other Banks**
  As a user, I want to transfer money to external accounts for payments or money transfers.
  - External Account Details (Account Number, Bank Details, etc.)
  - Amount
  - Description

- **Manage Payees**
  As a user, I want to add, modify, or delete payees to facilitate external transfers.
  - Payee Name
  - Payee Account Details (Account Number, Bank Details, etc.)

## Technical Requirements

### Project Structure

The project structure follows recommended practices for a Spring Boot application.

### Code Documentation

Each class and method should have clear responsibilities documented, including details on parameters and return values.

### Business Logic for Banking Operations

- Data validation during account creation and closure (e.g., sufficient balance before closure).
- Exception handling for illegal operations (e.g., attempting to transfer from a closed account).
- Validation of amounts before transfers (e.g., positive amount, sufficient balance).

---

