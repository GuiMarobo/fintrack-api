package com.guimarobo.Fintrack.service;

import com.guimarobo.Fintrack.exception.NotFoundException;
import com.guimarobo.Fintrack.model.User;
import com.guimarobo.Fintrack.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public User save(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }

        return userRepository.save(user);
    }


    public User update(Long id, User updatedUser) {
        User existingUser = findById(id);
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        return userRepository.save(existingUser);
    }

    public void delete(Long id){
        findById(id);
        userRepository.deleteById(id);
    }
}
