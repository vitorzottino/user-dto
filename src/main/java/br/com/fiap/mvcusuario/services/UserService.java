package br.com.fiap.mvcusuario.services;

import br.com.fiap.mvcusuario.dto.UserDTO;
import br.com.fiap.mvcusuario.models.User;
import br.com.fiap.mvcusuario.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {

        User user =  repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Recurso não encontrado")
        );

        return new UserDTO(user);

    }

    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {

        List<User> user = repository.findAll();

        return user.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public UserDTO insert(UserDTO dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setMoment(Instant.now());
        entity = repository.save(entity);
        return  new UserDTO(entity);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        User entity = repository.getReferenceById(id);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDTO(entity);

    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Usuário inválido - id: " + id);
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(UserDTO dto, User entity) {

        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity.setDataNascimento(dto.getDataNascimento());



    }

}
