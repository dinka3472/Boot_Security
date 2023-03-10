package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailService;

import java.util.List;

@Controller
public class AdminController {
    private UserDetailService userService;
    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setUserService(UserDetailService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "index";
    }

    @GetMapping("/admin/allUsers")
    public String showAllUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "show_users";
    }

    @GetMapping("/{id}/update")
    public ModelAndView updateUser(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        ModelAndView mav = new ModelAndView("update_user");
        mav.addObject("user", user);
        List<Role> roles = roleService.getRoles();
        mav.addObject("allRoles", roles);
        return mav;
    }
    @DeleteMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/admin/addUser")
    public ModelAndView addUser() {
        User user = new User();
        List<Role> roles = roleService.getRoles();
        ModelAndView mav = new ModelAndView("add_user");
        mav.addObject("user", user);
        mav.addObject("allRoles", roles);
        return mav;
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin/allUsers";
    }

    @PatchMapping("/updateUser")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/allUsers";
    }
}
