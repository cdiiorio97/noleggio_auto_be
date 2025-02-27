package Entities;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "auto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String brand;
    private String modello;
    private String targa;
}
