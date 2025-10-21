package com.example.rentalapp.controller;

import com.example.rentalapp.entity.Rental;
import com.example.rentalapp.repository.RentalRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Optional;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    private final RentalRepository rentalRepository;

    public RentalController(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // Список всех записей
    @GetMapping
    public String listRentals(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        return "rentals_list";
    }

    // Форма создания новой записи
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("rental", new Rental());
        model.addAttribute("formTitle", "Добавить новое объявление");
        return "rental_form";
    }

    // Сохранение (и создание, и обновление)
    @PostMapping("/save")
    public String saveRental(@ModelAttribute("rental") Rental rental, RedirectAttributes ra) {
        // Небольшая валидация/преобразование: если monthlyPrice пришёл null, ставим 0.00
        if (rental.getMonthlyPrice() == null) {
            rental.setMonthlyPrice(BigDecimal.ZERO);
        }
        rentalRepository.save(rental);
        ra.addFlashAttribute("successMessage", "Сохранено успешно");
        return "redirect:/rentals";
    }

    // Редактировать - показать форму с заполненными данными
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        Optional<Rental> opt = rentalRepository.findById(id);
        if (opt.isPresent()) {
            model.addAttribute("rental", opt.get());
            model.addAttribute("formTitle", "Редактировать объявление");
            return "rental_form";
        } else {
            ra.addFlashAttribute("errorMessage", "Объявление не найдено");
            return "redirect:/rentals";
        }
    }

    // Удалить
    @GetMapping("/delete/{id}")
    public String deleteRental(@PathVariable("id") Long id, RedirectAttributes ra) {
        if (rentalRepository.existsById(id)) {
            rentalRepository.deleteById(id);
            ra.addFlashAttribute("successMessage", "Удалено успешно");
        } else {
            ra.addFlashAttribute("errorMessage", "Объявление не найдено");
        }
        return "redirect:/rentals";
    }
}
