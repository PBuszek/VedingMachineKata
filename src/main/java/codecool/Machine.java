package codecool;

import codecool.model.Product;
import codecool.model.Coin;
import codecool.model.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Machine {
    private final List<Coin> customerCoins;
    private final List<Coin> returnedCoins;
    private final Map<Value, Integer> machineCoins;
    private final Map<Product, Integer> machineStock;
    private String currentDisplay;

    public Machine(Map<Value, Integer> machineCoins, Map<Product, Integer> machineStock) {
        this.customerCoins = new ArrayList<>();
        this.returnedCoins = new ArrayList<>();
        this.machineCoins = machineCoins;
        this.machineStock = machineStock;
        if (machineCoins.get(Value.NICKEL) >= 1){ //with one nickel inside you can buy cola and chips for sure and probably candy
            currentDisplay = "INSERT COIN";
        }else{
            currentDisplay = "EXACT CHANGE ONLY";
        }
    }

    public void insertCoin(Coin coin) {
        if (coin.getValue() != Value.NOT_WORTHY) {
            customerCoins.add(coin);
            currentDisplay = "Current amount: ";
            currentDisplay += getCoinsSum(customerCoins);
            currentDisplay += "$";
        } else {
            returnedCoins.add(coin);
            currentDisplay = "INSERT COIN";
        }
    }

    public Product selectProduct(Product product) {
        if (machineStock.get(product) >= 1){
            if (getCoinsSum(customerCoins).equals(product.getPrice())) {
                addCoinsToMachine();
                currentDisplay = "THANK YOU";
                return product;
            } else if (getCoinsSum(customerCoins).compareTo(product.getPrice()) >= 0) {
                if (checkIfChangePossibleAndDispenseIt(product)) {
                    addCoinsToMachine();
                    currentDisplay = "THANK YOU";
                    customerCoins.clear();
                    return product;
                }else{
                    currentDisplay = "CANNOT DISPENSE CHANGE";
                    returnButton();
                    return Product.NOTHING;
                }
            }
            currentDisplay = "INSERT COIN";
        }else {
            currentDisplay = "SOLD OUT";
        }
        return Product.NOTHING;
    }

    public void returnButton() {
        returnedCoins.addAll(customerCoins);
        customerCoins.clear();
    }

    public String showDisplay() {
        return currentDisplay;
    }

    public String showReturn() {
        StringBuilder coins = new StringBuilder();
        for (Coin coin : returnedCoins) {
            coins.append(coin.getValue().getValue().toString()).append(",");
        }
        return coins.toString();
    }

    private boolean checkIfChangePossibleAndDispenseIt(Product product) { // change 0.30 is needed but machine has 0.25 and 3 times 0.10
        BigDecimal change = getCoinsSum(customerCoins).subtract(product.getPrice());
        int quarters = change.divide(BigDecimal.valueOf(0.25)).intValue();
        BigDecimal quartersRest = change.divideAndRemainder(BigDecimal.valueOf(0.25))[1];
        int dimes = quartersRest.divide(BigDecimal.valueOf(0.10)).intValue();
        BigDecimal dimesRest = quartersRest.divideAndRemainder(BigDecimal.valueOf(0.10))[1];
        int nickels = dimesRest.divide(BigDecimal.valueOf(0.05)).intValue();
        if (machineCoins.get(Value.QUARTER) >= quarters &&
                machineCoins.get(Value.DIME) >= dimes && machineCoins.get(Value.NICKEL) >= nickels) {
            addCoinToReturn(quarters, Value.QUARTER);
            addCoinToReturn(dimes, Value.DIME);
            addCoinToReturn(nickels, Value.NICKEL);
            return true;
        }
        return false;
    }

    private void addCoinToReturn(int amount, Value value) {
        for (int i = 0; i < amount; i++) {
            returnedCoins.add(new Coin(value));
        }
    }

    private void addCoinsToMachine() {
        for (Coin coin : customerCoins) {
            int amount = machineCoins.get(coin.getValue());
            machineCoins.put(coin.getValue(), ++amount);
        }
    }

    private BigDecimal getCoinsSum(List<Coin> coins) {
        BigDecimal sum = new BigDecimal("0");
        for (Coin coin : coins) {
            sum = sum.add(coin.getValue().getValue());
        }
        return sum;
    }
}
