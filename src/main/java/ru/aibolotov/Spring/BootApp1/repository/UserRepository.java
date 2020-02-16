package ru.aibolotov.Spring.BootApp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aibolotov.Spring.BootApp1.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findAllByName(String name);
}
