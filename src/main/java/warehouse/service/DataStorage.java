package warehouse.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;

public class DataStorage {
    private final ObjectMapper mapper;
    private final String filePath = "warehouse.json";

    public DataStorage() {
        this.mapper = new ObjectMapper();

        this.mapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
        this.mapper.registerModule(new com.fasterxml.jackson.module.paramnames.ParameterNamesModule());
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        this.mapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
        this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void save(InventoryManager inventoryManager) throws IOException {
        File file = new File("warehouse.json");

        mapper.writeValue(file, inventoryManager);

        // Verifica se o arquivo foi realmente criado
        if (file.exists()) {
            System.out.println( "O arquivo foi criado com sucesso!");
        } else {
            System.out.println("ERRO CRÍTICO: O arquivo não foi gerado no disco!");
        }
    }

    public InventoryManager load() throws IOException {
        File file = new File("warehouse.json");
        if (!file.exists()) {
            throw new IOException("Arquivo não encontrado");
        }

        try {
            System.out.println("Lendo arquivo de: " + file.getAbsolutePath());
            return mapper.readValue(file, InventoryManager.class);
        } catch (Exception e) {
            System.err.println("Falha ao ler o JSON!");
            throw new IOException("Erro ao ler o arquivo", e);
        }
    }
}
