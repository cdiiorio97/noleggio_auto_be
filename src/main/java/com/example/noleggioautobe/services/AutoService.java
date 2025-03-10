package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoAuto;
import com.example.noleggioautobe.entities.Auto;
import com.example.noleggioautobe.repositories.AutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class AutoService {

    private final AutoRepository autoRepository;
    private final MapperService mapper;

    public AutoService(AutoRepository autoRepository, MapperService mapper) {
        this.autoRepository = autoRepository;
        this.mapper = mapper;
    }

    public List<DtoAuto> trovaAuto(){
        List<Auto> autoList = autoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<DtoAuto> dtoAutoList = new ArrayList<>();
        autoList.forEach(a -> dtoAutoList.add(new DtoAuto(a)));
        return dtoAutoList;
    }

    public DtoAuto trovaAutoById(Integer id) throws NullPointerException{
        Auto auto = autoRepository.findById(id).orElseThrow(() -> new NullPointerException("Auto non trovata"));
        return new DtoAuto(auto);
    }

    public void eliminaAuto(Integer id) throws NullPointerException{
        try{
            Auto auto = autoRepository.findById(id).orElseThrow(() -> new NullPointerException("Auto non trovata"));
            autoRepository.deleteById(auto.getId());
        } catch (NullPointerException e){
            throw new NullPointerException(e.getMessage());
        }
    }

    public Integer aggiungiAuto(DtoAuto dto) throws Exception {
        try {
            if(dto.getId() != null) {
                Auto autoDB = autoRepository.findById(dto.getId()).orElse(null);
                if(autoDB != null)
                    throw new Exception("Trovata auto con questo id");
            }
            Auto auto = mapper.convertDtoToAuto(new Auto(), dto);
            autoRepository.save(auto);
            return auto.getId();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void modificaAuto(DtoAuto dto) throws Exception {
        try {
            Auto auto = autoRepository.findById(dto.getId()).orElseThrow(() -> new NullPointerException("Auto non trovata"));
            mapper.convertDtoToAuto(auto, dto);
            autoRepository.save(auto);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<DtoAuto> cercaAutoDisponibili(Date dataInizio, Date dataFine) throws Exception {
        List<DtoAuto> autoDisponibiliDto = new ArrayList<>();
        try{
            List<Auto> autoDisponibili = autoRepository.trovaAutoDisponibili(dataInizio, dataFine);
            if(!autoDisponibili.isEmpty())
                autoDisponibili.forEach(elem -> autoDisponibiliDto.add(new DtoAuto(elem)));
            return autoDisponibiliDto;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
