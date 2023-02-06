package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repositories.RolesRepository;
import web.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Transactional
    public void add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        usersRepository.save(user);
    }

    public List<User> findAllUsers() {
        return usersRepository.findAll();
    }

    public User findOne(Integer id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null);
    }

    @Transactional
    public void update(User updatedUser, Integer id) {
        updatedUser.setUsername(updatedUser.getUsername());
        updatedUser.setLastname(updatedUser.getLastname());
        updatedUser.setAge(updatedUser.getAge());
        updatedUser.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword().length() != bCryptPasswordEncoder.encode(updatedUser.getPassword()).length()) {
            updatedUser.setPassword(bCryptPasswordEncoder.encode(updatedUser.getPassword()));
        }

        usersRepository.save(updatedUser);
    }

    @Transactional
    public void delete(Integer id) {
        usersRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = usersRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public User findByName(String username) {
        return usersRepository.findByEmail(username);
    }

    public List<Role> listRoles() {
        return rolesRepository.findAll();
    }
}
