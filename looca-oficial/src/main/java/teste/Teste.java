package teste;

import conexao.Conexao;
import dao.DadosComponentesDao;
import dao.MaquinaDao;
import dao.MaquinaTipoComponenteDao;
import dao.ValidacaoEmail;
import modelo.Maquina;
import modelo.MaquinaTipoComponente;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Teste {
    public static void main(String[] args) throws IOException {
        ValidacaoEmail validacaoEmail = new ValidacaoEmail();

        Scanner leitor = new Scanner(System.in);

        System.out.println("""
                ------------------------------------------------
                |           Seja Bem Vindo a Fármacos          |
                ------------------------------------------------
                | Digite o seu email para verificarmos se você |
                | tem acesso ao nosso sistema                  |
                |                                              |
                | Digite seu email:                            |
                |                                              |
                ------------------------------------------------
                """);
        String emailUsuario = leitor.next();

        Boolean existeEmail = validacaoEmail.verificarParametro(emailUsuario);

        if (existeEmail.equals(true)) {
            MaquinaDao maquinaDao = new MaquinaDao();
            MaquinaTipoComponente maquinaTipoComponente = new MaquinaTipoComponente();
            Timer timer = new Timer();

            TimerTask inserirBanco = new TimerTask() {
                @Override
                public void run() {
                    try {
                        maquinaDao.salvar();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            timer.scheduleAtFixedRate(inserirBanco, 0, 4000);
        } else{
            System.out.println("Não foi encontrado seu email. Contrate o nosso serviço primeiro. Obrigado");
        }
    }
}