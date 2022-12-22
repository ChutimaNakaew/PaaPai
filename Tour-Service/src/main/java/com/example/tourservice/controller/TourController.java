package com.example.tourservice.controller;

import com.example.tourservice.model.Tour;
import com.example.tourservice.repository.TourService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Controller

public class TourController {
    private TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping("/tour/main")
    public String tours(@RequestParam(value = "provinces", required=false) String provinces, @RequestParam(value = "filters", required=false) String filters, Model model) {

        if (provinces == null || provinces.equals("")) {
            model.addAttribute("tours", tourService.getAllTours(filters));
            model.addAttribute("provinces", tourService.getProvince());
        } else {
            model.addAttribute("tours", tourService.getToursByProvince(provinces, filters)); //, filters
        }
        return "User/Main";
    }

    @GetMapping("/tour/detail/{name}")
    public String detail(@PathVariable("name") String name, Model model) {
        model.addAttribute("tours", tourService.TourByName(name));
        return "User/DetailTour";
    }


    @GetMapping("/tour/form/{name}")
    public String formTour(@PathVariable("name") String name, Model model) {
        model.addAttribute("tours", tourService.TourByName(name));
        return "User/Form";
    }

    @GetMapping("/tour/provinces")
    public String getProvince() {
        tourService.getProvince();
        return "User/Main";
    }

//---------------------------------------Part Admin---------------------------------------------------------------------

    @GetMapping("/tour/adminHome")
    public String adminHome(Model model) {
        model.addAttribute("tours", tourService.getAllTours());
        return "Admin/adminHome";
    }

    @GetMapping("/tour/adminTour")
    public String adminTour(@RequestParam(value = "provinces", required=false) String provinces, @RequestParam(value = "filters", required=false) String filters, Model model){

        if (provinces == null || provinces.equals("")) {
            model.addAttribute("tours", tourService.getAllTours(filters));
        } else {
            model.addAttribute("tours", tourService.getToursByProvince(provinces, filters)); //, filters
        }
        return "Admin/adminTour";
    }

    @GetMapping(value = {"/tour/edit-add/{id}", "/tour/edit-add"})
    public String editTour(@PathVariable("id") Optional<String> id, Model model) {
        Tour tour = id.isPresent() ?
                tourService.findTourById(id.get()).get() : new Tour();
        model.addAttribute("tour", tour);
        return "Admin/addTour";
    }

    @PostMapping("/tour/save-reservation")
    public String editTour(HttpServletRequest request, @ModelAttribute("tour") @Valid Tour tour,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Admin/editTour";
        }
        tourService.saveTour(tour);
        String redirectUrl = request.getScheme() + "://localhost:8080/tour/adminTour";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/tour/delete-reservation/{id}")
    public String removeTour(HttpServletRequest request, @PathVariable("id") String id, Model model) {
        tourService.deleteTourById(id);
        model.addAttribute("tour", tourService.getAllTours());
        String redirectUrl = request.getScheme() + "://localhost/tour/adminTour";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/tour/TourUpdate/{id}")
    public String TourUpdate(@PathVariable(value = "id") String id, Model model) {
        Tour tour = tourService.getTourById(id);

        model.addAttribute("tour", tour);
        return "admin/editTour";
    }

    @PostMapping("/tour/UpdateForm")
    public String ChangeTour(HttpServletRequest request, @RequestParam("id") String id,
                             @RequestParam("name") String name,
                             @RequestParam("province") String province,
                             @RequestParam("price") Double price,
                             @RequestParam("schedule") String schedule,
                             @RequestParam("img") String img,
                             @RequestParam("detail_img") String detail_img,
                             @RequestParam("detail") String detail,
                             @RequestParam("date_first") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_first,
                             @RequestParam("date_second") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date_second,
                             @RequestParam("max_tourist") Integer max_tourist
    )
    {
        tourService.changeName(id, name, province, price, schedule, img, detail_img, detail, date_first, date_second, max_tourist);
        String redirectUrl = request.getScheme() + "://localhost/tour/adminTour";
        return "redirect:" + redirectUrl;
    }

}
