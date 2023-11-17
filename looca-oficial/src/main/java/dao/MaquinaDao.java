package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.processos.Processo;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.sistema.Sistema;
import conexao.Conexao;
import conexao.ConexaoSlack;
import modelo.Maquina;
import modelo.MaquinaTipoComponente;
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
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;
import static jdk.internal.org.jline.utils.Colors.s;

public class MaquinaDao {
    Looca looca = new Looca();
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    private String idMaquina = looca.getProcessador().getId();
    private String sistemaOperacional = looca.getSistema().getSistemaOperacional();
    private Integer arquitetura = looca.getSistema().getArquitetura();
    private String fabricante = looca.getProcessador().getFabricante();
    private Long tempoAtividade = looca.getSistema().getTempoDeAtividade();

    List<RedeInterface> redes = new ArrayList<>();

    ConexaoSlack conexaoSlack = new ConexaoSlack();
    ValidacaoIdMaquina validIdMaquina = new ValidacaoIdMaquina();
    MaquinaTipoComponenteDao maquinaTipoComponenteDao = new MaquinaTipoComponenteDao();

    public void salvar() throws IOException {
        Boolean existeIdMaquina = validIdMaquina.verificarParametro(idMaquina);

        if (existeIdMaquina) {
            maquinaTipoComponenteDao.salvar();

            Double usoCpu = looca.getProcessador().getUso();
//            if (usoCpu >= parametroPreocupante && usoCpu < parametroCritico) {
//                conexaoSlack.enviarMensagemCpu(usoCpu);
//            }

            // Captura do uso de RAM;
            Long usoRam = looca.getMemoria().getEmUso();
            Double divisaoUsoRam = (usoRam / 1000000000.0);
            String usoRamFormatado = String.format("%.1f", divisaoUsoRam);


//            if (s >= parametroRamPreocupante && s < parametroRamCritico) {
//                conexaoSlack.enviarMensagemRam(s);
//            }

            Long ramDisponivel = looca.getMemoria().getDisponivel();
            Double divisaoRamDisponivel = (ramDisponivel / 1000000000.0);
            String ramDisponivelFormatado = String.format("%.1f", divisaoRamDisponivel);
            StringBuilder sb = new StringBuilder(ramDisponivelFormatado);
            sb.setCharAt(1, '.');
            StringBuilder s = new StringBuilder(usoRamFormatado);
            s.setCharAt(1, '.');


            // Captura do uso de DISCO;
            Long tamanhoTotalDisco = (looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000 / 1000 / 1000);
            Long tamanhoDisponivel = (looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000 / 1000 / 1000);

            try {
                con.update("INSERT INTO dadosComponente (qtdUsoCpu, fkMaquina, fkTipoComponente) VALUES (?, ?, ?);", usoCpu, idMaquina, 1);
            } catch (Exception erroInsertCPU) {
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
        try {
            con.update("INSERT INTO dadosComponente (memoriaEmUso, memoriaDisponivel, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", s, sb, idMaquina, 2);
        } catch (Exception erroInsertMemoria) {
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
            }else {
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMemoria.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
        }
        try {
            con.update("INSERT INTO dadosComponente (usoAtualDisco, usoDisponivelDisco, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", (tamanhoTotalDisco - tamanhoDisponivel), tamanhoDisponivel, idMaquina, 3);
        } catch (Exception erroInsertDisco) {
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
                try {
                    con.update("INSERT INTO maquina (idMaquina, sistemaOperacional, arquitetura, fabricante, tempoAtividade) VALUES (?, ?, ?, ?, ?);",
                            idMaquina, sistemaOperacional, arquitetura,
                            fabricante, tempoAtividade);

                    maquinaTipoComponenteDao.salvar();
                } catch (Exception erroInsertSO) {
                    erroInsertSO.getMessage();
                    Path path2 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
                    Path path3 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
                    File log1 = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

                    if (!Files.exists(path)) {
                        Files.createDirectory(path2);
                        Files.createDirectory(path3);
                        log.createNewFile();
                        FileWriter fw = new FileWriter(log1, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + erroInsertSO.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }else {
                        FileWriter fw = new FileWriter(log, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + erroInsertSO.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }
                }

                // Captura do uso de DISCO;
                tamanhoTotalDisco = (looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000 / 1000 / 1000);
                tamanhoDisponivel = (looca.getGrupoDeDiscos().getVolumes().get(0).getDisponivel() / 1000 / 1000 / 1000);

                try {
                    con.update("INSERT INTO dadosComponente (qtdUsoCpu, fkMaquina, fkTipoComponente) VALUES (?, ?, ?);", usoCpu, idMaquina, 1);
                } catch (Exception e) {
                    e.getMessage();
                    Path path2 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
                    Path path3 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
                    File log1 = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

                    if (!Files.exists(path)) {
                        Files.createDirectory(path2);
                        Files.createDirectory(path3);
                        log.createNewFile();
                        FileWriter fw = new FileWriter(log1, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + e.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }else {
                        FileWriter fw = new FileWriter(log, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + e.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }
                }
                try {
                    con.update("INSERT INTO dadosComponente (memoriaEmUso, memoriaDisponivel, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", s, sb, idMaquina, 2);
                } catch (Exception e) {
                    e.getMessage();
                    Path path2 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
                    Path path3 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
                    File log1 = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

                    if (!Files.exists(path)) {
                        Files.createDirectory(path2);
                        Files.createDirectory(path3);
                        log.createNewFile();
                        FileWriter fw = new FileWriter(log1, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + e.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }
                    else {
                        FileWriter fw = new FileWriter(log, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + e.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }
                }
                try {
                    con.update("INSERT INTO dadosComponente (usoAtualDisco, usoDisponivelDisco, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", (tamanhoTotalDisco - tamanhoDisponivel), tamanhoDisponivel, idMaquina, 3);
                }catch (Exception e) {
                    e.getMessage();
                    Path path2 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
                    Path path3 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
                    File log1 = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

                    if (!Files.exists(path)) {
                        Files.createDirectory(path2);
                        Files.createDirectory(path3);
                        log.createNewFile();
                        FileWriter fw = new FileWriter(log1, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + e.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }
                    else {
                        FileWriter fw = new FileWriter(log, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + e.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }
                }
            }
        }
    }
}