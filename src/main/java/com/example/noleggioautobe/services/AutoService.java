package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoAuto;
import com.example.noleggioautobe.entities.Auto;
import com.example.noleggioautobe.repositories.AutoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    public List<DtoAuto> trovaAuto(){
        List<Auto> autoList = autoRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<DtoAuto> dtoAutoList = new ArrayList<>();
        for (Auto auto : autoList) {
            if(auto.getId() != 1)
                dtoAutoList.add(new DtoAuto(auto));
        }
        return dtoAutoList;
    }

    public DtoAuto trovaAutoById(Integer id) throws Exception{
        Auto auto = autoRepository.findById(id).orElse(null);
        if(auto == null) {
            log.error("Auto non trovata");
            throw new Exception("Auto non trovata");
        }
        else
            return new DtoAuto(auto);
    }

    public void eliminaAuto(Integer id) throws Exception{
        Auto auto = autoRepository.findById(id).orElse(null);
        if(auto == null)
            throw new Exception("Auto non trovata");
        try{
            autoRepository.deleteById(id);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void aggiungiAuto(DtoAuto dto) throws Exception {
        Auto Auto = convertiDtoAuto(dto);
        try{
            autoRepository.save(Auto);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public void modificaAuto(DtoAuto dto) throws Exception {
        Auto Auto = convertiDtoAuto(dto);
        try{
            autoRepository.save(Auto);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    static Auto convertiDtoAuto(DtoAuto dto) {
        Auto auto = new Auto();
        if(dto.getId() != null  && dto.getId() != 0)
            auto.setId(dto.getId());
        auto.setBrand(dto.getBrand());
        auto.setModello(dto.getModello());
        auto.setTarga(dto.getTarga());
        return auto;
    }
}
