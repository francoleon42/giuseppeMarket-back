package com.giuseppemarket.testUnitario.controller;

import com.giuseppemarket.controller.AuthController;
import com.giuseppemarket.dto.login.LoginRequestDTO;
import com.giuseppemarket.dto.login.LoginResponseDTO;
import com.giuseppemarket.dto.login.RegisterRequestDTO;
import com.giuseppemarket.service.IAuthService;
import com.giuseppemarket.utils.enums.Rol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private IAuthService authService;

    @Test
    void testLogin() {

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

        LoginResponseDTO loginResponse = LoginResponseDTO.builder()
                .role(Rol.ADMINISTRADOR)
                .username("admin")
                .token("abc")
                .build();


        when(authService.login(any(LoginRequestDTO.class))).thenReturn(loginResponse);

        ResponseEntity<?> response = authController.login(loginRequestDTO);

        verify(authService, times(1)).login(loginRequestDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponse, response.getBody());
    }

    @Test
    void testRegister() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();

        ResponseEntity<?> response = authController.register(registerRequestDTO);

        verify(authService, times(1)).register(registerRequestDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void testLogout() {
        String token = "someToken";
        ResponseEntity<?> response = authController.logout(token);

        verify(authService, times(1)).logout(token);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
