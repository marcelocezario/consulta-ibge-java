package br.dev.mhc.consultaibge.services;

import br.dev.mhc.consultaibge.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

@Service
public class GenerateSqlService {

    private final IBGEService ibgeService;

    private final String DB_SCHEMA = "location";

    @Autowired
    public GenerateSqlService(IBGEService ibgeService) {
        this.ibgeService = ibgeService;
    }

    public void generateSqlInsert() {
        Instant inicio = Instant.now();
        System.out.println("Buscando informações no site do IBGE - INICIO");

        List<Regiao> regioes = ibgeService.buscarRegioes();
        List<Estado> estados = ibgeService.buscarEstados();
        List<Mesorregiao> mesorregioes = ibgeService.buscarMesorregioes();
        List<Microrregiao> microrregioes = ibgeService.buscarMicrorregioes();
        List<Municipio> municipios = ibgeService.buscarMunicipios();

        System.out.printf("Buscando informações no site do IBGE - FIM [tempo decorrido: %d ms]%n", Instant.now().toEpochMilli() - inicio.toEpochMilli());

        String pasta = "sql-files";
        String diretorioPath = System.getProperty("user.dir") + File.separator + pasta;
        File diretorio = new File(diretorioPath);
        if (!diretorio.exists()) {
            if (!diretorio.mkdirs()) {
                throw new RuntimeException("Não foi possível criar pasta [" + pasta + "]");
            }
        }

        String insertFile = "/insert_locations_ibge.sql";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pasta.concat(insertFile)))) {
            addInsertRegioes(regioes, writer);
            addInsertEstados(estados, writer);
            addInsertMesorregioes(mesorregioes, writer);
            addInsertMicrorregioes(microrregioes, writer);
            addInsertMunicipios(municipios, writer);
            System.out.println(String.format("Arquivo sql para insert gerado em %s%s", pasta, insertFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String updateFile = "/update_locations_ibge.sql";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pasta.concat(updateFile)))) {
            addUpdateRegioes(regioes, writer);
            addUpdateEstados(estados, writer);
            addUpdateMesorregioes(mesorregioes, writer);
            addUpdateMicrorregioes(microrregioes, writer);
            addUpdateMunicipios(municipios, writer);
            System.out.println(String.format("Arquivo sql para insert gerado em %s%s", pasta, updateFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.printf("Término da execução do sistema [tempo decorrido: %d ms]%n", Instant.now().toEpochMilli() - inicio.toEpochMilli());
    }

    private void addInsertMunicipios(List<Municipio> municipios, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Municipio municipio : municipios) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("cities");
            String colunas = "id, name, microregion_id, active, created_at, updated_at";
            agora = Instant.now();
            String valores = String.format("%d, '%s', %d, true, '%s', '%s'",
                    municipio.getId(), municipio.getNome().replace("'", "''"), municipio.getMicrorregiao().getId(), agora, agora);
            writer.write(String.format("insert into %s (%s) values (%s);", tabela, colunas, valores));
            writer.newLine();
        }
    }

    private void addInsertMicrorregioes(List<Microrregiao> microrregioes, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Microrregiao microrregiao : microrregioes) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("microregions");
            String colunas = "id, name, mesoregion_id, active, created_at, updated_at";
            agora = Instant.now();
            String valores = String.format("%d, '%s', %d, true, '%s', '%s'",
                    microrregiao.getId(), microrregiao.getNome().replace("'", "''"), microrregiao.getMesorregiao().getId(), agora, agora);
            writer.write(String.format("insert into %s (%s) values (%s);", tabela, colunas, valores));
            writer.newLine();
        }
    }

    private void addInsertMesorregioes(List<Mesorregiao> mesorregioes, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Mesorregiao mesorregiao : mesorregioes) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("mesoregions");
            String colunas = "id, name, state_id, active, created_at, updated_at";
            agora = Instant.now();
            String valores = String.format("%d, '%s', %d, true, '%s', '%s'",
                    mesorregiao.getId(), mesorregiao.getNome().replace("'", "''"), mesorregiao.getUf().getId(), agora, agora);
            writer.write(String.format("insert into %s (%s) values (%s);", tabela, colunas, valores));
            writer.newLine();
        }
    }

    private void addInsertEstados(List<Estado> estados, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Estado estado : estados) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("states");
            String colunas = "id, name, acronym, region_id, active, created_at, updated_at";
            agora = Instant.now();
            String valores = String.format("%d, '%s', '%s', %d, true, '%s', '%s'",
                    estado.getId(), estado.getNome().replace("'", "''"), estado.getSigla(), estado.getRegiao().getId(), agora, agora);
            writer.write(String.format("insert into %s (%s) values (%s);", tabela, colunas, valores));
            writer.newLine();
        }
    }

    private void addInsertRegioes(List<Regiao> regioes, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Regiao regiao : regioes) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("regions");
            String colunas = "id, name, acronym, active, created_at, updated_at";
            agora = Instant.now();
            String valores = String.format("%d, '%s', '%s', true, '%s', '%s'",
                    regiao.getId(), regiao.getNome().replace("'", "''"), regiao.getSigla(), agora, agora);
            writer.write(String.format("insert into %s (%s) values (%s);", tabela, colunas, valores));
            writer.newLine();
        }
    }

    private void addUpdateMunicipios(List<Municipio> municipios, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Municipio municipio : municipios) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("cities");
            String colunas = "id, name, active, created_at, updated_at";
            agora = Instant.now();
            String name = municipio.getNome().replace("'", "''");
            Long microregionId = municipio.getMicrorregiao().getId();
            String valores = String.format("name = '%s', microregion_id = %d, updated_at = '%s'", name, microregionId, agora);
            String where = String.format("id = %d and (name <> '%s' or microregion_id <> %d)", municipio.getId(), name, microregionId);
            writer.write(String.format("update %s set %s where %s;", tabela, valores, where));
            writer.newLine();
        }
    }

    private void addUpdateMicrorregioes(List<Microrregiao> microrregioes, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Microrregiao microrregiao : microrregioes) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("microregions");
            String colunas = "id, name, active, created_at, updated_at";
            agora = Instant.now();
            String name = microrregiao.getNome().replace("'", "''");
            Long mesoregionId = microrregiao.getMesorregiao().getId();
            String valores = String.format("name = '%s', mesoregion_id = %d, updated_at = '%s'", name, mesoregionId, agora);
            String where = String.format("id = %d and (name <> '%s' or mesoregion_id <> %d)", microrregiao.getId(), name, mesoregionId);
            writer.write(String.format("update %s set %s where %s;", tabela, valores, where));
            writer.newLine();
        }
    }

    private void addUpdateMesorregioes(List<Mesorregiao> mesorregioes, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Mesorregiao mesorregiao : mesorregioes) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("mesoregions");
            String colunas = "id, name, active, updated_at";
            agora = Instant.now();
            String name = mesorregiao.getNome().replace("'", "''");
            Long stateId = mesorregiao.getUf().getId();
            String valores = String.format("name = '%s', state_id = %d, updated_at = '%s'", name, stateId, agora);
            String where = String.format("id = %d and (name <> '%s' or state_id <> %d)", mesorregiao.getId(), name, stateId);
            writer.write(String.format("update %s set %s where %s;", tabela, valores, where));
            writer.newLine();
        }
    }

    private void addUpdateEstados(List<Estado> estados, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Estado estado : estados) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("states");
            String colunas = "id, name, acronym, region_id, updated_at";
            agora = Instant.now();
            String name = estado.getNome().replace("'", "''");
            String acronym = estado.getSigla().replace("'", "''");
            String valores = String.format("name = '%s', acronym = '%s', updated_at = '%s'", name, acronym, agora);
            String where = String.format("id = %d and (name <> '%s' or acronym <> '%s')", estado.getId(), name, acronym);
            writer.write(String.format("update %s set %s where %s;", tabela, valores, where));
            writer.newLine();
        }
    }

    private void addUpdateRegioes(List<Regiao> regioes, BufferedWriter writer) throws IOException {
        Instant agora;
        for (Regiao regiao : regioes) {
            String tabela = (DB_SCHEMA.isBlank() ? "" : DB_SCHEMA.concat(".")).concat("regions");
            agora = Instant.now();
            String name = regiao.getNome().replace("'", "''");
            String acronym = regiao.getSigla().replace("'", "''");
            String valores = String.format("name = '%s', acronym = '%s', updated_at = '%s'", name, acronym, agora);
            String where = String.format("id = %d and (name <> '%s' or acronym <> '%s')", regiao.getId(), name, acronym);
            writer.write(String.format("update %s set %s where %s;", tabela, valores, where));
            writer.newLine();
        }
    }
}
