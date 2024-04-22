package br.com.fiap.mvcusuario.dto;


import br.com.fiap.mvcusuario.models.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MinUserDTO {

    private Long id;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "Campo requerido")
    private String email;

    private Instant moment;

    @NotBlank(message = "Campo requerido")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    public MinUserDTO(User entity) {
        id = entity.getId();
        email = entity.getEmail();
        moment = entity.getMoment();
        senha = entity.getSenha();
    }
}
