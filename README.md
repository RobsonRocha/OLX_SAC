## OLX_SAC

## Motiva��o

Projeto criado para registar os chamados que podem chegar via chat, e-mail ou telefone.
Os usu�rios que t�m acesso a esse sistema s�o cadastrados via CreateUserOLX_SAC.
Os atendentes do SAC registram os chamados preenchendo o formul�rio onde informam o tipo do chamado (telefone, chat, email), estado (ex: RJ, ES, etc), o motivo do chamado (d�vidas, elogios, sugest�es) e a descri��o do chamado que � um campo de texto livre onde os atendentes ir�o registrar os detalhes da conversa com o cliente.
O supervisor poder� ver todos os chamados.

## Linguagem

A linguagem utilizada � Java, com utiliza��o de JSF na apresenta��o das telas e hibernate para manipula��o do banco de dados.

## Compila��o

Para facilitar a importa��o de bibliotecas e a compila��o dos arquivos em um �nico pacote, foi utilizado Maven.
Para compilar gerando o pacote basta executar o comando mvn -DskipTests compile package na linha de comando.
Na pasta target ser�o gerados v�rios arquivos, mas o paote principal � gerado com o nome sacweb-1.0-SNAPSHOT.war

##Testes

Para os testes foram utilizadas as bibliotecas TestNG e Seleninum.
Para usar o Selenium foi necess�rio o driver para o navegador, existem v�rios drivers para todos os navegadores, o utilizado aqui � o Chrome Driver, cujo execut�vel est� no projeto.
Para executar os testes basta escrever na linha de comando mvn test -Dwebdriver.base.url=http://endereco/sacweb -Dwebdriver.chrome.driver=caminhoDoArquivo/chromedriver.exe.
Onde endereco � a url do projeto no navegador e caminhoDoArquivo � o caminho completo aonde est� o arquivo do driver.

## Execu��o

O container utilizado para execu��o do projeto foi o Tomcat 8.0 com a configura��o padr�o.


## Banco de dados

A cria��o do banco est� no arquivo CREATEDB.
