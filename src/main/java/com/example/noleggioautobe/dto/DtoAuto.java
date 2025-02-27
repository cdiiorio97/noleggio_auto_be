package com.example.noleggioautobe.dto;

import com.example.noleggioautobe.entities.Auto;
import lombok.Data;

@Data
public class DtoAuto {
    private Integer id;
    private String brand;
    private String modello;
    private String targa;

    public DtoAuto() {}

    public DtoAuto(Auto auto) {
        this.id = auto.getId();
        this.brand = auto.getBrand();
        this.modello = auto.getModello();
        this.targa = auto.getTarga();
    }
}
