package com.marina.comptaApi.Services;


import com.marina.comptaApi.Models.Revenu;
import com.marina.comptaApi.Models.User;
import com.marina.comptaApi.Repositories.RevenuRepository;


import com.marina.comptaApi.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenuServiceImpl implements RevenuService {

    private final RevenuRepository revenuRepository;
    private final SoldeService soldeService;
    private final AuthenticationService authService;


    @Override
    public Revenu save(Revenu revenu) {
        User user=authService.getProfile();
        var revenu1=Revenu.builder()
                .date(LocalDateTime.now())
                .montant(revenu.getMontant())
                .user(user)
                .description(revenu.getDescription())
                .build();
        revenuRepository.save(revenu1);
        soldeService.updateSolde(user, revenu.getMontant(), "REVENU");

        return revenu1;
    }

    @Override
    public Revenu update(Revenu revenu) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Revenu> getAll() {
        User user=authService.getProfile();
        return revenuRepository.findByUser(user);
    }

    @Override
    public Revenu get(Long id) {
        return null;
    }
}
