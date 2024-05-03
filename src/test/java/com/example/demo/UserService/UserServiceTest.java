package com.example.demo.UserService;

import com.example.demo.Model.Users;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_UsernameIsUnique(){
        //Arrange
        Users user = new Users();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findByUsername("testuser")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);

        //act
        Users registeredUser = userService.registerUser(user);

        //Assert
        assertNotNull(registeredUser);
        assertEquals("testuser",registeredUser.getUsername());
        assertEquals("encodedPassword",registeredUser.getPassword());
        verify(userRepository,times(1)).findByUsername("testusername");
        verify(passwordEncoder,times(1)).encode("password");
        verify(userRepository,times(1)).save(user);
    }

    @Test
    public void testGetUserById_WhenUserExists(){
        //Arrange
        Users user = new Users();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //Act
        Users fetchedUser = userService.getUserById(1L);

        //Assert
        assertNotNull(fetchedUser);
        assertEquals(1L,fetchedUser.getId());
        assertEquals("testuser",fetchedUser.getUsername());
        assertEquals("password",fetchedUser.getPassword());
        verify(userRepository,times(1)).findById(1L);
    }

    @Test
    public void testGetUserById_WhenUserDoesNotExists(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(RuntimeException.class,()-> userService.getUserById(1L));
        verify(userRepository,times(1)).findById(1L);
    }

}
