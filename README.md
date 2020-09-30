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
	4. [Vagrant como ambiente de Desenvolvimento](https://nandovieira.com.br/usando-o-vagrant-como-ambiente-de-desenvolvimento-no-windows)

### Docker
Conheço os conceitos e fiz um curso na minha antiga empresa, porém já não lembrava mais os comandos. Então utilizei os links abaixo para me ajudarem a desenvolver o Desafio.

	1. [Docker Compose - configurando redes](https://docs.docker.com/compose/networking/)
	2. [Instalando Docker na imagens criada pelo Vagrant](https://docs.docker.com/engine/install/debian/)


# Aplicação

Acessar a documentação em swagger do serviço

		http://{ip}:{port}/desafio/swagger-ui.htm


# Executar aplicação

	- Se não estiver uma maquina linux:
		1. Seguir os passos desse link [Vagrant como ambiente de Desenvolvimento](https://nandovieira.com.br/usando-o-vagrant-como-ambiente-de-desenvolvimento-no-windows) para instalar o VirtualBox, Vagrant e gerar a chave privada de acesso ao vagrant.
		2. Após configurar o Vagrant, entrar na pasta do projeto e executar o comandos:
			3.1. vagrant up - Esse comando irá criar a máquina linux
			3.2. vagrant ssh - para acessar a máquina criada no comando acima.
	
	- Daqui em diante é para maquinas linux
		1. Instalar o [docker](https://docs.docker.com/engine/install/debian/)
		2. Instalar o docker-compose -> https://docs.docker.com/compose/install/
	
	- Criando o ambiente
		1. Rodar o comando abaixo para criar a imagem
			1.1 docker build -f Dockerfile -t img-desafio .
			1.2 docker-compose up --remove-orphans
	
	
	
	
	