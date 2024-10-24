package com.project.financial_management.controller;

import com.project.financial_management.dto.UsuarioDTO;
import com.project.financial_management.entity.Usuario;
import com.project.financial_management.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Transactional
    @PostMapping("")
    public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTO usuarioDTO) {
        this.usuarioService.create(usuarioDTO);
        return ResponseEntity.ok(usuarioDTO);
    }

    @Transactional
    @PutMapping(path = "/{idUsuario}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<UsuarioDTO> update(@PathVariable UUID idUsuario, @RequestBody UsuarioDTO usuarioDTO) {
        var usuario = this.usuarioService.update(idUsuario, usuarioDTO);
        return ResponseEntity.ok(usuario);
    }
}
