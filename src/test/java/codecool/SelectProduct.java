package codecool;

import codecool.model.Coin;
import codecool.model.Product;
import codecool.model.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SelectProduct {

    private final Map<Value, Integer> machineCoins = new HashMap<>();
    private final Map<Product, Integer> machineStock = new HashMap<>();
    private Machine machine;

    @BeforeEach
    public void prepareMachine() {
        fillMaps();
        machine = new Machine(machineCoins, machineStock);
    }

    @Test
    public void machineShouldDispenseCola() {
        machine.insertCoin(new Coin(Value.QUARTER));
        machine.insertCoin(new Coin(Value.QUARTER));
        machine.insertCoin(new Coin(Value.QUARTER));
        machine.insertCoin(new Coin(Value.QUARTER));
        Assertions.assertAll("Should dispense cola and thank you",
                () -> assertEquals(Product.COLA, machine.selectProduct(Product.COLA)),
                () -> assertEquals("THANK YOU", machine.showDisplay()));
    }

    @Test
    public void machineShouldNotDispenseCandy(){
        machine.insertCoin(new Coin(Value.QUARTER));
        Assertions.assertAll("Should dispense nothing and show insert coin",
                () -> assertEquals(Product.NOTHING, machine.selectProduct(Product.CANDY)),
                () -> assertEquals("INSERT COIN", machine.showDisplay()));
    }

    @Test
    public void machineShouldDispenseCandyAndChange(){
        machine.insertCoin(new Coin(Value.QUARTER));
        machine.insertCoin(new Coin(Value.QUARTER));
        machine.insertCoin(new Coin(Value.QUARTER));
        Assertions.assertAll("Should dispense candy and change",
                () -> assertEquals(Product.CANDY, machine.selectProduct(Product.CANDY)),
                () -> assertEquals("THANK YOU", machine.showDisplay()),
                () -> assertEquals("0.10,", machine.showReturn()));
    }

    @Test
    public void machineShouldDisplaySoldOut(){
        machineStock.put(Product.CANDY, 0);
        machine.insertCoin(new Coin(Value.DIME));
        machine.selectProduct(Product.CANDY);
        assertEquals("SOLD OUT", machine.showDisplay());
    }

    private void fillMaps(){
        machineCoins.put(Value.NICKEL, 5);
        machineCoins.put(Value.DIME, 10);
        machineCoins.put(Value.QUARTER, 10);
        machineStock.put(Product.COLA, 4);
        machineStock.put(Product.CHIPS, 8);
        machineStock.put(Product.CANDY, 10);
    }
}
