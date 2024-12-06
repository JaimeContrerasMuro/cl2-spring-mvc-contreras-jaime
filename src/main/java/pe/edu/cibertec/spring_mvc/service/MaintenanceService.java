package pe.edu.cibertec.spring_mvc.service;

import pe.edu.cibertec.spring_mvc.dto.FilmCreateDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDeleteDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDetailDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDto;

import java.util.List;

public interface MaintenanceService {
    List<FilmDto> findAllFilms(String sortField, String sortDirection);

    FilmDetailDto findDetailById(Integer id);

    Boolean updateFilm(FilmDetailDto filmDetailDto);

    FilmCreateDto createFilm(FilmCreateDto filmCreateDto);

    FilmDeleteDto deleteFilmById(Integer id);

}