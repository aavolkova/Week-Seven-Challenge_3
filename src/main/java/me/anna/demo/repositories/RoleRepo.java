package me.anna.demo.repositories;

import me.anna.demo.models.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepo extends CrudRepository<Role, Long> {
    Role findByRole(String r);
}

