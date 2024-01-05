package br.dev.mhc.consultaibge.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mesorregiao implements Serializable {

    private Long id;
    private String nome;
    @JsonProperty("UF")
    private Estado uf;

}
