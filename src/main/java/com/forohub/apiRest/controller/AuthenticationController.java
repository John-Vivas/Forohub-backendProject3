package com.forohub.apiRest.controller;

import com.forohub.apiRest.domain.dto.AuthenticationUserDTO;
import com.forohub.apiRest.domain.dto.JWTokenDTO;
import com.forohub.apiRest.domain.model.Usuario;
import com.forohub.apiRest.domain.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(
            @RequestBody @Valid AuthenticationUserDTO authenticationUserDTO) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationUserDTO.usuario(),
                authenticationUserDTO.password());
        var authentication = authenticationManager.authenticate(authenticationToken);
        var JWToken = tokenService.generarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new JWTokenDTO(JWToken));
    }
}