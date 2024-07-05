package com.marina.comptaApi.Services;

import com.marina.comptaApi.Models.Achat;
import com.marina.comptaApi.Models.ImageData;
import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Models.Vente;
import com.marina.comptaApi.Repositories.VenteRepository;
import com.marina.comptaApi.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VenteServiceImpl implements VenteService {

    private final VenteRepository venteRepository;
    private final ImageService imageService;

    @Override
    public Vente save(Vente vente, MultipartFile file) throws Exception {
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
        Vente vente1 = Vente.builder()
                .dateEmission(vente.getDateEmission())
                .tva(vente.getTva())
                .numero(vente.getNumero())
                .totaltva(vente.getTotaltva())
                .totalttc(vente.getTotalttc())
                .user(user1)
                .image(imageService.uploadImage(file))
                .build();
        venteRepository.save(vente1);

        return  vente1;
    }

    @Override
    public Vente update(Vente vente) {
        return venteRepository.save(vente);
    }

    @Override
    public void delete(Long id) {
        venteRepository.deleteById(id);
    }

    @Override
    public List<Vente> getAll() {
        return venteRepository.findAll();
    }

    @Override
    public Vente get(Long id) {
        Vente vente= venteRepository.findById(id).orElse(null);
        assert vente != null;
        ImageData image = vente.getImage();
        byte[] images= ImageUtils.decompressImage(image.getImageData());
        image.setImageData(images);

        vente.setImage(image);
        return vente;
    }
}
