package model;

import java.time.LocalDate;


import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;


public class Record {

	private StringProperty artist;
	private StringProperty recordTitle;
	private ObjectProperty<LocalDate> releaseDate;
	private StringProperty genre;
	private StringProperty condition;
	private StringProperty notice;
	private DoubleProperty price;
	


	public Record() {
		this(null, null, null, null, null, null, 0.0);
	}

	public Record(String artist, String recordTitle, LocalDate releaseDate, String genre, String condition,
			String notice, Double price) {

		this.artist = new SimpleStringProperty(artist);
		this.recordTitle = new SimpleStringProperty(recordTitle);
		this.releaseDate = new SimpleObjectProperty<LocalDate>(releaseDate);
		this.genre = new SimpleStringProperty(genre);
		this.condition = new SimpleStringProperty(condition);
		this.notice = new SimpleStringProperty(notice);
		this.price = new SimpleDoubleProperty(price);
	}

	// artist

	public String getArtist() {
		return artist.get();
	}

	public void setArtist(String artist) {
		this.artist.set(artist);
	}

	public StringProperty artistProperty() {
		return artist;
	}

	// recordTitle

	public String getRecordTitle() {
		return recordTitle.get();
	}

	public void setRecordTitle(String recordTitle) {
		this.recordTitle.set(recordTitle);
	}

	public StringProperty recordTitleProperty() {
		return recordTitle;
	}

	// releaseDate

	public LocalDate getReleaseDate() {
		return releaseDate.get();
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate.set(releaseDate);
	}

	public ObjectProperty<LocalDate> releaseDateProperty() {
		return releaseDate;
	}

	// genre

	public StringProperty genreProperty() {
		return genre;
	}

	public String getGenre() {
		return genre.get();
	}

	public void setGenre(String genre) {
		this.genre.set(genre);

	}

	// Condition

	public String getCondition() {
		return condition.get();
	}

	public void setCondition(String condition) {
		this.condition.set(condition);
	}

	public StringProperty conditionProperty() {
		return condition;
	}

	// notice

	public StringProperty noticeProperty() {
		return notice;
	}

	public String getNotice() {
		return notice.get();
	}

	public void setNotice(String notice) {
		this.notice.set(notice);
	}

	// price

	public DoubleProperty priceProperty() {
		return price;
	}

	public Double getPrice() {
		return price.get();
	}

	public void setPrice(Double price) {
		this.price.set(price);
	}

	@Override
	public String toString() {
		return "Record [artist=" + artist + ", recordTitle=" + recordTitle + " \n releaseDate=" + releaseDate
				+ ", genre="
				+ genre + "\n condition=" + condition + ", notice=" + notice + "\n price=" + price + "]";
	}

}
