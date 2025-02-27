package Services;

import DTO.DtoPrenotazione;
import Entities.Prenotazione;
import Repositories.PrenotazioneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public List<DtoPrenotazione> trovaPrenotazioni(){
        List<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
        List<DtoPrenotazione> dtoPrenotazioni = new ArrayList<>();
        if(prenotazioni.isEmpty())
            log.warn("Nessuna prenotazione trovata");
        for(Prenotazione p : prenotazioni){
            dtoPrenotazioni.add(new DtoPrenotazione(p));
        }
        return dtoPrenotazioni;
    }

    public DtoPrenotazione getPrenotazioneById(Integer id) {
        Prenotazione p = prenotazioneRepository.findById(id).orElse(null);
        if(p == null)
            log.error("Prenotazione non trovata");
        return p != null ? new DtoPrenotazione(p) : null;
    }
}
