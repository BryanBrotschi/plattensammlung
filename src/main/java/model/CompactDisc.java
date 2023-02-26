package model;

import java.time.LocalDate;

public class CompactDisc extends Record{

    public CompactDisc(Category category, String artist, String recordTitle, LocalDate releaseDate, String genre, String condition,
            String notice, Double price, String cover) {
        super(category, artist, recordTitle, releaseDate, genre, condition, notice, price, cover);
    }

    public CompactDisc() {
    }

    @Override
    public String toString() {
        return "CompactDisc []";
    }
    
}
