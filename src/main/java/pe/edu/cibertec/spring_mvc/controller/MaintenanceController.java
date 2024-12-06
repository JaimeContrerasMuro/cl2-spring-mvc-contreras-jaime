package pe.edu.cibertec.spring_mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.edu.cibertec.spring_mvc.dto.FilmCreateDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDeleteDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDetailDto;
import pe.edu.cibertec.spring_mvc.dto.FilmDto;
import pe.edu.cibertec.spring_mvc.entity.Language;
import pe.edu.cibertec.spring_mvc.repository.LanguageRepository;
import pe.edu.cibertec.spring_mvc.service.MaintenanceService;

import java.util.List;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    MaintenanceService maintenanceService;

    @GetMapping("/start")
    public String start(Model model,
                        @RequestParam(defaultValue = "filmId") String sortField,
                        @RequestParam(defaultValue = "asc") String sortDirection) {

        // Obtener la lista de películas
        List<FilmDto> films = maintenanceService.findAllFilms(sortField, sortDirection);

        // Agregar los parámetros de ordenación al modelo
        model.addAttribute("films", films);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "maintenance";  // Retorna la vista "maintenance"
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findDetailById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        FilmDetailDto filmDetailDto = maintenanceService.findDetailById(id);
        model.addAttribute("film", filmDetailDto);
        return "maintenance-edit";
    }

    @PostMapping("/edit-confirm")
    public String edit(@ModelAttribute FilmDetailDto film, Model model) {
        maintenanceService.updateFilm(film);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<Language> languages = languageRepository.findAll();
        FilmCreateDto newFilm = new FilmCreateDto(null, "", "", null, null, null, null, null, null, null, null, null);
        model.addAttribute("film", newFilm);
        model.addAttribute("languages", languages); // Lista de idiomas
        return "maintenance-create"; // Nombre de la vista
    }


    @PostMapping("/create-confirm")
    public String create(@ModelAttribute FilmCreateDto film, Model model) {
        maintenanceService.createFilm(film);
        return "redirect:/maintenance/start";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            FilmDeleteDto filmDeleteDto = maintenanceService.deleteFilmById(id);
            redirectAttributes.addFlashAttribute("message", "Película eliminada correctamente. ID: " + filmDeleteDto.filmId());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Error al eliminar la película: " + e.getMessage());
        }
        return "redirect:/maintenance/start";
    }

}