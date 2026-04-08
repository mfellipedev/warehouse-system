package warehouse.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Warehouse {
    private final HashMap<String, Address> addresses;
    private final List<Street> streets;
    private final List<Level> levels;
    private final List<Column> columns;

    public Warehouse(int quantityStreet, int quantityLevel, int quantityColumn) {
        this.addresses = new HashMap<>();
        this.streets = new ArrayList<>();
        this.levels = new ArrayList<>();
        this.columns = new ArrayList<>();
        generateStreets(quantityStreet);
        generateLevels(quantityLevel);
        generateColumns(quantityColumn);
        generateAddress();
    }

    private void generateStreets(int quantity) {
        if (quantity > 26 || quantity < 1) {
            throw new IllegalArgumentException();
        }
        char lyrics = 'A';
        for (int i = 0; i < quantity; i++) {
            streets.add(new Street(String.valueOf(lyrics)));
            lyrics++;
        }
    }

    private void generateLevels(int quantity) {
        if (quantity < 1 || quantity > 3) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i <= quantity; i++) {
            levels.add(new Level(i));
        }
    }

    private void generateColumns(int quantity) {
        if (quantity < 1 || quantity > 10) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i <= quantity; i++) {
            columns.add(new Column(i));
        }
    }

    private void generateAddress() {
        for (int i = 0; i < this.streets.size(); i++) {
            for (int j = 0; j < this.levels.size(); j++) {
                for (int k = 0; k < this.columns.size(); k++) {
                    Address address = new Address(this.streets.get(i), this.columns.get(k), this.levels.get(j));
                    addAddress(address.getId(), address);
                }
            }
        }
    }

    private void addAddress(String id, Address address) {
        if (this.addresses.containsKey(id)) {
            throw new IllegalArgumentException("Não pode existir dois IDs iguais");
        }
        this.addresses.put(id, address);
    }

    public int quantityOfAddress() {
        return this.addresses.size();
    }
}
