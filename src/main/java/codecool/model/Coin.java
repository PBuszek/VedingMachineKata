package codecool.model;

public class Coin {
    private final Value value;

    public Coin(Value value) {

        this.value = value;
    }

    public Value getValue() {
        return value;
    }
}
