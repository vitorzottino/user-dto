package br.com.fiap.mvcusuario.controllers;

import br.com.fiap.mvcusuario.dto.UserDTO;
import br.com.fiap.mvcusuario.models.User;
import br.com.fiap.mvcusuario.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {

   @Autowired
   private UserService service;

    @GetMapping("/form")
    public String loadForm(Model model) {
        model.addAttribute("userDto", new UserDTO());
        return "usuarios/novo-usuario";
    }

    @PostMapping()
    public String insert(@Valid UserDTO userDto,
                         BindingResult result,
                         RedirectAttributes attributes) {
        if(result.hasErrors()){
            return "usuarios/novo-usuario";
        }
        userDto = service.insert(userDto);
        attributes.addFlashAttribute("mensagem", "Produto salvo com sucesso");
        return "redirect:/users/form";
    }

    @GetMapping()
    public String findAll(Model model){
        model.addAttribute("usersDto", service.findAll());
        return "/usuarios/listar-usuarios";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model ){
        UserDTO userDto = service.findById(id);
        model.addAttribute("userDto", userDto);
        return "/usuarios/editar-usuario";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable("id") Long id,
                         @Valid UserDTO userDto,
                         BindingResult result){
        if(result.hasErrors()){
            userDto.setId(id);
            return "/usuarios/editar-usuario";
        }
        userDto = service.update(id, userDto);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "redirect:/users";
    }
}
