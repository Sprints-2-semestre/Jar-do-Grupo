#!/bin/bash

VERSAO=17
echo -e "FÁRMACOS"
echo -e "Olá usuário, serei seu assistente para instalação do Java!"
echo -e "Verificando se você possui o Java instalado na sua máquina!"

# Verifica se o Java está instalado
if java -version &> /dev/null; then
    echo -e "Você já possui o Java instalado na sua máquina!"
    
   
echo -e "Vamos atualizar os pacotes..."
    sudo apt update && sudo apt upgrade -y
    clear

    sudo apt update && sudo apt upgrade

    sudo apt
echo -e "Pacotes atualizados!"
else
    echo -e "Não foi encontrada nenhuma versão do Java na sua máquina, mas iremos resolver isso!"
    echo -e "Você deseja instalar o Java na sua máquina (S/N)?"
    read inst

    if [ "$inst" == "S" ] || [ "$inst" == "s" ]; then
        echo -e "Ok! Você decidiu instalar o Java na sua máquina."

        # Instala o Java
        sudo apt install openjdk-17-jre y
        
        echo -e "Java instalado com sucesso!"
    else
        echo -e "Entendido, você optou por não instalar o Java. Até a próxima!"
	exit 1
    fi

mkdir dataxtract
cd dataxtract
git clone "https://github.com/Sprints-2-semestre/Jar-do-Grupo.git"
find . -name "*.jar" -exec chmod +x {} \;
sudo java -jar dataxtract.jar
fi
mkdir dataxtract
cd dataxtract
git clone "https://github.com/Sprints-2-semestre/Jar-do-Grupo.git"

cd Jar-do-Grupo\looca-oficial\target\

find . -name "*.jar" -exec chmod +x {} \;
sudo java -jar looca-oficial-1.0-jar-with-dependencies.jar