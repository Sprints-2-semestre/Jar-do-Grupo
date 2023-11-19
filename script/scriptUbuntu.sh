#!/bin/bash

echo -e "FÁRMACOS"
echo -e "Olá, serei seu assistente para instalação!"
echo -e "Verificando se você possui o Docker instalado na sua máquina!"
sleep 1

# Verifica se o script está sendo executado como root
if [ "$EUID" -ne 0 ]; then
  echo "Por favor, execute este script como root."
  exit 1
fi

# Atualiza a lista de pacotes
sudo apt update && sudo apt upgrade -y

# Instala o Docker
sudo apt install docker.io -y

# Exibe a versão do Docker instalada
docker --version

# Exibe mensagem indicando que o Docker foi instalado com sucesso
echo "Docker foi instalado com sucesso."

# Ativando o Docker no sistema operacional
sudo systemctl start docker

# Habilita o serviço do Docker para ser iniciado junto ao sistema operacional
sudo systemctl enable docker

# Pull da imagem do MySQL 5.7
sudo docker pull mysql:5.7

# Exibe mensagem indicando que o MySQL foi instalado com sucesso
sudo docker images

# Cria um contêiner Docker com o MySQL Workbench
sudo docker run -d -p 3306:3306 --name ContainerBdFarmacos -e "MYSQL_DATABASE=farmacos" -e "MYSQL_ROOT_PASSWORD=urubu100" mysql:5.7

# Confirma a criação do contêiner
sudo docker ps -a

# Para acessar o contêiner e manipular o banco de dados
sudo docker exec -it ContainerBdFarmacos bash <<EOF
# Entrar com senha do root
mysql -u root -p <<MYSQL_SCRIPT
# Criação do banco e das tabelas usando o utilitário mysql
CREATE DATABASE farmacos;
USE farmacos;

CREATE TABLE AME (
    idAme INT PRIMARY KEY AUTO_INCREMENT,
    nomeAme VARCHAR(45),
    cep CHAR(9)
);

CREATE TABLE maquina (
    idMaquina VARCHAR(30) PRIMARY KEY,
    sistemaOperacional VARCHAR(45),
    arquitetura INT,
    fabricante VARCHAR(45),
    tempoAtividade VARCHAR(45),
    fkAme INT,
    CONSTRAINT FK_Ame FOREIGN KEY (fkAme) REFERENCES AME(idAme)
);

CREATE TABLE permissao (
    idPermissao INT PRIMARY KEY AUTO_INCREMENT,
    tipoPermissao VARCHAR(45)
);
CREATE TABLE usuario (
    idUsuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    email VARCHAR(100),
    senha VARCHAR(45),
    cargo VARCHAR(45),
    fkAme INT,
    CONSTRAINT FK_AmeUsuario FOREIGN KEY (fkAme) REFERENCES AME(idAme),
    fkPermissaoUsuario INT,
    CONSTRAINT FK_perm FOREIGN KEY (fkPermissaoUsuario) REFERENCES permissao(idPermissao)
);
CREATE TABLE parametro (
    idParametro INT PRIMARY KEY AUTO_INCREMENT,
    nomeComponente VARCHAR(30),
    critico INT,
    preocupante INT,
    fkPermissaoParametro INT,
    CONSTRAINT FkPerm_param FOREIGN KEY (fkPermissaoParametro) REFERENCES permissao(idPermissao)
);
CREATE TABLE tipoComponente (
    idTipoComp INT PRIMARY KEY AUTO_INCREMENT,
    nomeTipoComp VARCHAR(45),
    fkParametro INT,
    CONSTRAINT fkParametroComponente FOREIGN KEY (fkParametro) REFERENCES parametro (idParametro)
);
CREATE TABLE maquinaTipoComponente (
    idMaqTipoComp INT PRIMARY KEY AUTO_INCREMENT,
    fkMaquina VARCHAR(30),
    CONSTRAINT FK_Maquina FOREIGN KEY (fkMaquina) REFERENCES maquina(idMaquina),
    fkTipoComp INT,
    CONSTRAINT FK_TipoComp FOREIGN KEY (fkTipoComp) REFERENCES tipoComponente(idTipoComp),
    numProcesLogicos INT,
    numProcesFisicos INT,
    tamanhoTotalRam INT,
    tamanhoTotalDisco INT,
    enderecoMac VARCHAR(45),
    numSerial VARCHAR(45),
    ipv4 VARCHAR(45)
);
CREATE TABLE dadosComponente (
    idDadosComponentes INT PRIMARY KEY AUTO_INCREMENT,
    fkMaquina VARCHAR(30),
    CONSTRAINT Dados_FK_Maquina FOREIGN KEY (fkMaquina) REFERENCES maquina(idMaquina),
    fkTipoComponente INT,
    CONSTRAINT Dados_FK_TipoComp FOREIGN KEY (fkTipoComponente) REFERENCES tipoComponente(idTipoComp),
    fkMaquinaTipoComponente INT,
    CONSTRAINT Dados_FK_MaqTipoComp FOREIGN KEY (fkMaquinaTipoComponente) REFERENCES maquinaTipoComponente(idMaqTipoComp),
    qtdUsoCpu DECIMAL(4, 2),
    memoriaEmUso DECIMAL(2, 1),
    memoriaDisponivel DECIMAL(2, 1),
    usoAtualDisco INT,
    usoDisponivelDisco INT,
    bytesRecebido DECIMAL(6, 2),
    bytesEnviado DECIMAL(6, 2),
    dtHora DATETIME DEFAULT CURRENT_TIMESTAMP
);"
MYSQL_SCRIPT
EOF

exit
VERSAO=17
# Verifica se o Java está instalado
if java -version &> /dev/null; then
    echo -e "Você já possui o Java instalado na sua máquina!"
    echo -e "Vamos atualizar os pacotes..."
    sleep 1
    sudo apt update && sudo apt upgrade -y
    echo -e "Pacotes atualizados!"
else
    echo -e "Você não possui o Java instalado na sua máquina!"
    echo -e "Vamos instalar o Java..."
    sleep 1
    sudo apt install openjdk-$VERSAO-jdk -y
    echo -e "Java instalado!"
fi
curl -LJO https://github.com/Sprints-2-semestre/Jar-do-Grupo/raw/main/looca-oficial/target/looca-oficial-1.0-jar-with-dependencies.jar
if [ $? -eq 0 ]; then
    # Verificando se o arquivo baixado é um arquivo .jar válido
    if [ -f looca-oficial-1.0-jar-with-dependencies.jar ]; then
        echo ""
        echo "Iniciando o software"
        sleep 1
        echo "Bem-Vindo a Fármacos"
        echo ""
        java -jar looca-oficial-1.0-jar-with-dependencies.jar
    else
        echo "Erro ao rodar o .jar"
        exit 1
    fi
else
    echo "Erro ao executar o curl"
    exit 1
fi