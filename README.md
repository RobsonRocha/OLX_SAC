## OLX_SAC

## Motivação

Projeto criado para registar os chamados de um SAC que podem chegar via chat, e-mail ou telefone.
Os usuários que têm acesso a esse sistema são cadastrados via [CreateUserOLX_SAC](https://github.com/RobsonRocha/CreateUserOLX_SAC).
Os atendentes do SAC registram os chamados preenchendo o formulário onde informam o tipo do chamado (telefone, chat, email), estado (ex: RJ, ES, etc), o motivo do chamado (dúvidas, elogios, sugestões) e a descrição do chamado que é um campo de texto livre onde os atendentes irão registrar os detalhes da conversa com o cliente.
O supervisor poderá ver todos os chamados.

## Linguagem

A linguagem utilizada é Java, com utilização de JSF com componentes [Primefaces](http://www.primefaces.org/) na apresentação das telas e [Hibernate](http://hibernate.org/) para manipulação do banco de dados.

## Compilação

Para facilitar a importação de bibliotecas e a compilação dos arquivos em um único pacote, foi utilizado [Maven](https://maven.apache.org/).
Para compilar gerando o pacote basta executar o comando abaixo na linha de comando.

```mvn -DskipTests compile package```

Na pasta target serão gerados vários arquivos, mas o pacote principal é gerado com o nome `sacweb-1.0-SNAPSHOT.war`.

## Banco de dados

O banco de dados usado foi o PostgreSQL.
A criação do banco está no arquivo CREATEDB.

##Testes

Para os testes foram utilizadas as bibliotecas [TestNG](http://testng.org/doc/index.html) e [Seleninum](http://www.seleniumhq.org/).
Para usar o Selenium foi necessário o driver para o navegador, existem vários drivers para todos os navegadores, o utilizado aqui é o [Chrome Driver](https://sites.google.com/a/chromium.org/chromedriver/getting-started), cujo executável está no projeto.
Para executar os testes basta escrever na linha de comando

```mvn test -Dwebdriver.base.url=http://endereco/sacweb -Dwebdriver.chrome.driver=[caminhoDoArquivo]/chromedriver.exe```

Onde endereço é a url do projeto no navegador e caminhoDoArquivo é o caminho completo aonde está o arquivo do driver.

## Execução

O container utilizado para execução do projeto foi o Tomcat 8.0 com a configuração padrão.



