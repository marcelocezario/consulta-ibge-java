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
public class Estado implements Serializable {

    private Long id;
    private String sigla;
    private String nome;
    private Regiao regiao;

}
