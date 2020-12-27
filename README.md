Simple ATM.
Program is using two files (attached in resources):
- users.csv - storing user login, password and account number
- accounts.csv - storing account number and balance.

Functional requirements:
1. Program should read both files on start.
2. Before performing other operation, user should to able to log in. User should also
be able to log out and switch to other account.
3. Operations available for logged on users:
- help/withdraw/deposit/exit/transfer/undo/balance
4. Logged on users, through all session until they log out should be able to check 
history of session operations and able to undo previous operation.
5. Program should be resistant to errors and swindling (eg. withdraw/transfer excessive resources, 
money transfer to non-existing account).
