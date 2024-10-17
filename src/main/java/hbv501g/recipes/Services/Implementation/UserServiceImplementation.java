package hbv501g.recipes.Services.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hbv501g.recipes.Persistence.Repositories.UserRepository;
import hbv501g.recipes.Services.UserService;
import hbv501g.recipes.Persistence.Entities.*;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> initUsers() {
        List<User> AllUsers = findAll();

        if (AllUsers.size() == 0) {
            User user = new User("Jón", "jon123", "jon123@gmail.com");
            save(user);

            user = new User("Superman", "123", "superman@gmail.com");
            save(user);

            user = new User("admin", "admin", "admin@hi.is");
            save(user);

            AllUsers = findAll();
        }

        return AllUsers;
    }

    @Override
    public User findByID(Long id) {
        return userRepository.findByID(id);
    }

    public User update(User updatedUser) {
        return userRepository.save(updatedUser);
    }

}
