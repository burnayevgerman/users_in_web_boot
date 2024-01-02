package web.application.users.controllers;

import jakarta.validation.Valid;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.application.users.models.User;
import web.application.users.services.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService service;

    @Autowired
    public UsersController(UserService service) {
        this.service = service;
    }

    @GetMapping("/list")
    public String sendUsers(Model model) {
        model.addAttribute("users", service.getUsers());
        return "users";
    }

    @GetMapping("/user-info")
    public String sendUserInfo(@RequestParam(value = "id") long id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "user_info";
    }

    @GetMapping("/create")
    public String sendFormForCreate(Model model) {
        model.addAttribute("user", new User());
        return "create_user";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult br) {
        if (br.hasErrors()) {
            return "create_user";
        }

        service.addUser(user);
        return "user_info";
    }

    @GetMapping("/edit-user")
    public String sendFormForEdit(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", service.getUserById(id));
        return "edit_user";
    }

    @PutMapping("/edit-user")
    public String editUser(@ModelAttribute("user") @Valid User user, @RequestParam("id") long id, BindingResult br) {
        if (br.hasErrors()) {
            return "edit_user";
        }


        service.updateUserById(id, user);
        return "user_info";
    }

    @GetMapping("/delete-user")
    public String sendFormForDelete(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        return "delete_user";
    }

    @DeleteMapping("/delete-user")
    public String deleteUser(@ModelAttribute("id") long id) {
        service.removeUserById(id);
        return "deletion_completed";
    }

}
