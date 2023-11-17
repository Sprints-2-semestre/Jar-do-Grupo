package dao;

import conexao.Conexao;
import modelo.ValidacaoParametro;
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

public class ValidacaoEmail extends ValidacaoParametro {

    Conexao conexao = new Conexao();
    JdbcTemplate con = conexao.getConexaoDoBanco();

    public ValidacaoEmail() {
    }

    @Override
    public Boolean verificarParametro(String parametro) throws IOException {
        List<String> emailsExistentes = null;
        try {
            emailsExistentes = con.queryForList("SELECT email FROM usuario", String.class);
        }catch (Exception erroVerificarEmail) {
            erroVerificarEmail.getMessage();
            Path path = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs");
            Path path1 = Paths.get("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now());
            File log = new File("C:/Users/vitor/OneDrive/Documentos/SPTECH/projeto - LOGS/Jar-do-Grupo/logs/" + LocalDate.now() + "/" + LocalDate.now() + ".txt");

            if (!Files.exists(path)) {
                Files.createDirectory(path);
                Files.createDirectory(path1);
                log.createNewFile();
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroVerificarEmail.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }else {
                FileWriter fw = new FileWriter(log, true);
                BufferedWriter bw = new BufferedWriter(fw);

                bw.write(LocalDateTime.now() + erroVerificarEmail.getMessage());
                bw.newLine();

                bw.close();
                fw.close();
            }
        }
        for(int i = 0; i < emailsExistentes.size(); i++){
            if(emailsExistentes.get(i).equals(parametro)){
                return true;
            }
        }
        return false;
    }
}
