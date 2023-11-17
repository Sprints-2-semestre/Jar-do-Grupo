package dao;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import conexao.Conexao;
import modelo.MaquinaTipoComponente;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MaquinaTipoComponenteDao {
    Looca looca = new Looca();
    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();
    ValidacaoIdMaquina validacaoIdMaquina = new ValidacaoIdMaquina();

    // Captura da fkMaquina;
    private String fkMaquina = looca.getProcessador().getId();


    // Capturas da Máquina
    private Integer qtdCpuLogico = looca.getProcessador().getNumeroCpusLogicas();
    private Integer qtdCpuFisico = looca.getProcessador().getNumeroCpusFisicas();


    // Capturas de RAM;
    private Long totalRam = looca.getMemoria().getTotal() / 1000000000;


    // Capturas de Disco;
    private String numSerial = looca.getGrupoDeDiscos().getDiscos().get(0).getSerial();
    private Long totalDisco = looca.getGrupoDeDiscos().getVolumes().get(0).getTotal() / 1000000000;


    // Capturas de Rede;
    private String enderecoMac;
    private String ipv4;
    List<RedeInterface> redes = looca.getRede().getGrupoDeInterfaces().getInterfaces();


    public void salvar() throws IOException {
        try {
            con.update("INSERT INTO maquinaTipoComponente (numProcesLogicos, numProcesFisicos, fkMaquina, fkTipoComp) VALUES (?, ?, ?, ?);", qtdCpuLogico, qtdCpuFisico, fkMaquina, 1);
        }catch (Exception erroInsertMaquinaTipoComponente) {
            erroInsertMaquinaTipoComponente.getMessage();
            Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
            Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
            File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Files.createDirectory(path1);
                log.createNewFile();
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMaquinaTipoComponente.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }else {
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMaquinaTipoComponente.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
        }
        try {
            con.update("INSERT INTO maquinaTipoComponente (tamanhoTotalRam, fkMaquina, fkTipoComp) VALUES (?, ?, ?);", totalRam, fkMaquina, 2);
        }catch (Exception erroInsertMaquinaTipoComponente) {
            erroInsertMaquinaTipoComponente.getMessage();
            Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
            Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
            File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Files.createDirectory(path1);
                log.createNewFile();
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMaquinaTipoComponente.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }else {
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMaquinaTipoComponente.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
        }
        try {
            con.update("INSERT INTO maquinaTipoComponente (numSerial, tamanhoTotalDisco, fkMaquina, fkTipoComp) VALUES (?, ?, ?, ?);", numSerial, totalDisco, fkMaquina, 3);
        }catch (Exception erroInsertMaquinaTipoComponente) {
            erroInsertMaquinaTipoComponente.getMessage();
            Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
            Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
            File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Files.createDirectory(path1);
                log.createNewFile();
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroInsertMaquinaTipoComponente.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }else {
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() +
                        erroInsertMaquinaTipoComponente.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
        }
        for (int i = 0; i < redes.size(); i++) {
            if (redes.get(i).getBytesRecebidos() > 0 && redes.get(i).getBytesEnviados() > 0) {
                enderecoMac = redes.get(i).getEnderecoMac();
                ipv4 = redes.get(i).getEnderecoIpv4().toString();
                con.update("INSERT INTO maquinaTipoComponente (enderecoMac, ipv4, fkMaquina, fkTipoComp) VALUES (?, ?, ?, ?);", enderecoMac, ipv4, fkMaquina, 4);

                Long bytesRecebido = redes.get(i).getBytesRecebidos();
                double bytesRecebidosDouble = bytesRecebido.doubleValue() / 100000;
                String bytesRecebidoFormatado = String.format("%.2f", bytesRecebidosDouble);
                bytesRecebidoFormatado = bytesRecebidoFormatado.replace(",", ".");


                Long bytesEnviado = redes.get(i).getBytesEnviados();
                double bytesEnviadosDouble = bytesEnviado.doubleValue() / 100000;
                String bytesEnviadoFormatado = String.format("%.2f", bytesEnviadosDouble);
                bytesEnviadoFormatado = bytesEnviadoFormatado.replace(",",".");

                try {
                    con.update("INSERT INTO dadosComponente (bytesRecebido, bytesEnviado, fkMaquina, fkTipoComponente) VALUES (?, ?, ?, ?);", bytesRecebidoFormatado, bytesEnviadoFormatado, fkMaquina, 4);
                }catch (Exception erroInsertBytes) {
                    erroInsertBytes.getMessage();
                    Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
                    Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
                    File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

                    if (!Files.exists(path)) {
                        Files.createDirectory(path);
                            Files.createDirectory(path1);
                                log.createNewFile();
                                FileWriter fw = new FileWriter(log, true);
                                BufferedWriter bw = new BufferedWriter(fw);

                                bw.write(LocalDateTime.now() + erroInsertBytes.getMessage());
                                bw.newLine();

                                bw.close();
                                fw.close();
                            }else {
                        FileWriter fw = new FileWriter(log, true);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(LocalDateTime.now() + erroInsertBytes.getMessage());
                        bw.newLine();

                        bw.close();
                        fw.close();
                    }
                        }
                    }
                break;
                }
            }


    public void listar() {
        System.out.println(con.query("""
                        select maquinaTipoComponente.idProcessador,
                        maquinaTipoComponente.num_ProcesLogicos,
                        maquinaTipoComponente.num_ProcesFisicos,
                        maquinaTipoComponente.tamanhoTotalRam,
                        maquinaTipoComponente.numSerial,
                        maquinaTipoComponente.enderecoMac,
                        maquinaTipoComponente.ipv4
                        from maquinaTipoComponente
                        where fkMaquina = 1;""",
                new BeanPropertyRowMapper<>(MaquinaTipoComponente.class)));
    }
}
