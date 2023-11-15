package teste;

import conexao.Conexao;
import dao.DadosComponentesDao;
import dao.MaquinaDao;
import dao.MaquinaTipoComponenteDao;
import dao.ValidacaoEmail;
import modelo.Maquina;
import modelo.MaquinaTipoComponente;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.*;

public class Teste {
    public static void main(String[] args) {
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
                    maquinaDao.salvar();
                }
            };
            timer.scheduleAtFixedRate(inserirBanco, 0, 4000);
        } else{
            System.out.println("Não foi encontrado seu email. Contrate o nosso serviço primeiro. Obrigado");
        }
    }
}