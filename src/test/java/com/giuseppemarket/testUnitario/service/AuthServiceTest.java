package com.giuseppemarket.testUnitario.service;

import com.giuseppemarket.config.jwt.JwtService;
import com.giuseppemarket.dto.login.*;
import com.giuseppemarket.exception.NotFoundException;
import com.giuseppemarket.exception.RegisterException;
import com.giuseppemarket.model.Usuario;
import com.giuseppemarket.repository.IUsuarioRepository;
import com.giuseppemarket.service.impl.AuthServiceImpl;
import com.giuseppemarket.utils.enums.EstadoUsuario;
import com.giuseppemarket.utils.enums.Rol;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceImplTest {

    @Mock
    private IUsuarioRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;


    @InjectMocks
    private AuthServiceImpl authService;

    private final String password = "1234"; // Contraseña fija
    private final String encodedPassword = "encodedPassword"; // Simulación de la contraseña encriptada

    private final String mockedJwtToken = "mockedJwtToken";

    // Usuarios
    private final String adminUsername = "admin";
    private final String supervisorUsername = "supervisor";
    private final String operadorUsername = "operador";
    private final String gerenteUsername = "gerente";

    private UpdateRequestDTO updateUsuarioDTO;
    private Usuario usuario;
    private Usuario chofer;
    private List<Usuario> usuarios;

    @BeforeEach
    void setUp() {
        updateUsuarioDTO = UpdateRequestDTO.builder()
                .username("usuario")
                .password("password")
                .build();
        chofer = Usuario.builder()

                .id(1)
                .rol(Rol.CHOFER)
                .estadoUsuario(EstadoUsuario.HABILITADO)
                .build();
        usuario = Usuario.builder()
                .id(2)
                .estadoUsuario(EstadoUsuario.INHABILITADO)
                .contrasena("asdsa")
                .rol(Rol.ADMINISTRADOR)
                .build();
        usuarios = List.of(usuario, chofer);
    }

    @Test
    void habilitar_lanzaNotFoundException() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> authService.habilitar(anyInt()));

        verify(userRepository, never()).save(any(Usuario.class));

    }

    @Test
    void habilitar_cambiaEstadoDelUsuarioAhabilitado() {
        when(userRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        authService.habilitar(usuario.getId());

        assertEquals(usuario.getEstadoUsuario(), EstadoUsuario.HABILITADO);
        verify(userRepository, times(1)).save(any(Usuario.class));

    }

    @Test
    void inhabilitar_lanzaNotFoundException() {
        when(userRepository.findById(usuario.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> authService.inhabilitar(usuario.getId()));

        verify(userRepository, never()).save(any(Usuario.class));
    }


    @Test
    void getAll_devuelveTodosLosUsuarios() {
        when(userRepository.findAll()).thenReturn(usuarios);
        List<GetUserDTO> usuariosRegistrados = authService.getAll();

        assertEquals(usuariosRegistrados.size(), usuarios.size());
        assertEquals(usuariosRegistrados.get(0).getId(), usuarios.get(0).getId());
        assertEquals(usuariosRegistrados.get(1).getId(), usuarios.get(1).getId());
    }

    @Test
    void updateUsuarioInvalido_lanzaNotFounException() {
        when(userRepository.findById(usuario.getId())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> authService.update(usuario.getId(), updateUsuarioDTO));

        verify(userRepository, never()).save(any(Usuario.class));
    }

    @Test
    void update_seActualizaUsuario() {
        when(userRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.encode(updateUsuarioDTO.getPassword())).thenReturn(updateUsuarioDTO.getPassword());
        authService.update(usuario.getId(), updateUsuarioDTO);

        assertEquals(updateUsuarioDTO.getUsername(), usuario.getUsername());
        assertEquals(updateUsuarioDTO.getPassword(), usuario.getPassword());
        verify(userRepository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testLogin_Success_Admin() {
        loginTestHelper(adminUsername, Rol.ADMINISTRADOR);
    }

    @Test
    void testRegister_Success_Vendedor() {
        registerTestHelper(gerenteUsername, Rol.VENDEDOR);
    }

    private void loginTestHelper(String username, Rol role) {
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);
        Usuario user = new Usuario();
        user.setUsuario(username);
        user.setContrasena(encodedPassword);
        user.setRol(role);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null); // Simular autenticación exitosa
        when(userRepository.findByUsuario(username)).thenReturn(Optional.of(user));
        when(jwtService.getToken(user)).thenReturn(mockedJwtToken);

        LoginResponseDTO response = authService.login(loginRequest);

        assertEquals(username, response.getUsername());
        assertEquals(mockedJwtToken, response.getToken());
        assertEquals(role, response.getRole());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsuario(username);
        verify(jwtService).getToken(user);
    }

    private void registerTestHelper(String username, Rol role) {
        RegisterRequestDTO registerRequest = new RegisterRequestDTO();
        registerRequest.setUsername(username);
        registerRequest.setPassword(password);
        registerRequest.setRole(role.name());
        Usuario user = new Usuario();
        user.setUsuario(username);
        user.setContrasena(password);
        user.setRol(role);

        when(userRepository.existsByUsuario(username)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
        when(jwtService.getToken(any(Usuario.class))).thenReturn(mockedJwtToken);


        LoginResponseDTO response = authService.register(registerRequest);

        assertEquals(username, response.getUsername());
        assertEquals(mockedJwtToken, response.getToken());
        assertEquals(role, response.getRole());
        verify(userRepository).save(any(Usuario.class));
    }

    @Test
    void testLoginUsuarioNoExiste() {
        String usuarioInexistente = "unknown";
        LoginRequestDTO loginRequest = new LoginRequestDTO();
        loginRequest.setUsername(usuarioInexistente);
        loginRequest.setPassword(password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByUsuario(usuarioInexistente)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> authService.login(loginRequest));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository).findByUsuario(usuarioInexistente);
    }

    @Test
    void testRegisterUsuarioYaExiste() {
        String usuarioAdmin = adminUsername;
        RegisterRequestDTO registerRequest = new RegisterRequestDTO(usuarioAdmin, password, Rol.ADMINISTRADOR.toString());

        when(userRepository.existsByUsuario(usuarioAdmin)).thenReturn(true);

        assertThrows(RegisterException.class, () -> authService.register(registerRequest));
        verify(userRepository, never()).save(any(Usuario.class));
    }


}