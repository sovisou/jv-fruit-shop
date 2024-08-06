package core.basesyntax.dao.impl;

import core.basesyntax.dao.DataConverter;
import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> inputReport) {
        inputReport.replaceAll(string -> string.replace(" ", ""));
        List<FruitTransaction> transactions = new ArrayList<>();
        for (int i = 1; i < inputReport.size(); i++) {
            String[] parts = inputReport.get(i).split(",");
            try {
                if (parts.length != 3) {
                    continue;
                }
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid format: " + parts.length);
                continue;
            }
            FruitTransaction.Operation operation;
            try {
                operation = FruitTransaction.Operation.getOperationFromCode(parts[0]);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid operation code: " + parts[0]);
                continue;
            }
            String fruit = parts[1];
            int quantity;
            try {
                quantity = Integer.parseInt(parts[2]);
                if (quantity < 0) {
                    throw new NumberFormatException("Quantity cannot be negative");
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid quantity: " + parts[2]);
                continue;
            }
            transactions.add(new FruitTransaction(operation, fruit, quantity));
        }
        return transactions;
    }
}