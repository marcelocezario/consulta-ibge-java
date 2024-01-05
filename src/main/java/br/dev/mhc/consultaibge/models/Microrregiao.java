package br.dev.mhc.consultaibge.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Microrregiao implements Serializable {

    private Long id;
    private String nome;
    private Mesorregiao mesorregiao;

}
