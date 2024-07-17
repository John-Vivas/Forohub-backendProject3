package com.forohub.apiRest.domain.repostory;

import com.forohub.apiRest.domain.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByUsername(String username);
}
