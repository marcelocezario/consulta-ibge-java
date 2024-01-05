package br.dev.mhc.consultaibge.services;

import br.dev.mhc.consultaibge.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Service
public class IBGEService {

    private final WebClient webClient;

    @Autowired
    public IBGEService() {
        final String PATH_IBGE = "https://servicodados.ibge.gov.br/api/v1/localidades";
        final int MAX_BYTES = 5242880; // 5242880 b = 5 mb
        this.webClient = WebClient.builder()
                .codecs(clientCodecConfigurer ->
                        clientCodecConfigurer.defaultCodecs().maxInMemorySize(MAX_BYTES))
                .baseUrl(PATH_IBGE)
                .build();
    }

    public List<Regiao> buscarRegioes() {
        Instant inicio = Instant.now();
        System.out.println("Buscando dados de regiões - INICIO");
        Regiao[] regioes = webClient.get()
                .uri("/regioes")
                .retrieve()
                .bodyToMono(Regiao[].class)
                .block();
        assert regioes != null;
        System.out.printf("Buscando dados de regiões - FIM [tempo decorrido: %d ms]%n", Instant.now().toEpochMilli() - inicio.toEpochMilli());
        return Arrays.asList(regioes);
    }

    public List<Estado> buscarEstados() {
        Instant inicio = Instant.now();
        System.out.println("Buscando dados de estados - INICIO");
        Estado[] Estados = webClient.get()
                .uri("/estados")
                .retrieve()
                .bodyToMono(Estado[].class)
                .block();
        assert Estados != null;
        System.out.printf("Buscando dados de estados - FIM [tempo decorrido: %d ms]%n", Instant.now().toEpochMilli() - inicio.toEpochMilli());
        return Arrays.asList(Estados);
    }

    public List<Mesorregiao> buscarMesorregioes() {
        Instant inicio = Instant.now();
        System.out.println("Buscando dados de mesorregiões - INICIO");
        Mesorregiao[] mesorregioes = webClient.get()
                .uri("/mesorregioes")
                .retrieve()
                .bodyToMono(Mesorregiao[].class)
                .block();
        assert mesorregioes != null;
        System.out.printf("Buscando dados de mesorregiões - FIM [tempo decorrido: %d ms]%n", Instant.now().toEpochMilli() - inicio.toEpochMilli());
        return Arrays.asList(mesorregioes);
    }

    public List<Microrregiao> buscarMicrorregioes() {
        Instant inicio = Instant.now();
        System.out.println("Buscando dados de microrregiões - INICIO");
        Microrregiao[] microrregioes = webClient.get()
                .uri("/microrregioes")
                .retrieve()
                .bodyToMono(Microrregiao[].class)
                .block();
        assert microrregioes != null;
        System.out.printf("Buscando dados de microrregiões - FIM [tempo decorrido: %d ms]%n", Instant.now().toEpochMilli() - inicio.toEpochMilli());
        return Arrays.asList(microrregioes);
    }

    public List<Municipio> buscarMunicipios() {
        Instant inicio = Instant.now();
        System.out.println("Buscando dados de municípios - INICIO");
        Municipio[] municipios = webClient.get()
                .uri("/municipios")
                .retrieve()
                .bodyToMono(Municipio[].class)
                .block();
        assert municipios != null;
        System.out.printf("Buscando dados de municípios - FIM [tempo decorrido: %d ms]%n", Instant.now().toEpochMilli() - inicio.toEpochMilli());
        return Arrays.asList(municipios);
    }


}
