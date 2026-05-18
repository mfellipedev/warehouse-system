package warehouse.domain;

import java.util.*;

public class Warehouse {
    private  TreeMap<String, Address> addresses;
    private  List<Street> streets;
    private  List<Level> levels;
    private  List<Column> columns;

    public Warehouse(int quantityStreet, int quantityLevel, int quantityColumn) {
        this.addresses = new TreeMap<>();
        this.streets = new ArrayList<>();
        this.levels = new ArrayList<>();
        this.columns = new ArrayList<>();
        generateStreets(quantityStreet);
        generateLevels(quantityLevel);
        generateColumns(quantityColumn);
        generateAddress();
    }

    protected Warehouse() {
        this.addresses = new TreeMap<>();
        this.streets = new ArrayList<>();
        this.levels = new ArrayList<>();
        this.columns = new ArrayList<>();}

    public TreeMap<String, Address> getAddresses() {
        return addresses;
    }

    public List<Street> getStreets() {
        return streets;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public Address getAddress(String id) {
        Address address = this.addresses.get(id);
        if (address == null) {
            throw new IllegalArgumentException("O endereço não pode ser encontrado!");
        }
        return address;
    }

    private void generateStreets(int quantity) {
        if (quantity > 26 || quantity < 1) {
            throw new IllegalArgumentException("Quantidade de ruas invalida!");
        }
        char lyrics = 'A';
        for (int i = 0; i < quantity; i++) {
            streets.add(new Street(String.valueOf(lyrics)));
            lyrics++;
        }
    }

    private void generateLevels(int quantity) {
        if (quantity < 1 || quantity > 3) {
            throw new IllegalArgumentException("Informe uma quantidade valida de níveis");
        }
        for (int i = 1; i <= quantity; i++) {
            levels.add(new Level(i));
        }
    }

    private void generateColumns(int quantity) {
        if (quantity < 1 || quantity > 10) {
            throw new IllegalArgumentException("Informe uma quantidade valida de colunas");
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

    public TreeMap<String, Address> returnAddresses() {
        TreeMap<String, Address> copyAddresses = new TreeMap<>(this.addresses);
        return copyAddresses;
    }

    public List<Street> returnStreets() {
        List<Street> listStreets = new ArrayList<>(this.streets);
        Collections.sort(listStreets, (s1, s2) -> s1.street().compareTo(s2.street()));
        return listStreets;
    }

}