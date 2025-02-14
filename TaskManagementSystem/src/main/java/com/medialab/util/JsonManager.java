package com.medialab.util;

import com.google.gson.*;
import com.medialab.model.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JsonManager {
    private static final String DATA_DIR = "medialab";
    private static final String TASKS_FILE = "tasks.json";
    private static final String CATEGORIES_FILE = "categories.json";
    private static final String PRIORITIES_FILE = "priorities.json";
    private static final String REMINDERS_FILE = "reminders.json";
    
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .setPrettyPrinting()
        .create();

    public static void initializeDataDirectory() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory", e);
        }
    }

    public static void saveToJson(Object data, String filename) {
        Path filePath = Paths.get(DATA_DIR, filename);
        try (Writer writer = Files.newBufferedWriter(filePath)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save data to " + filename, e);
        }
    }

    public static <T> T loadFromJson(String filename, Class<T> type) {
        Path filePath = Paths.get(DATA_DIR, filename);
        if (!Files.exists(filePath)) {
            return null;
        }
        
        try (Reader reader = Files.newBufferedReader(filePath)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load data from " + filename, e);
        }
    }
}