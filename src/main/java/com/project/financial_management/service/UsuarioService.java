package com.project.financial_management.service;

import com.project.financial_management.dto.UsuarioDTO;
import com.project.financial_management.entity.Pessoa;
import com.project.financial_management.entity.Role;
import com.project.financial_management.entity.Usuario;
import com.project.financial_management.enums.Roles;
import com.project.financial_management.repository.RoleRepository;
import com.project.financial_management.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Pessoa pessoa = buildPessoa(usuarioDTO);
        Usuario usuario = buildUsuario(usuarioDTO, pessoa);
        this.usuarioRepository.save(usuario);
        return usuarioDTO;
    }

    private Usuario buildUsuario(UsuarioDTO usuarioDTO, Pessoa pessoa) {
        var basicRole = this.roleRepository.findByNmRole(Roles.BASIC.name());
        Usuario usuario = new Usuario();
        usuario.setScUsuario(usuarioDTO.scUsuario());
        usuario.setScSenha(passwordEncoder.encode(usuarioDTO.scSenha()));
        usuario.setPessoa(pessoa);
        usuario.setRole(Set.of(basicRole));
        return usuario;
    }

    private Pessoa buildPessoa(UsuarioDTO usuarioDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNmPessoa(usuarioDTO.pessoa().getNmPessoa());
        pessoa.setNrCpf(usuarioDTO.pessoa().getNrCpf());
        return pessoa;
    }

}
