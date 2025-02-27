package com.example.noleggioautobe.services;

import com.example.noleggioautobe.dto.DtoAuto;
import com.example.noleggioautobe.entities.Auto;
import com.example.noleggioautobe.repositories.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    public List<DtoAuto> trovaAuto(){
        List<Auto> autoList = autoRepository.findAll();
        List<DtoAuto> dtoAutoList = new ArrayList<>();
        for (Auto auto : autoList) {
            dtoAutoList.add(new DtoAuto(auto));
        }
        return dtoAutoList;
    }

    public DtoAuto trovaAutoById(Integer id){
        Auto auto = autoRepository.findById(id).orElse(null);
        return new DtoAuto(auto);
    }

}
