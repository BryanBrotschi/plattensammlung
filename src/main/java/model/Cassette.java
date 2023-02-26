package model;

import java.time.LocalDate;

public class Cassette extends Record{

    public Cassette(Category category, String artist, String recordTitle, LocalDate releaseDate, String genre, String condition,
            String notice, Double price, String cover) {
        super(category, artist, recordTitle, releaseDate, genre, condition, notice, price, cover);
    }

    public Cassette() {
    }

    @Override
    public String toString() {
        return "Cassette []";
    }
    
}
