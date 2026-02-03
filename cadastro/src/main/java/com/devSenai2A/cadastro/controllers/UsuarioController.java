package com.devSenai2A.cadastro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devSenai2A.cadastro.entities.Usuario;
import com.devSenai2A.cadastro.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

@Autowired
private UsuarioService service;

@PostMapping
public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
return ResponseEntity 
		.status(HttpStatus.CREATED)
		.body(service.cadastrar(usuario));
}
}