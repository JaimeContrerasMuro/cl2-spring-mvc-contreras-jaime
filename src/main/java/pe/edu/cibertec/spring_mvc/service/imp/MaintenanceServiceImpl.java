package pe.edu.cibertec.spring_mvc.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.spring_mvc.dto.FilmCreateDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDeleteDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDetailDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDto;
import pe.edu.cibertec.spring_mvc.entity.Film;
import pe.edu.cibertec.spring_mvc.entity.Language;
import pe.edu.cibertec.spring_mvc.repository.FilmRepository;
import pe.edu.cibertec.spring_mvc.repository.LanguageRepository;
import pe.edu.cibertec.spring_mvc.service.MaintenanceService;

import java.util.*;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    FilmRepository filmRepository;

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public List<FilmDto> findAllFilms(String sortField, String sortDirection) {
        List<FilmDto> films = new ArrayList<>();
        Iterable<Film> iterable = filmRepository.findAll();
        iterable.forEach(film -> {
            FilmDto filmDto = new FilmDto(film.getFilmId(),
                    film.getTitle(),
                    film.getLanguage().getName(),
                    film.getRentalDuration(),
                    film.getRentalRate());
            films.add(filmDto);
        });

        // Ordenar la lista basada en el campo y la dirección
        if (sortDirection.equals("asc")) {
            switch (sortField) {
                case "filmId":
                    films.sort(Comparator.comparing(FilmDto::getFilmId));
                    break;
                case "title":
                    films.sort(Comparator.comparing(FilmDto::getTitle));
                    break;
                case "language":
                    films.sort(Comparator.comparing(FilmDto::getLanguage));
                    break;
                case "rentalDuration":
                    films.sort(Comparator.comparing(FilmDto::getRentalDuration));
                    break;
                case "rentalRate":
                    films.sort(Comparator.comparing(FilmDto::getRentalRate));
                    break;
            }
        } else {  // Orden descendente
            switch (sortField) {
                case "filmId":
                    films.sort(Comparator.comparing(FilmDto::getFilmId).reversed());
                    break;
                case "title":
                    films.sort(Comparator.comparing(FilmDto::getTitle).reversed());
                    break;
                case "language":
                    films.sort(Comparator.comparing(FilmDto::getLanguage).reversed());
                    break;
                case "rentalDuration":
                    films.sort(Comparator.comparing(FilmDto::getRentalDuration).reversed());
                    break;
                case "rentalRate":
                    films.sort(Comparator.comparing(FilmDto::getRentalRate).reversed());
                    break;
            }
        }
        return films;
    }

    @Override
    public FilmDetailDto findDetailById(Integer id) {

        Optional<Film> optional = filmRepository.findById(id);
        return optional.map(
                film -> new FilmDetailDto(film.getFilmId(),
                        film.getTitle(),
                        film.getDescription(),
                        film.getReleaseYear(),
                        film.getRentalDuration(),
                        film.getRentalRate(),
                        film.getLength(),
                        film.getReplacementCost(),
                        film.getRating(),
                        film.getSpecialFeatures(),
                        film.getLastUpdate())
        ).orElse(null);

    }

    @Override
    public Boolean updateFilm(FilmDetailDto filmDetailDto) {
        Optional<Film> optional = filmRepository.findById(filmDetailDto.filmId());
        return optional.map(
                film -> {
                    film.setTitle(filmDetailDto.title());
                    film.setDescription(filmDetailDto.description());
                    film.setReleaseYear(filmDetailDto.releaseYear());
                    film.setRentalDuration(filmDetailDto.rentalDuration());
                    film.setRentalRate(filmDetailDto.rentalRate());
                    film.setLength(filmDetailDto.length());
                    film.setReplacementCost(filmDetailDto.replacementCost());
                    film.setRating(filmDetailDto.rating());
                    film.setSpecialFeatures(filmDetailDto.specialFeatures());
                    film.setLastUpdate(new Date());
                    filmRepository.save(film);
                    return true;
                }
        ).orElse(false);

    }

    @Override
    public FilmCreateDto createFilm(FilmCreateDto filmCreateDto) {
        Film film = new Film();

        film.setTitle(filmCreateDto.title());
        film.setDescription(filmCreateDto.description());
        film.setReleaseYear(filmCreateDto.releaseYear());
        film.setRentalDuration(filmCreateDto.rentalDuration());
        film.setRentalRate(filmCreateDto.rentalRate());
        film.setLength(filmCreateDto.length());
        film.setReplacementCost(filmCreateDto.replacementCost());
        film.setRating(filmCreateDto.rating());
        film.setSpecialFeatures(filmCreateDto.specialFeatures());
        film.setLastUpdate(new Date());

        if (filmCreateDto.originalLanguageId() != null) {
            Language language = languageRepository.findById(filmCreateDto.originalLanguageId())
                    .orElseThrow(() -> new IllegalArgumentException("" + filmCreateDto.originalLanguageId()));
            film.setLanguage(language);
        }
        Film savedFilm = filmRepository.save(film);
        return new FilmCreateDto(
                savedFilm.getFilmId(),
                savedFilm.getTitle(),
                savedFilm.getDescription(),
                savedFilm.getReleaseYear(),
                savedFilm.getLanguage() != null ? savedFilm.getLanguage().getLanguageId() : null,
                savedFilm.getRentalDuration(),
                savedFilm.getRentalRate(),
                savedFilm.getLength(),
                savedFilm.getReplacementCost(),
                savedFilm.getRating(),
                savedFilm.getSpecialFeatures(),
                savedFilm.getLastUpdate()
        );
    }

    @Override
    public FilmDeleteDto deleteFilmById(Integer id) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El ID de la película no es válido."));
        filmRepository.deleteById(id);
        return new FilmDeleteDto(film.getFilmId());
    }

}