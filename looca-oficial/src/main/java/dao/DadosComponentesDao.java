package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;
import conexao.Conexao;
import modelo.DadosComponentes;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DadosComponentesDao {
    Looca looca = new Looca();
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    // fkMaquina
    String fkMaquina = looca.getProcessador().getId();

    public void salvar() throws IOException {
        // Captura do uso da CPU;
        Double usoCpu = looca.getProcessador().getUso();

        // Captura do uso de RAM;
        Long usoRam = looca.getMemoria().getEmUso();
        Double divisaoUsoRam = (usoRam / 1000000000.0);
        String usoRamFormatado = String.format("%.1f", divisaoUsoRam);
        StringBuilder s = new StringBuilder(usoRamFormatado);
        s.setCharAt(1, '.');

        Long ramDisponivel = looca.getMemoria().getDisponivel();
        Double divisaoRamDisponivel = (ramDisponivel / 1000000000.0);
        String ramDisponivelFormatado = String.format("%.1f", divisaoRamDisponivel);
        StringBuilder sb = new StringBuilder(ramDisponivelFormatado);
        sb.setCharAt(1, '.');


        // Captura do uso de DISCO;
        Long tamanhoTotalDisco = (looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000 / 1000 / 1000);
        Long tamanhoDisponivel = (looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000 / 1000 / 1000);

        if (usoCpu < 100.0) {
            try{
            con.update("INSERT INTO dadosComponente (qtdUsoCpu, fkMaquina) VALUES (?, ?);", usoCpu, fkMaquina);
        }catch (Exception erroInsertCPU) {
                erroInsertCPU.getMessage();
                Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
                Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
                File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

                if (!Files.exists(path)) {
                    Files.createDirectory(path);
                    Files.createDirectory(path1);
                    log.createNewFile();
                    FileWriter fw = new FileWriter(log, true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(LocalDateTime.now() + erroInsertCPU.getMessage());
                    bw.newLine();

                    bw.close();
                    fw.close();
                }else {
                    FileWriter fw = new FileWriter(log, true);
                    BufferedWriter bw = new BufferedWriter(fw);

                    bw.write(LocalDateTime.now() + erroInsertCPU.getMessage());
                    bw.newLine();

                    bw.close();
                    fw.close();
                }
            }
        }
        try {
            con.update("INSERT INTO dadosComponente (memoriaEmUso, memoriaDisponivel, fkMaquina) VALUES (?, ?, ?);", s, sb, fkMaquina);
        }catch (Exception erroInsertMemoria) {
            erroInsertMemoria.getMessage();
            Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
            Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
            File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Files.createDirectory(path1);
                log.createNewFile();
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMemoria.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
            else {
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMemoria.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
        }
        try{
        con.update("INSERT INTO dadosComponente (usoAtualDisco, usoDisponivelDisco, fkMaquina) VALUES (?, ?, ?);", (tamanhoTotalDisco - tamanhoDisponivel), tamanhoDisponivel, fkMaquina);
    }catch (Exception erroInsertDisco) {
            erroInsertDisco.getMessage();
            Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
            Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
            File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Files.createDirectory(path1);
                log.createNewFile();
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertDisco.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }else {
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertDisco.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
        }
    }

    public void listar() {
        System.out.println(con.query("""
                        select dadosComponente.qtdUsoCpu,
                        dadosComponente.memoriaEmUso,
                        dadosComponente.memoriaDisponivel,
                        dadosComponente.usoAtualDisco,
                        dadosComponente.usoDisponivelDisco,
                        dadosComponente.pacoteRecebido,
                        dadosComponente.pacoteEnviado
                        from dadosComponente
                        where fkMaquina = 1;""",
                new BeanPropertyRowMapper<>(DadosComponentes.class)));
    }
}