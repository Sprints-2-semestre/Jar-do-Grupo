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

# Configurações do Docker no Windows Server
$dockerImage = "mcr.microsoft.com/mssql/server"
$containerName = "containerBdFarmacosSqlServer"
$databasePassword = "urubu100"
$databaseName = "farmacos"

# Baixa a imagem do SQL Server do Docker Hub
docker pull $dockerImage

# Configuração do SQL Server em um contêiner Docker no ambiente Windows
docker run -e 'ACCEPT_EULA=Y' -e "SA_PASSWORD=$databasePassword" -p 1433:1433 --name $containerName -d $dockerImage

# Verifica se o contêiner foi criado com sucesso
if ($?) {
  Write-Output "SQL Server foi configurado em um contêiner Docker."
  # Aguarda alguns segundos para garantir que o SQL Server esteja pronto
  Start-Sleep -Seconds 20

  # Comandos SQL
  docker exec -i $containerName /opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P $databasePassword -d $databaseName -i - <<EOF
CREATE DATABASE farmacos;
USE farmacos;

-- Criação da tabela AME
CREATE TABLE AME (
    idAme INT PRIMARY KEY IDENTITY,
    nomeAme NVARCHAR(45),
    cep CHAR(9)
);

-- Criação da tabela maquina
CREATE TABLE maquina (
    idMaquina NVARCHAR(30) PRIMARY KEY,
    sistemaOperacional NVARCHAR(45),
    arquitetura INT,
    fabricante NVARCHAR(45),
    tempoAtividade NVARCHAR(45),
    fkAme INT,
    CONSTRAINT FK_Ame FOREIGN KEY (fkAme) REFERENCES AME(idAme)
);

-- Criação da tabela permissao
CREATE TABLE permissao (
    idPermissao INT PRIMARY KEY IDENTITY,
    tipoPermissao NVARCHAR(45)
);

-- Criação da tabela usuario
CREATE TABLE usuario (
    idUsuario INT PRIMARY KEY IDENTITY,
    nome NVARCHAR(100),
    email NVARCHAR(100),
    senha NVARCHAR(45),
    cargo NVARCHAR(45),
    fkAme INT,
    CONSTRAINT FK_AmeUsuario FOREIGN KEY (fkAme) REFERENCES AME(idAme),
    fkPermissaoUsuario INT,
    CONSTRAINT FK_perm FOREIGN KEY (fkPermissaoUsuario) REFERENCES permissao(idPermissao)
);

-- Criação da tabela parametro
CREATE TABLE parametro (
    idParametro INT PRIMARY KEY IDENTITY,
    nomeComponente NVARCHAR(30),
    critico INT,
    preocupante INT,
    fkPermissaoParametro INT,
    CONSTRAINT FkPerm_param FOREIGN KEY (fkPermissaoParametro) REFERENCES permissao(idPermissao)
);

-- Criação da tabela tipoComponente
CREATE TABLE tipoComponente (
    idTipoComp INT PRIMARY KEY IDENTITY,
    nomeTipoComp NVARCHAR(45),
    fkParametro INT,
    CONSTRAINT fkParametroComponente FOREIGN KEY (fkParametro) REFERENCES parametro(idParametro)
);

-- Criação da tabela maquinaTipoComponente
CREATE TABLE maquinaTipoComponente (
    idMaqTipoComp INT PRIMARY KEY IDENTITY,
    fkMaquina NVARCHAR(30),
    CONSTRAINT FK_Maquina FOREIGN KEY (fkMaquina) REFERENCES maquina(idMaquina),
    fkTipoComp INT,
    CONSTRAINT FK_TipoComp FOREIGN KEY (fkTipoComp) REFERENCES tipoComponente(idTipoComp),
    numProcesLogicos INT,
    numProcesFisicos INT,
    tamanhoTotalRam INT,
    tamanhoTotalDisco INT,
    enderecoMac NVARCHAR(45),
    numSerial NVARCHAR(45),
    ipv4 NVARCHAR(45)
);

-- Criação da tabela dadosComponente
CREATE TABLE dadosComponente (
    idDadosComponentes INT PRIMARY KEY IDENTITY,
    fkMaquina NVARCHAR(30),
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
);

-- Inserção de dados nas tabelas permissao e tipoComponente
INSERT INTO permissao (tipoPermissao) VALUES ('NOC');
INSERT INTO permissao (tipoPermissao) VALUES ('Visualização');

INSERT INTO tipoComponente (nomeTipoComp) VALUES ('CPU');
INSERT INTO tipoComponente (nomeTipoComp) VALUES ('RAM');
INSERT INTO tipoComponente (nomeTipoComp) VALUES ('DISCO');
INSERT INTO tipoComponente (nomeTipoComp) VALUES ('REDE');

-- Exemplo de consulta SQL
SELECT 
    maquina.sistemaOperacional,
    maquina.arquitetura,
    maquina.fabricante,
    maquina.tempoAtividade,
    maquinaTipoComponente.numProcesLogicos,
    maquinaTipoComponente.numProcesFisicos,
    maquinaTipoComponente.tamanhoTotalRam,
    maquinaTipoComponente.numSerial,
    maquinaTipoComponente.tamanhoTotalDisco,
    maquinaTipoComponente.enderecoMac,
    maquinaTipoComponente.ipv4,
    dadosComponente.bytesRecebido,
    dadosComponente.bytesEnviado,
    dadosComponente.qtdUsoCpu,
    dadosComponente.memoriaEmUso,
    dadosComponente.memoriaDisponivel,
    dadosComponente.usoAtualDisco,
    dadosComponente.usoDisponivelDisco
FROM 
    maquina
JOIN 
    maquinaTipoComponente ON maquina.idMaquina = maquinaTipoComponente.fkMaquina
JOIN 
    dadosComponente ON maquina.idMaquina = dadosComponente.fkMaquina
WHERE 
    maquina.idMaquina = 'BFEBFBFF000A0660';

-- Exemplo de consulta SQL
SELECT * FROM dadosComponente;

-- Exemplo de consulta SQL
SELECT * FROM usuario;
EOF
} else {
  Write-Error "Erro ao configurar o SQL Server em um contêiner Docker. Verifique a instalação e tente novamente."
  exit 1
}
