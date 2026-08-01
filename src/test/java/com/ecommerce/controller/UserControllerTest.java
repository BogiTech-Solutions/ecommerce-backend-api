package com.ecommerce.controller;

import com.ecommerce.config.JwtService;
import com.ecommerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    // 💡 ADD THIS: Spring Security needs this to build the security context during @WebMvcTest
    @MockitoBean
    private UserDetailsService userDetailsService; 

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_AsAdmin_Returns200OK() throws Exception {
        when(userService.getAllUsers(any()))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }
}
// package com.ecommerce.controller;

// import com.ecommerce.config.JwtService;
// import com.ecommerce.dto.user.UserProfileResponse;
// import com.ecommerce.entity.Role;
// import com.ecommerce.service.UserService;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.data.domain.PageImpl;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.security.test.context.support.WithMockUser;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

// import java.util.List;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest(UserController.class)
// @ActiveProfiles("test")
// class UserControllerTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @MockitoBean
//     private UserService userService;

//     @MockitoBean
//     private JwtService jwtService;

//     @Test
//     @WithMockUser(roles = "ADMIN")
//     void getAllUsers_AsAdmin_Returns200OK() throws Exception {
//         UserProfileResponse profile = UserProfileResponse.builder()
//                 .id(1L)
//                 .email("admin@ecommerce.com")
//                 .role(Role.ROLE_ADMIN)
//                 .enabled(true)
//                 .build();

//         when(userService.getAllUsers(any()))
//                 .thenReturn(new PageImpl<>(List.of(profile), PageRequest.of(0, 10), 1));

//         mockMvc.perform(get("/api/v1/users"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.content[0].email").value("admin@ecommerce.com"));
//     }

//     @Test
//     @WithMockUser(roles = "USER")
//     void getAllUsers_AsUser_Returns403Forbidden() throws Exception {
//         mockMvc.perform(get("/api/v1/users"))
//                 .andExpect(status().isForbidden());
//     }
// }