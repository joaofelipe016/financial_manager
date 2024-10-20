package com.project.financial_management.dto;

import com.project.financial_management.entity.Pessoa;
import com.project.financial_management.entity.Role;

public record UsuarioDTO(String scUsuario, String scSenha, Pessoa pessoa) {
}
