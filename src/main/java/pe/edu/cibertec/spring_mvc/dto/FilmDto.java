package pe.edu.cibertec.spring_mvc.dto;

public record FilmDto(
        Integer filmId,
        String title,
        String language,
        Integer rentalDuration,
        Double rentalRate) {

    public FilmDto(Integer filmId, String title, String language, Integer rentalDuration, Double rentalRate) {
        this.filmId = filmId;
        this.title = title;
        this.language = language;
        this.rentalDuration = rentalDuration;
        this.rentalRate = rentalRate;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public String getTitle() {
        return title;
    }

    public String getLanguage() {
        return language;
    }

    public Integer getRentalDuration() {
        return rentalDuration;
    }

    public Double getRentalRate() {
        return rentalRate;
    }
}