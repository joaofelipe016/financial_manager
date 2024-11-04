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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CarteiraRepository carteiraRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        UsuarioDTO usuarioDTO = criarUsuarioDTO();
        Role basicRole = new Role();
        basicRole.setNmRole(Roles.BASIC.name());

        when(roleRepository.findByNmRole(Roles.BASIC.name())).thenReturn(basicRole);
        when(passwordEncoder.encode(anyString())).thenReturn("senhaEncriptada");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioDTO resultado = usuarioService.create(usuarioDTO);

        assertNotNull(resultado);
        assertEquals(usuarioDTO.scUsuario(), resultado.scUsuario());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testUpdate() {
        UUID idUsuario = UUID.randomUUID();
        UsuarioDTO usuarioDTO = criarUsuarioDTO();
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(idUsuario);

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UsuarioDTO resultado = usuarioService.update(idUsuario, usuarioDTO);

        assertNotNull(resultado);
        assertEquals(usuarioDTO.scUsuario(), resultado.scUsuario());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testUpdateUsuarioNaoEncontrado() {
        UUID idUsuario = UUID.randomUUID();
        UsuarioDTO usuarioDTO = criarUsuarioDTO();

        when(usuarioRepository.findById(idUsuario)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usuarioService.update(idUsuario, usuarioDTO));
    }

    private UsuarioDTO criarUsuarioDTO() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNmPessoa("Teste");
        pessoa.setNrCpf("12345678900");

        return new UsuarioDTO("usuario_teste", "senha123", pessoa);
    }
}