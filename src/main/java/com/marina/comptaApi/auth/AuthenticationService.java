package com.marina.comptaApi.auth;
import com.marina.comptaApi.Email.EmailService;
import com.marina.comptaApi.Email.EmailTemplateName;
import com.marina.comptaApi.Models.Role;
import com.marina.comptaApi.config.JwtService;
import com.marina.comptaApi.token.Token;
import com.marina.comptaApi.token.TokenRepository;
import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Repositories.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final TokenRepository tokenRepository;

    public void register(RegistrationRequest request) throws MessagingException {

        Role role = request.getRole().equalsIgnoreCase("administrateur")? Role.ADMIN
                : request.getRole().equalsIgnoreCase("comptable")?Role.MANAGER
                : Role.USER;
        if(userRepository.findByEmail(request.getEmail()).isPresent()){
            throw new IllegalStateException("Email déjà utilisé");
        }
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .accountLocked(false)
                .enabled(false)
                .build();

      userRepository.save(user);

      sendEmaliValidation(user);


    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        // vérifier si le mot de passe est correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Mot de Passe incorrect");
        }
        // vérifier si les 2 mots de passe sont les même
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Les mots de Passe ne sont pas les même");
        }
        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        // save the new password
        userRepository.save(user);
    }

    public User getProfile() {
        // récupérer l'utilisateur connecté
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            } else {
                throw new IllegalStateException("Type de principal non supporté : " + principal.getClass().getName());
            }
        }
        throw new IllegalStateException("Utilisateur non authentifié");
    }

    public void updateProfile(UpdateProfilRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setTelephone(request.getTelephone());
        userRepository.save(user);
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                // todo exception has to be defined
                .orElseThrow(() -> new RuntimeException("token invalide"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendEmaliValidation(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been send to the same email address");
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private String generateAndSaveActivationToken(User user) {
        // Generate a token
        String generatedToken = generateActivationCode();
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    public void sendEmaliValidation(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                "http://localhost:4200/auth/activate_account?token="+newToken,
                newToken,
                "Activation de compte"
        );
    }

    private String generateActivationCode() {
        return String.valueOf((int) (Math.random() * Math.pow(10, 6)));
    }

    public List<User>findUsers(){
        return userRepository.findAll();
    }


    public User LockAccount(Long id){
        User user = userRepository.findById(id).get();
        user.setAccountLocked(true);
        user.setEnabled(false);
        return user;
    }

    public User unLockAccount(Long id){
        User user = userRepository.findById(id).get();
        user.setAccountLocked(false);
        user.setEnabled(true);
        return user;
    }


    public User getOneUser(Long id){
        return userRepository.getById(id);
    }

}
