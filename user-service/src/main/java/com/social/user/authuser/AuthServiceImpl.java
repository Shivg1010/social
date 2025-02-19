package com.social.user.authuser;

import com.social.user.exception.NotFoundException;
import com.social.user.exception.ServiceException;
import com.social.user.security.JWTGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final JpaUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    @Override
    public JpaUserDto saveUser(RegisterDto registerDto) {
        if (
                        Boolean.TRUE.equals(userRepository.existsByEmail(registerDto.getEmail())) ||
                        Boolean.TRUE.equals(userRepository.existsByUniqueName(registerDto.getUniqueName()))
        ) {
            log.error("Cannot save user as email or username is already present in database {}",registerDto.getEmail());
            throw new ServiceException("User is already present choose different email or username!", HttpStatus.FOUND,"User Already exists");
        }
        JpaUser user = mapper.map(registerDto, JpaUser.class);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        if (log.isDebugEnabled()) log.debug("user details -> {}", user);
        final var jpaUser = userRepository.save(user);
        return mapper.map(jpaUser,JpaUserDto.class);
    }

    @Override
    public JpaUserDto getUserByEmail(String email) {
        final var jpaUser = userRepository.findByEmail(email).orElseThrow(
                () -> new ServiceException("No User Found with email: %s".formatted(email),HttpStatus.NOT_FOUND,"No User Found")
        );
        if (log.isDebugEnabled()) log.debug("user details -> {}", jpaUser);
        return mapper.map(jpaUser,JpaUserDto.class);
    }

    @Override
    public String getAuthToken(LoginDto loginDto) {
        JpaUser savedUser = userRepository.findByEmail(loginDto.getEmail()).orElseThrow(() -> new NotFoundException("No user found with given email: {}" +loginDto.getEmail()));
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(savedUser.getUniqueName(), loginDto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var token = jwtGenerator.generateToken(authentication);
        if (log.isDebugEnabled()) log.info("token details -> {}", token);
        return token;
    }

}
