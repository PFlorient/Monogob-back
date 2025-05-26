package com.example.monoGoblin.manager;

import com.example.monoGoblin.dto.UserDto;
import com.example.monoGoblin.dto.auth.LoginResponse;
import com.example.monoGoblin.exception.UserNotFoundException;
import com.example.monoGoblin.model.UserModel;
import com.example.monoGoblin.model.UserPrincipal;
import com.example.monoGoblin.repository.UserRepository;
import com.example.monoGoblin.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserManager implements UserDetailsService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService = new JWTService();
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void register(UserDto userDto) {
        UserModel user = userDto.transform();

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public LoginResponse login(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Nom d'utilisateur ou mot de passe manquant");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        

        UserModel user = userRepository.findByEmailOrUsername(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        String token = jwtService.generateToken(user.getUsername(), user.getId());

        return new LoginResponse(token, user.getUsername(), user.getEmail());
    }

    public void delete(UUID uuidUser) {
        try {
            userRepository.deleteById(uuidUser);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByEmailOrUsername(username, username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user);
    }
}
