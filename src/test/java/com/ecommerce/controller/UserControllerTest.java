package com.ecommerce.controller;

import com.ecommerce.config.JwtAuthenticationFilter;
import com.ecommerce.config.JwtService;
import com.ecommerce.config.SecurityConfig;
import com.ecommerce.dto.user.UserProfileResponse;
import com.ecommerce.entity.Role;
import com.ecommerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(
    controllers = UserController.class,
    properties = {
        "application.security.jwt.secret=404E635266556A586E3272357538782F413F4428472B4B6250655368566D5971",
        "application.security.jwt.expiration=86400000"
    }
)
@Import({SecurityConfig.class, JwtAuthenticationFilter.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @MockitoBean
    private AuthenticationProvider authenticationProvider;

    
    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_AsAdmin_Returns200OK() throws Exception {
        UserProfileResponse profile = UserProfileResponse.builder()
                .id(1L)
                .email("admin@ecommerce.com")
                .role(Role.ROLE_ADMIN)
                .enabled(true)
                .build();

        when(userService.getAllUsers(any()))
                .thenReturn(new PageImpl<>(List.of(profile), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].email").value("admin@ecommerce.com"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllUsers_AsUser_Returns403Forbidden() throws Exception {
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isForbidden());
    }
}
// package com.ecommerce.controller;

// import com.ecommerce.config.JwtAuthenticationFilter;
// import com.ecommerce.config.JwtService;
// import com.ecommerce.config.SecurityConfig;
// import com.ecommerce.dto.user.UserProfileResponse;
// import com.ecommerce.entity.Role;
// import com.ecommerce.service.UserService;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
// import org.springframework.context.annotation.Import;

// import java.util.List;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @Import({ SecurityConfig.class, JwtAuthenticationFilter.class }) // Include security setup
// @WebMvcTest(UserController.class)
// @ActiveProfiles("test")
// class UserControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockitoBean
//     private UserService userService;

//     // Standard Spring Security Mocks when WebMvcTest auto-configures security
//     @MockitoBean
//     private JwtService jwtService;

//     @MockitoBean
//     private UserDetailsService userDetailsService;
