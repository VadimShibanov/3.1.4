package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;

public interface UserService {

    public void add(User user);

    public List<User> findAllUsers();

    public User findOne(Integer id);

    public void update(User user, Integer id);

    public void delete(Integer id);
    public List<Role> listRoles();
}
