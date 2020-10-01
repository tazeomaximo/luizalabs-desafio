# luizalabs-desafio
Desafio Luiza Labs - API Produtos Favoritos por Clientes  

## Sites de referência 

#### autenticação Oauth2
Até hoje apenas utilizei ferramentas prontas para autenticação de webservice. Então tive que procurar como fazer e acabei utilizando os códigos dos links abaixo nos quais fiz pequenas modificações.  
	1. [https://medium.com/cwi-software/oauth2-com-spring-boot-2-e-spring-5-e7bfb7c58d4a](https://medium.com/cwi-software/oauth2-com-spring-boot-2-e-spring-5-e7bfb7c58d4a)  
	2. [http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/](http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/)  

### Redis
Quando utilizei o Redis, apenas elaborei uma parte da definição da arquitetura do magento. A configuração foi feita por outra pessoa. Procurei como fazer a configuração do Token, gerado para Oauth2, e utilizei o Redis como cache distribuído.  
	1. [Colocando o Token no Redis](https://medium.com/@erangadulshan.14/replace-inmemory-token-store-with-a-persistent-one-in-spring-boot-oauth2-c00a4c35f90f)  
	2. [Habilitando o Redis](https://www.rimon.xyz/2019/04/redis-as-token-store-spring-boot-oauth2/)  
	3. [Expirando chaves automaticamente no Redis](https://medium.com/@prog.tiago/expirando-chaves-automaticamente-no-redis-726d038560b2)  
	4. [Configurando tempo de vida do cache](https://cursos.alura.com.br/forum/topico-invalidar-o-cache-por-tempo-e-nao-por-cacheevict-91914)  
	5. [Apagando todo cache do Redis](https://dev.to/sr2ds/apagando-o-cache-do-redis-37b7)  

### Vagrant
Conhecia o Vagrant apenas teoricamente. Com o desafio, tive que colocar o conhecimento em prática.  
	1. [Instalando Vagrant](https://www.vagrantup.com/)  
	2. [Criando a máquina passando arquivos para ela](https://www.vagrantup.com/docs/provisioning/file.html)  
	3. [Vagrant como ambiente de Desenvolvimento](https://nandovieira.com.br/usando-o-vagrant-como-ambiente-de-desenvolvimento-no-windows)  

### Docker
Conhecia os conceitos, porém já não lembrava mais os comandos. Então utilizei os links abaixo para me ajudarem a desenvolver o Desafio.  
	1. [Docker Compose - configurando redes](https://docs.docker.com/compose/networking/)  
	2. [Instalando Docker na imagem criada pelo Vagrant](https://docs.docker.com/engine/install/debian/)  
	3. [Montando Imagem](https://dzone.com/articles/all-about-hibernate-manytomany-association)  

# Executar aplicação
- Se não tiver uma máquina linux:  
		1. Seguir os passos desse link [Vagrant como ambiente de Desenvolvimento](https://nandovieira.com.br/usando-o-vagrant-como-ambiente-de-desenvolvimento-no-windows) para instalar o VirtualBox, Vagrant e gerar a chave privada de acesso ao vagrant.  
		2. Após configurar o Vagrant, entrar na pasta do projeto e executar os comandos:  
			2.1. vagrant up - Esse comando irá criar a máquina linux  
			2.2. vagrant ssh - para acessar a máquina criada no comando acima.  
	
- Daqui em diante é para máquina linux  
		1. Instalar o [docker](https://docs.docker.com/engine/install/debian/)  
		2. Instalar o docker-compose -> https://docs.docker.com/compose/install/  
	
- Criando o ambiente em docker com docker-compose  
		1. Entrar na pasta ..\luizalabs-desafio\config  
		2. Rodar o comando abaixo para criar a imagem  
			2.1 docker build -f Dockerfile -t img-desafio .  
		3. Iniciar a aplicação
			3.1 docker-compose up --remove-orphans  
	
- Caso não queira criar em docker, basta criar:  
		1. Servidor Redis  
		2. Servidor MySql  
			2.1 Criar o banco de dados  
			2.2 Executar os script DDL e DML  
		3. Alterar os parâmetros abaixo para apontar para o servidor de vocês.  
	  
	`java -jar desafio.jar -Dspring.profiles.active=prod -Dtoken.seconds.access=120 -Dtoken.seconds.refresh=240 -Dmysql.host=192.168.15.144 -Dmysql.port=3306 -Dmysql.database=desafioluizalabs -Dspring.datasource.username=desafio -Dspring.datasource.password=desafio -Dspring.redis.host=192.168.15.144 -Dspring.redis.port=6379`
	  
# Aplicação

## Autenticação da aplicação

A autenticação deve ser feita na URL abaixo:  
		  
		* http://{ip}:{porta}/desafio/oauth/token  
	  
Os dados do **`Authorization: Basic`** são:  
		**usuário**: cliente  
		**senha**: 123  
	  
	curl --location --request POST 'http://{{ULR}}/desafio/oauth/token' \
	--header 'Authorization: Basic Y2xpZW50ZToxMjM=' \
	--form 'grant_type=password' \
	--form 'username=darbem' \
	--form 'password=123'

## Acesso 
Acessar a documentação em swagger do serviço:  

		* http://{ip}:{porta}/desafio/swagger-ui.htm
  
O json do postman pode ser acessado na pasta:  
		..\luizalabs-desafio\config\desafioluizalabs.postman_collection.json  