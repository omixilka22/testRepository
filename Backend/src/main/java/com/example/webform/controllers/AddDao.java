package com.example.webform.controllers;

import com.example.webform.dao.ContainerA;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.webform.model.Person;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class AddDao {

    private ContainerA container;

    @Autowired
    public AddDao(ContainerA container) {
        this.container = container;
    }

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("people" , container.getPeople());
        return "index";
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        System.out.println("Index page called!");
        model.addAttribute("people", container.getPeople());
        return "index"; // шукає index.html у /templates
    }

    @GetMapping("/form")
    public String newPerson(Model model) {
        model.addAttribute("person" , new Person());
        return "form";
    }

    @GetMapping("/successPage")
    public String successPage() {
        return "successPage"; // шукає successPage.html у /templates
    }

    @PostMapping("/people")
    public String addPerson(@ModelAttribute("person") @Valid Person person ,  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { return "form"; }
        container.save(person);
        return "redirect:/successPage";
    }

    @GetMapping("/{id}/show")
    public String showPerson(Model model , @PathVariable("id") int id) {
        model.addAttribute("person" , container.getPerson(id));
        return "show";
    }

//    @GetMapping("/{id}/edit")
//    public String editPerson(@PathVariable("id") int id, Model model ,
//                             @ModelAttribute("person") Person person) {
//        model.addAttribute("person", person);
//        return "edit";
//    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        Person person = container.getPerson(id); // отримуємо з контейнера/бази
        model.addAttribute("person", person);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        container.update(person , id); // оновлюємо дані у контейнері
        return "redirect:/"; // повертаємося до списку людейe
    }


//    @PatchMapping("/{id}")
//    public String updatePerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person ,
//                               BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) { return "form"; }
//        container.update(person, id);
//        return "redirect:/";
//    }

}
