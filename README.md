## SACWeb

## Motiva��o

Projeto exemplo criado para registrar os chamados de um SAC que podem chegar via chat, e-mail ou telefone.
Os usu�rios que t�m acesso a esse sistema s�o cadastrados via [CreateUser](https://github.com/RobsonRocha/CreateUser).
Os atendentes do SAC registram os chamados preenchendo o formul�rio onde informam o tipo do chamado (telefone, chat, email), estado (ex: RJ, ES, etc), o motivo do chamado (d�vidas, elogios, sugest�es) e a descri��o do chamado que � um campo de texto livre onde os atendentes ir�o registrar os detalhes da conversa com o cliente.
O supervisor poder� ver todos os chamados.

## Linguagem

A linguagem utilizada � Java, com utiliza��o de JSF com componentes [Primefaces](http://www.primefaces.org/) na apresenta��o das telas e [Hibernate](http://hibernate.org/) para manipula��o do banco de dados.

## Compila��o

Para facilitar a importa��o de bibliotecas e a compila��o dos arquivos em um �nico pacote, foi utilizado [Maven v3](https://maven.apache.org/).
Para compilar gerando o pacote basta executar o comando abaixo na linha de comando.

```mvn -DskipTests compile package```

Na pasta target ser�o gerados v�rios arquivos, mas o pacote principal � gerado com o nome `sacweb-1.0-SNAPSHOT.war`.

## Banco de dados

O banco de dados usado foi o PostgreSQL.
A cria��o do banco est� no arquivo CREATEDB.

##Testes

Para os testes foram utilizadas as bibliotecas [TestNG](http://testng.org/doc/index.html) e [Seleninum](http://www.seleniumhq.org/).
Para usar o Selenium foi necess�rio o driver para o navegador, existem v�rios drivers para todos os navegadores, o utilizado aqui � o [Chrome Driver](https://sites.google.com/a/chromium.org/chromedriver/getting-started), cujo execut�vel est� no projeto e o FireFox, cujo driver j� vem com a biblioteca do Selenium.
Para Linux e Mac � poss�vel baixar a vers�o 2.9 do Chrome Driver (mais recente, por enquanto) [aqui.](https://chromedriver.storage.googleapis.com/index.html?path=2.9/)
Para executar os testes utilizando o navegador Chrome, basta escrever na linha de comando abaixo com o sistema no ar.

```mvn test -Dwebdriver.base.url=http://endereco/sacweb -Dwebdriver.chrome.driver=[caminhoDoArquivo]/chromedriver.exe```

Para usar o navegador FireFox, basta escrever na linha de comando abaixo com o sistema no ar.

```mvn test -Dwebdriver.base.url=http://endereco/sacweb -Dwebdriver.using.firefox=true```

Onde endere�o � a url do projeto no navegador e caminhoDoArquivo � o caminho completo aonde est� o arquivo do driver.

## Execu��o

O container utilizado para execu��o do projeto foi o [Tomcat 8.0](http://tomcat.apache.org/download-80.cgi) com a configura��o padr�o.



