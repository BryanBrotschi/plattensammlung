package model;

import java.time.LocalDate;

public class Cassette extends Record{

    public Cassette(String category, String artist, String recordTitle, LocalDate releaseDate, String genre, String condition,
            String notice, Double price) {
        super(category, artist, recordTitle, releaseDate, genre, condition, notice, price);
    }

    public Cassette() {
    }

    @Override
    public String toString() {
        return "Cassette []";
    }
    
}
