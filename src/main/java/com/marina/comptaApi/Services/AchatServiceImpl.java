package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.*;
import com.marina.comptaApi.Repositories.AchatRepository;
import com.marina.comptaApi.auth.AuthenticationService;
import com.marina.comptaApi.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AchatServiceImpl implements AchatService {

    private final AchatRepository achatRepository;
    private final ImageService imageService;
    private final AuthenticationService userService;
    private final SoldeService soldeService;

    @Override
    public Achat save(Achat achat, MultipartFile file) throws IOException {
        User user1=new User();
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            Object principal = auth.getPrincipal();
            if (principal instanceof User) {
                user1= (User) principal;
            } else {
                throw new IllegalStateException("Type de principal non supporté : " + principal.getClass().getName());
            }
        }
        Achat achat1 = Achat.builder()
                .dateEmission(achat.getDateEmission())
                .dateEcheance(achat.getDateEcheance())
                .tva(achat.getTva())
                .numero(achat.getNumero())
                .totaltva(achat.getTotaltva())
                .totalttc(achat.getTotalttc())
                .user(user1)
                .image(imageService.uploadImage(file))
                .build();
        achatRepository.save(achat1);

        Solde solde = soldeService.soldeActuel(user1.getId());
        if (solde != null) {
            solde.setSolde(solde.getSolde()-achat1.getTotalttc());
        }
        return  achat1;
    }

    @Override
    public Optional<List<Achat>> findByUser() {
        return achatRepository.findByUserId(userService.getProfile().getId());
    }

    @Override
    public Achat update(Achat achat) {
        return achatRepository.save(achat);
    }

    @Override
    public void delete(Long id) {
        achatRepository.deleteById(id);
    }

    @Override
    public Achat findById(Long id) {
        Achat achat= achatRepository.findById(id).orElse(null);
        assert achat != null;
        ImageData image = achat.getImage();
        byte[] images= ImageUtils.decompressImage(image.getImageData());
        image.setImageData(images);

        achat.setImage(image);
        return achat;
    }

    @Override
    public List<Achat> findAll() {
        return achatRepository.findAll();
    }
}
