package com.example.netfriquis.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class APIKeyFilterTests {

    @Test
    void devePermitirAcessoComChaveCorreta() throws Exception {
        APIKeyFilter filter = new APIKeyFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        request.addHeader("X-API-KEY", "I_Love_Milfs");

        filter.doFilterInternal(request, response, chain);

        // Verifica se o filtro deixou a requisição seguir adiante
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    void deveNegarAcessoComChaveIncorreta() throws Exception {
        APIKeyFilter filter = new APIKeyFilter();
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);

        request.addHeader("X-API-KEY", "Chave_Errada");

        filter.doFilterInternal(request, response, chain);

        // Verifica se retornou 401 Unauthorized
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
        // Verifica se a corrente de filtros foi interrompida
        verify(chain, never()).doFilter(request, response);
    }
}