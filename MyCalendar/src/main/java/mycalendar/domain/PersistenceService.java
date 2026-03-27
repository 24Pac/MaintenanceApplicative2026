package mycalendar.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistenceService {
    private final ObjectMapper mapper;
    private final String filePath;

    public PersistenceService(String filePath) {
        this.filePath = filePath;
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.registerModule(new ParameterNamesModule());
    }

    public void save(List<Evenement> evenements) throws IOException {
        mapper.writerFor(new TypeReference<List<Evenement>>() {})
                .withDefaultPrettyPrinter()
                .writeValue(new File(filePath), evenements);
    }

    public List<Evenement> load() throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return mapper.readValue(file, new TypeReference<List<Evenement>>() {});
    }
}
