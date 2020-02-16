package ru.aibolotov.Spring.BootApp1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.aibolotov.Spring.BootApp1.model.Role;
import ru.aibolotov.Spring.BootApp1.model.User;
import ru.aibolotov.Spring.BootApp1.repository.RoleRepository;
import ru.aibolotov.Spring.BootApp1.repository.UserRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    static final String USER_LIST = "/list";
    static final String USER_FORM = "/user-form";

    @GetMapping("/index")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name");
        return "index";
    }

    @RequestMapping(value = USER_LIST)
    public String messages(Model model) {
        model.addAttribute("userList", userRepository.findAll());

        return USER_LIST;
    }

    @RequestMapping(value = "/update")
    public String update(@RequestParam("userId") long id, Model model) {
        if (id == -1) {
            User user = new User();
            model.addAttribute("user", user);
            return USER_FORM;
        }
        List<Role> roles = roleRepository.findAll();
        User user = userRepository.getOne(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return USER_FORM;
    }

    @RequestMapping(value = "/saveUser")
    public String saveUser(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("roles") Set<Role> role) {
        Set<Role> roleSet = new HashSet<>();
//        for (int i = 0; i < size-1; i++) {
//            roleSet.add(roleRepository.findAllByRole(role[i]));
//        }

//        for (String roleName : role) {
//            roleSet.add(roleRepository.findAllByRole(roleName));
//        }
        if (userRepository.findAllByName(name) != null) {
            User user = userRepository.findAllByName(name);

            user.setRoles(role);
            userRepository.save(user);
        } else {
            User user = new User(name, password, roleSet);
            userRepository.save(user);
        }
        return "redirect:/list";
    }

    @RequestMapping(value = "/delete")
    public String delete(@RequestParam("userId") long id, Model model) {
        userRepository.deleteById(id);
        return "redirect:/list";
    }

    @RequestMapping(value = "/addUser")
    public String addUser(@RequestParam("userId") long id, Model model) {
        Set<Role> userRoles = new HashSet<>(roleRepository.findAll());
        List<User> userList = userRepository.findAll();
        int i = userList.size();
        Long idLast = userList.get(i - 1).getId();
        User user = new User();
        user.setId(idLast + 1);
        user.setName("");
        user.setPassword("");
        user.setRoles(userRoles);

        model.addAttribute("user", user);
        model.addAttribute("roles", userRoles);
        return USER_FORM;
    }

}
