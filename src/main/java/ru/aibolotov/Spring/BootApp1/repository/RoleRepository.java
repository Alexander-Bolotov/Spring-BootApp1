package ru.aibolotov.Spring.BootApp1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aibolotov.Spring.BootApp1.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findAllByRole(String role);
}
