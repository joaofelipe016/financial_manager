package com.project.financial_management.service;

import com.project.financial_management.dto.UsuarioDTO;
import com.project.financial_management.entity.Carteira;
import com.project.financial_management.entity.Pessoa;
import com.project.financial_management.entity.Role;
import com.project.financial_management.entity.Usuario;
import com.project.financial_management.enums.Roles;
import com.project.financial_management.repository.CarteiraRepository;
import com.project.financial_management.repository.RoleRepository;
import com.project.financial_management.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final CarteiraRepository carteiraRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, CarteiraRepository carteiraRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.carteiraRepository = carteiraRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        Pessoa pessoa = buildPessoa(usuarioDTO);
        Usuario usuario = buildUsuario(usuarioDTO, pessoa);
        this.usuarioRepository.save(usuario);
        Carteira carteira = new Carteira();
        carteira.setUsuario(usuario);
        this.carteiraRepository.save(carteira);
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

    public UsuarioDTO update(UUID idUsuario, UsuarioDTO usuarioDTO) {
        return this.usuarioRepository.findById(idUsuario)
            .map(usuario -> {
                Usuario usuarioAtualizado = populateUsuario(usuario, usuarioDTO);
                this.usuarioRepository.save(usuarioAtualizado);
                return usuarioDTO;
            }).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    private Usuario populateUsuario(Usuario usuario, UsuarioDTO usuarioDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNmPessoa(usuarioDTO.pessoa().getNmPessoa());
        pessoa.setNrCpf(usuarioDTO.pessoa().getNrCpf());

        usuario.setScUsuario(usuarioDTO.scUsuario());
        usuario.setScSenha(usuario.getScSenha());
        usuario.setPessoa(pessoa);  
        return usuario;
    }
}
