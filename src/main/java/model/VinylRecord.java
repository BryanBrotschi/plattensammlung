package model;

import java.time.LocalDate;

public class VinylRecord extends Record{

    public VinylRecord(Category category, String artist, String recordTitle, LocalDate releaseDate, String genre, String condition,
            String notice, Double price, String cover) {
        super(category, artist, recordTitle, releaseDate, genre, condition, notice, price, cover);
    }

    public VinylRecord() {
    }

    @Override
    public String toString() {
        return "VinylRecord []";
    }
    
}
