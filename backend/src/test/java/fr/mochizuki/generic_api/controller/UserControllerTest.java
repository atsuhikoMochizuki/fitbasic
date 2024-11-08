package fr.mochizuki.generic_api.controller;

import static fr.mochizuki.generic_api.ConstantsForTests.REFERENCE_USER_NAME;
import static fr.mochizuki.generic_api.ConstantsForTests.REFERENCE_USER_PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.mochizuki.generic_api.cross_cutting.constants.Endpoint;
import fr.mochizuki.generic_api.cross_cutting.enums.RoleEnum;
import fr.mochizuki.generic_api.cross_cutting.security.impl.JwtServiceImpl;
import fr.mochizuki.generic_api.dto.ProtectedUserResponseDto;
import fr.mochizuki.generic_api.dto.PublicUserResponseDto;
import fr.mochizuki.generic_api.dto.UserRequestDto;
import fr.mochizuki.generic_api.entity.Role;
import fr.mochizuki.generic_api.entity.User;
import fr.mochizuki.generic_api.service.impl.UserServiceImpl;

/**
 * Unit tests of User controller layer
 *
 * @author A.Mochizuki
 * @date 2024-06-11
 */
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UserControllerTest {

        /* DEPENDENCIES INJECTION */
        /* ============================================================ */

        @Autowired
        private MockMvc mockMvc;

        /* ObjectMapper provide functionalities for read and write JSON data's */
        @Autowired
        private ObjectMapper objectMapper;

        @MockBean
        private UserServiceImpl userService;

        @MockBean
        private AuthenticationManager authenticationManager;
        @MockBean
        private JwtServiceImpl jwtServiceImpl;

        /* REFERENCES FOR MOCKING */
        /* ============================================================ */
        Role roleForTest = Role.builder().name(RoleEnum.ADMIN).build();

        private User userRef = User.builder()
                        .email(REFERENCE_USER_NAME)
                        .name(REFERENCE_USER_NAME)
                        .password(REFERENCE_USER_PASSWORD)
                        .role(roleForTest)
                        .build();

        private User userRef2 = User.builder()
                        .email("piccolo@namek.org")
                        .name("pic-picSenzuFreez")
                        .password("Picc@@lO128!")
                        .role(roleForTest)
                        .build();

        /* FIXTURES */
        /* ============================================================ */
        // @BeforeEach
        // void init() {}

        /* CONTROLLER UNIT TEST */
        /* ============================================================ */
        @Test
        @DisplayName("Get by username an existing user")
        void getUser_ShouldSuccess_WhenUserExists() throws Exception {

                /* Arrange */
                when(this.userService.loadUserByUsername(this.userRef.getUsername()))
                                .thenReturn(this.userRef);

                /* Act */
                UserRequestDto userRequestDto = new UserRequestDto(this.userRef.getUsername());

                ResultActions response = this.mockMvc.perform(post(Endpoint.USER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(this.objectMapper.writeValueAsString(userRequestDto)))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                /* Assert */
                String serializedResponse = response.andReturn().getResponse().getContentAsString();
                PublicUserResponseDto parsedResponse = this.objectMapper.readValue(serializedResponse,
                                new TypeReference<PublicUserResponseDto>() {
                                });

                assertThat(parsedResponse.pseudonyme()).isEqualTo(this.userRef.getPseudonyme());
                assertThat(parsedResponse.username()).isEqualTo(this.userRef.getUsername());
                assertThat(parsedResponse.avatar()).isEqualTo(this.userRef.getAvatar());
                assertThat(parsedResponse.actif()).isEqualTo(this.userRef.isActif());
                assertThat(parsedResponse.role()).isEqualTo(this.userRef.getRole());
        }

        @Test
        @DisplayName("Get by username an non-existing user")
        void getUser_ShouldFailed_WhenUserNotExists() throws Exception {

                /* Arrange */
                when(this.userService.loadUserByUsername(anyString()))
                                .thenThrow(UsernameNotFoundException.class);

                /* Act */
                UserRequestDto userRequestDto = new UserRequestDto("quisuije@dansqueletatjerre.fr");

                this.mockMvc.perform(post(Endpoint.USER)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(this.objectMapper.writeValueAsString(userRequestDto)))
                                .andExpect(result -> assertTrue(
                                                result.getResolvedException() instanceof UsernameNotFoundException))
                                .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("Get all users when at least one is present")
        void list_ShouldSuccess_WhenAnUserExists() throws Exception {

                /* Arrange */
                List<User> users = new ArrayList<>();
                users.add(this.userRef);
                users.add(this.userRef2);
                List<ProtectedUserResponseDto> protectedUserDtos = new ArrayList<>();
                for (User item : users) {
                        protectedUserDtos.add(
                                        new ProtectedUserResponseDto(
                                                        item.getName(),
                                                        item.getEmail(),
                                                        item.isActif(),
                                                        item.getPseudonyme(),
                                                        item.getAvatar(),
                                                        item.getRole().getName().toString()));
                }

                when(this.userService.list()).thenReturn(users);

                /* Act & assert */
                ResultActions response = this.mockMvc.perform(get(Endpoint.GET_ALL_USERS))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                String serializedResponse = response.andReturn().getResponse().getContentAsString();
                List<ProtectedUserResponseDto> parsedResponse = this.objectMapper.readValue(serializedResponse,
                                new TypeReference<List<ProtectedUserResponseDto>>() {
                                });
                assertThat(parsedResponse).isEqualTo(protectedUserDtos);
        }

        @Test
        @DisplayName("Get all users when none user exists")
        void list_ShouldReturnNull_WhenNoneUserExists() throws Exception {

                /* Arrange */
                List<User> users = new ArrayList<>();
                when(this.userService.list()).thenReturn(users);

                /* Act & assert */
                ResultActions response = this.mockMvc.perform(get(Endpoint.GET_ALL_USERS))
                                .andExpect(MockMvcResultMatchers.status().isOk());

                assertThat(response.andReturn().getResponse().getContentAsString()).isEqualTo("[]");
        }
}