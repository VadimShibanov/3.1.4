package web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.repositories.UsersRepository;
import web.service.UserServiceImpl;
import web.util.UserValidator;

import java.security.Principal;
import java.util.List;


@Controller
public class UserAndAdminController {

    private final UserValidator userValidator;
    private final UserServiceImpl userService;
    private final UsersRepository usersRepository;

    @Autowired
    public UserAndAdminController(UserValidator userValidator, UserServiceImpl userService, UsersRepository usersRepository) {
        this.userValidator = userValidator;
        this.userService = userService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/")
    public String loginPage() {
        return "/login_page";
    }

    @GetMapping("/user")
    public String userInfo(Principal principal, Model model) {
        List<User> allUsers = userService.findAllUsers();
        int val = 0;
        User userToShow = new User();
        for (User user : allUsers) {
            if (val == principal.getName().compareTo(user.getUsername())) {
                userToShow.setEmail(user.getEmail());
                userToShow.setRoles(user.getRoles());
                userToShow.setAge(user.getAge());
                userToShow.setId(user.getId());
                userToShow.setUsername(user.getUsername());
                userToShow.setLastname(user.getLastname());
            }
        }
        model.addAttribute("userToShow", userToShow);
        return "/user";
    }

    @GetMapping("/admin")
    public String index(ModelMap model, Principal principal) {
        List<User> allUsers = userService.findAllUsers();
        List<Role> listRoles = userService.listRoles();
        int val = 0;
        User userToShow = new User();
        for (User user : allUsers) {
            if (val == principal.getName().compareTo(user.getUsername())) {
                userToShow.setEmail(user.getEmail());
                userToShow.setRoles(user.getRoles());
                userToShow.setAge(user.getAge());
                userToShow.setId(user.getId());
                userToShow.setUsername(user.getUsername());
                userToShow.setLastname(user.getLastname());
            }
        }
        model.addAttribute("users", allUsers);
        model.addAttribute("userToShow", userToShow);
        model.addAttribute("listRoles", listRoles);
        return "/admin";
    }

    @GetMapping("/new")
    public String NewUser(@ModelAttribute("user") User user) {
        return "/new";
    }

    @PostMapping("/")
    public String create(@ModelAttribute("user") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/findOne")
    @ResponseBody
    public User findOne(@PathVariable("id") Integer id) {
        return userService.findOne(id);
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @PatchMapping("/{id}/update")
    public String update(User user, Integer id) {
        userService.update(user, id);
        return "redirect:/admin";
    }
}


