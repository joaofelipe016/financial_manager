package com.project.financial_management.config;

import com.project.financial_management.entity.Pessoa;
import com.project.financial_management.entity.Usuario;
import com.project.financial_management.enums.Roles;
import com.project.financial_management.repository.RoleRepository;
import com.project.financial_management.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Configuration
public class AdminConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminConfig(RoleRepository roleRepository, UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception{

        var roleAdmin = this.roleRepository.findByNmRole(Roles.ADMIN.name());

        var usuarioAdmin = this.usuarioRepository.findByScUsuario("admin");

        usuarioAdmin.ifPresentOrElse(
            usuario -> {
                System.out.println("admin já existe!");
            },
            () -> {
                var pessoa = new Pessoa();
                pessoa.setNmPessoa("João Felipe de Souza");
                pessoa.setNrCpf("374.100.068-03");

                var usuario = new Usuario();
                usuario.setScUsuario("admin");
                usuario.setScSenha(passwordEncoder.encode("cto14porta7"));
                usuario.setRole(Set.of(roleAdmin));
                usuario.setPessoa(pessoa);

                this.usuarioRepository.save(usuario);
            }
        );
    }
}
