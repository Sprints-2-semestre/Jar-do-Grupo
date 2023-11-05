package teste;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;
import com.github.britooo.looca.api.group.discos.DiscoGrupo;
import com.github.britooo.looca.api.group.memoria.Memoria;
import com.github.britooo.looca.api.group.processador.Processador;
import com.github.britooo.looca.api.group.rede.Rede;
import com.github.britooo.looca.api.group.rede.RedeInterface;
import com.github.britooo.looca.api.group.sistema.Sistema;
import dao.DadosComponentesDao;
import dao.MaquinaDao;
import dao.MaquinaTipoComponeneteDao;
import oshi.SystemInfo;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Teste {
    public static void main(String[] args) {
        MaquinaTipoComponeneteDao maquinaTipoComponeneteDao = new MaquinaTipoComponeneteDao();
        MaquinaDao maquinaDao = new MaquinaDao();
        DadosComponentesDao dadosComponentesDao = new DadosComponentesDao();

        maquinaDao.salvar();

        Timer timer = new Timer();
        TimerTask inserirBanco = new TimerTask() {
            @Override
            public void run() {
                maquinaTipoComponeneteDao.salvar();
                dadosComponentesDao.salvar();
            }
        };
        timer.scheduleAtFixedRate(inserirBanco, 0, 1000);
    }
}