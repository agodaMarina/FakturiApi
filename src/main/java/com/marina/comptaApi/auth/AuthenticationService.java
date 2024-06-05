package com.marina.comptaApi.auth;


import com.marina.comptaApi.Email.EmailService;
import com.marina.comptaApi.Email.EmailTemplateName;
import com.marina.comptaApi.Models.Role;
import com.marina.comptaApi.token.Token;
import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Repositories.UserRepository;
import com.marina.comptaApi.config.JwtService;
import com.marina.comptaApi.token.TokenRepository;
import com.marina.comptaApi.token.TokenType;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;

    public void register(RegistrationRequest request) throws MessagingException {
//        var userRole= roleRepository.findByName("USER").orElseThrow(() -> new IllegalStateException("Error:Le role USER n'existe pas."));
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
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .isaccountLocked(false)
                .enabled(false)
                .build();

      userRepository.save(user);

      sendEmaliValidation(user);

    }

    public AuthenticationResponse authenticate(AuthenticateRequest request) {
        var auth= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var claims=new HashMap<String, Object>();
        var user= (User) auth.getPrincipal();
        claims.put("Fullname", user.fullName());
        //var jwtToken= jwtService.generateToken(claims, user);
        // Génère un token de rafraîchissement pour l'utilisateur
        //var refreshToken = jwtService.generateRefreshToken(user);
        // Révoque tous les tokens valides existants de l'utilisateur
        //revokeAllUserTokens(user);
        // Sauvegarde le nouveau token JWT dans la base de données
        //saveUserToken(user, jwtToken);
        // Retourne les tokens d'accès et de rafraîchissement
        var jwtToken = jwtService.generateToken(claims, user);
        System.out.println(jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // Méthode pour sauvegarder un token JWT associé à un utilisateur
    private void saveUserToken(User user, String jwtToken) {
        // Crée un nouvel objet Token associé à l'utilisateur
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        // Sauvegarde le token dans la base de données
        tokenRepository.save(token);
    }

    // Méthode pour révoquer tous les tokens valides d'un utilisateur
    private void revokeAllUserTokens(User user) {
        // Récupère tous les tokens valides associés à l'utilisateur
        var validUserTokens = tokenRepository.findAllValidTokenByUser(Math.toIntExact(user.getId()));
        if (validUserTokens.isEmpty())
            return;
        // Révoque chaque token en les marquant comme expirés et révoqués
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        // Sauvegarde les tokens révoqués dans la base de données
        tokenRepository.saveAll(validUserTokens);
    }

    // Méthode pour rafraîchir un token JWT
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException{
//        // Extrait le token de rafraîchissement du header d'autorisation
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        // Extrait l'email de l'utilisateur à partir du token de rafraîchissement
//        userEmail = jwtService.extractEmail(refreshToken);
//        if (userEmail != null) {
//            // Récupère l'utilisateur depuis la base de données en utilisant l'email
//            var user = this.userRepository.findByEmail(userEmail)
//                    .orElseThrow();
//            // Vérifie si le token de rafraîchissement est valide
//            if (jwtService.isvalidateToken(refreshToken, user)) {
//                // Génère un nouveau token d'accès pour l'utilisateur
//                var accessToken = jwtService.generateToken(user);
//                // Révoque tous les anciens tokens de l'utilisateur
//                revokeAllUserTokens(user);
//                // Sauvegarde le nouveau token dans la base de données
//                saveUserToken(user, accessToken);
//                // Crée une réponse contenant les nouveaux tokens d'accès et de rafraîchissement
//                AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                // Écrit la réponse contenant les nouveaux tokens dans le corps de la réponse HTTP
////                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }

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
        //récupérer l'utilisateur connecté
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return (User) auth.getPrincipal();
        }
        throw new IllegalStateException("Utilisateur non authentifié");
    }

    public void updateProfile(UpdateProfilRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }


    public void sendEmaliValidation(User user) throws MessagingException {
        var newToken= genarateAndSaveToken(user);

        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                "http://localhost:8080/api/v1/auth/activate-account?token="+newToken,
                newToken,
                "Activation de compte"
        );

    }

    public String genarateAndSaveToken(User user) {
       String generatedtoken = generateActivationCode();
       tokenRepository.save(Token.builder()
               .user(user)
               .token(generatedtoken)
               .tokenType(TokenType.ACTIVATION)
               .expired(false)
               .revoked(false)
               .build());
       return generatedtoken;
    }

    private String generateActivationCode() {

        return String.valueOf((int) (Math.random() * Math.pow(10, 6)));

    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
       Token savedToken= tokenRepository.findByToken(token)
               .orElseThrow(() -> new IllegalStateException("Token invalide"));
        if(savedToken.isExpired()){
            sendEmaliValidation(savedToken.getUser());
            throw new IllegalStateException("Token expiré. un nouveau code a été envoyé à votre adresse email");
        }

        var user= userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        user.setEnabled(true);
        userRepository.save(user);
        tokenRepository.save(savedToken);
    }


}
