package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Controller
@CrossOrigin("*")

public class UserController {

    private UserService userService;

    @Autowired
    private RabbitTemplate template;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/save-user")
    public String saveUser(HttpServletRequest request, @ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            String redirectUrl = request.getScheme() + "://localhost:8080/tour/main";
            return "redirect:" + redirectUrl;
        }
        userService.save(user);
        return "User/ConfirmTour";
    }

    @GetMapping("/user/delete-usertour/{id}")
    public String removeUserTour(HttpServletRequest request, @PathVariable("id") String id, Model model) {
        userService.deleteUserById(id);
        model.addAttribute("user", userService.getAllUsers());
        String redirectUrl = request.getScheme() + "://localhost:8080/tour/main";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/user/pay/{id}")
    public String userPayment(@PathVariable("id") Optional<String> id, Model model) {

        User user = id.isPresent() ?
                userService.findUserById(id.get()).get() : new User();
        model.addAttribute("user", user);

        return "User/FormPay";
    }

    @PostMapping("/user/save-pay")
    public String payTour(HttpServletRequest request, @RequestParam("bank") String bank, @RequestParam("id") String id, @RequestParam("image") MultipartFile image) throws IOException {
        userService.change(bank, id, image);
        String redirectUrl = request.getScheme() + "://localhost:8080/user/Status/" + id;
        return "redirect:" + redirectUrl;
//        return "redirect:/Status/"+id;
    }

//-------------------------------------------------Part Admin-----------------------------------------------------------

    @GetMapping("/user/adminViewList/{nameTour}")
    public String ShowUser(@PathVariable("nameTour") String nameTour, Model model) {
        model.addAttribute("user", userService.findByNameTour(nameTour));
        model.addAttribute("count", userService.getCount(nameTour));
        return "Admin/adminViewListName";
    }

    @GetMapping("/user/adminAllBooking")
    public String Booking(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "Admin/adminAllBooking";
    }

    @GetMapping("/user/userdetail/{id}")
    public String userDetail(@PathVariable("id") String id, Model model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);
        return "Admin/userDetail";
    }

    @GetMapping("/user/adminPayment")
    public String adminPayment(Model model) {
        model.addAttribute("tours", userService.getAllUsers());
        return "Admin/adminPayment";
    }

    @GetMapping("/user/adminCheckPayment/{id}")
    public String adminCheckPayment(@PathVariable("id") String id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("image",
                Base64.getEncoder().encodeToString(user.getSlip().getData()));
        model.addAttribute("user", user);
        return "Admin/adminCheckPayment";
    }

    @PostMapping("/user/updateStateTrue/{id}")
    public String updateStateTrue(HttpServletRequest request, @PathVariable("id") String id)
    {
        userService.updateStateTrue(id);
        template.convertAndSend("MyDirect", "check", id);
        String redirectUrl = request.getScheme() + "://localhost:8080/user/adminPayment";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/user/Status/{id}")
    public String Status(@PathVariable("id") String id,Model model){
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/Status";
    }

    @PostMapping("/user/updateStateFalse/{id}")
    public String updateStateFalse(HttpServletRequest request, @PathVariable("id") String id)
    {
        userService.updateStateFalse(id);
        template.convertAndSend("MyDirect2", "notpass", id);
        String redirectUrl = request.getScheme() + "://localhost:8080/user/adminPayment";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/user/adminPaymentApprove")
    public String adminPaymentApprove(Model model) {
        model.addAttribute("tours", userService.getAllUsers());
        return "Admin/adminPaymentApprove";
    }

    @PostMapping("/user/changeStateFalse/{id}")
    public String changeStateFalse(HttpServletRequest request, @PathVariable("id") String id)
    {
        userService.changeStateFalse(id);
        template.convertAndSend("MyDirect2", "notpass", id);
        String redirectUrl = request.getScheme() + "://localhost:8080/user/adminPaymentApprove";
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/user/notPass")
    public String notPass(Model model) {
        model.addAttribute("tours", userService.getAllUsers());
        return "Admin/notPass";
    }

    @PostMapping("/user/changeStateTrue/{id}")
    public String changeStateTrue(HttpServletRequest request, @PathVariable("id") String id)
    {
        userService.updateStateTrue(id);
        template.convertAndSend("MyDirect", "check", id);
        String redirectUrl = request.getScheme() + "://localhost:8080/user/notPass";
        return "redirect:" + redirectUrl;
    }
}

