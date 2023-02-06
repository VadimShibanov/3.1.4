package web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import web.model.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u join fetch u.roles where u.email = :email")
    public User findByEmail(String email);
}
