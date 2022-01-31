package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.exception.EntityNotFoundException;
import ua.com.alevel.persistence.entity.user.User;
import ua.com.alevel.persistence.repository.UserRepository;
import ua.com.alevel.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(User entity) {
        userRepository.save(entity);
    }

    @Override
    public void update(User entity) {
        if (!userRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("not found...");
        }
        userRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("not found...");
        }
        userRepository.deleteById(id);
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        else
            throw new EntityNotFoundException("not found...");
    }

    @Override
    public DataTableResponse<User> findAll(DataTableRequest request) {
        return null;
    }
}
