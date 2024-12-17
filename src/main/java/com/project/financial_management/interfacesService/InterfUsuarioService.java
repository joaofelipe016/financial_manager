package com.project.financial_management.interfacesService;

import com.project.financial_management.dto.UsuarioDTO;

import java.util.UUID;

public interface InterfUsuarioService {

    public UsuarioDTO create(UsuarioDTO usuarioDTO) throws Exception;
    public UsuarioDTO update(UUID idUsuario, UsuarioDTO usuarioDTO) throws Exception;
}
