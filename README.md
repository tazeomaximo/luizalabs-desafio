# luizalabs-desafio
Desafio Luiza Labs - API Produtos Favoritos por Clientes

## Sites de referência 

#### autenticação Oauth2
Até hoje apenas utilizei ferramentas prontas para autenticação de webservice. Então tive que procurar como fazer e acabei utilizando os códigos dos links abaixo, fiz pequenas modificações.

	1.[https://medium.com/cwi-software/oauth2-com-spring-boot-2-e-spring-5-e7bfb7c58d4a](https://medium.com/cwi-software/oauth2-com-spring-boot-2-e-spring-5-e7bfb7c58d4a)
	2.[http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/](http://websystique.com/spring-security/secure-spring-rest-api-using-oauth2/)

### Redis
O Redis apenas fiz parte da definição da arquitetura do magento, a configuração foi outra pessoa que fez. Então tive que procurar como fazer a configuração para o Token gerado pelo Oauth2 e para cache da chamado do WebService de produtos
	1. [Colocando o Token no Redis] (https://medium.com/@erangadulshan.14/replace-inmemory-token-store-with-a-persistent-one-in-spring-boot-oauth2-c00a4c35f90f)
	2. [Habilitando o Redis](https://www.rimon.xyz/2019/04/redis-as-token-store-spring-boot-oauth2/)
	3. [Expirando chaves automaticamente no Redis](https://medium.com/@prog.tiago/expirando-chaves-automaticamente-no-redis-726d038560b2)
	4. [Configurando tempo de vida do cache](https://cursos.alura.com.br/forum/topico-invalidar-o-cache-por-tempo-e-nao-por-cacheevict-91914)
	5. [Apagando todo cache do Redis](https://dev.to/sr2ds/apagando-o-cache-do-redis-37b7)

### Vagrant
O Vagrant tinha visto apenas no curso, agora com o handson tive que buscar o conhecimento novamente.

	1. [Instalando Vagrant](https://www.vagrantup.com/)
	2. [Criando a máquina passando arquivos para ela](https://www.vagrantup.com/docs/provisioning/file.html)
	3. [Instalando Docker na imagens criada pelo Vagrant](https://docs.docker.com/engine/install/debian/)

### Docker
Conheço os conceitos e fiz um curso na minha antiga empresa, porém já não lembrava mais os comandos. Então utilizei os links abaixo para me ajudarem a desenvolver o Desafio.

	1. [Docker Compose - configurando redes](https://docs.docker.com/compose/networking/)
	2. [Instalando Docker na imagens criada pelo Vagrant](https://docs.docker.com/engine/install/debian/)


# Aplicação

Acessar a documentação em swagger do serviço

		http://{ip}:{port}/desafio/swagger-ui.htm


# Executar aplicação

	instalar o vagrant -> https://www.vagrantup.com/
	
	depois de intalado copiar o arquivo Vagrant
	
	Atualizar debian e instalar o docker ->
	começar por esse se o debaixo der erro na lib https://www.hostinger.com.br/tutoriais/install-docker-ubuntu
	https://docs.docker.com/engine/install/debian/
	
	docker-compose -> https://docs.docker.com/compose/install/
	
	sudo apt update
	sudo apt upgrade