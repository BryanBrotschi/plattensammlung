package controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import model.Record;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReadAndUpdateRecordslist {

    private static final String FILE_PATH = "src/main/resources/records/records.json";

    public static void main(String[] args) throws IOException {
        createJsonFile();
        ArrayList<Record> records = readRecordsFromJson(FILE_PATH);
        Record record1 = new Record("Jane Smith", "New Record 1", LocalDate.parse("1995-09-29"), "Rock", "Excellent", "None", 14.99);
        Record record2 = new Record("Bob Johnson", "New Record 2", LocalDate.parse("1995-09-29"), "Jazz", "Very Good", "Limited edition", 19.99);
        records.add(record1);
        records.add(record2);
        saveRecordsToJson(records, FILE_PATH);
        System.out.println("JSON file updated successfully.");
    }

    private static ArrayList<Record> readRecordsFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File(filePath);
        ArrayList<Record> records = objectMapper.readValue(file, new TypeReference<ArrayList<Record>>() {});
        return records;
    }
    
    private static void saveRecordsToJson(ArrayList<Record> records, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.writeValue(new File(filePath), records);
    }
    

    
}
