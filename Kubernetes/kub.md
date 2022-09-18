## MUITOS	 MONSTRINHOS	 –	 O	 QUE	 SÃO MICROSSERVIÇOS?

As	aplicação	poderia	tirar	ainda	mais	vantagens	da
escalabilidade	 horizontal	 que	 falamos	 anteriormente,	 cada	 um
destes	serviços	poderia	ser	escalado	individualmente.

### SYSCALL
Uma	syscall	 é	 o	 termo	 curto	 para	 System	Call	 (chamada	 de
sistema).	Um	conjunto	de	comandos	especiais	que	podem	ser
dados	diretamente	ao	kernel	do	SO	de	forma	que	este	possa	se
comunicar	 com	 os	 equipamentos	 presentes	 no	 hardware.
Uma	syscall	 pode	 ser,	 por	 exemplo,	 uma	 ordem	 de	 abertura
para	 um	 socket	 TCP	 para,	 por	 exemplo,	 realizar	 uma
requisição	HTTP	simples.

### CONTAINER REGISTRIES

Estes	são	repositórios	(públicos	ou	privados)	de	imagens	que
podem	 ser	 baixadas	 ou	 enviadas	 através	 de	APIs	 próprias.	E
são	 utilizados	 por	 ferramentas	 como	 o	 Docker	 para	 fazer	 o
download	 de	 suas	 imagens	 em	 tempo	 real,	 sem	 ter	 a
necessidade	 de	 armazenar	 todas	 elas	 na	 sua	 máquina	 ou
possuir	um	armazenamento	separado	apenas	para	isto.

### Kubernetes siginificado 

Kubernetes	é	a	palavra	grega	para	"timoneiro",	"governador"	ou
"piloto".	É	definido	como	um	sistema	open	source	para	automação,
gerência,	 escalabilidade	 e	 deploy	 de	 aplicações	 baseadas	 em
contêineres.
Ele	é	o	responsável	por	criar	os	contêineres,	gerenciar	seu
funcionamento,	manter	a	infraestrutura	em	estado	de	execução	e,
quando	um	contêiner	falha	ou	deixa	de	funcionar,	também	é	tarefa
dele	executar	uma	nova	instância,	mantendo	o	que	é	chamado	de
estado	ideal	do	cluster.

K8S?
Kubernetes	 também	pode	ser	chamado	de	k8s	porque	é	uma
letra	K	com	oito	letras	no	meio	e	depois	S,	para	aqueles	 que
gostam	de	ser	mais	concisos.

### Slave, master conceito
As	 demais	 máquinas	slaves	 que	 vão,	 de
fato,	conter	os	contêineres	rodando	nossas	aplicações,	ou	seja,	eles
serão	a	mão	de	obra	necessária	para	que	possamos	executar	o	que
precisamos	 em	 nosso	 cluster,	 enquanto	 o	 master	 apenas	 faz	 o
trabalho	de	gerência.	Separar	o	master	dos	demais	é	essencial	para
este	 tipo	 de	 arquitetura,	 pois	 ele	 deve	 ser	 o	 nó	mais	 resiliente	 de
todo	o	conjunto,	de	forma	que,	se	algo	acontecer,	temos	que	ter	a
certeza	 de	 que	 o	master	 vai	 estar	 de	 pé	 para	 corrigir	 os	 danos.	 E
isto	é	muito	mais	fácil	quando	ele	tem	somente	a	responsabilidade
de	gerenciar,	e	não	a	de	executar.

#### ETCD

O	ETCD	é	um	banco	de	dados	chave/valor	desenvolvido	pela
CoreOS,	 feito	 para	 ser	 confiável	 e	 resiliente	 no
armazenamento,	 principalmente	 sendo	 utilizado	 em
ambientes	com	arquitetura	distribuída,	como	clusters.
O	 projeto	 é	 open	 source	 e	 está	 disponível	 no	 site	 oficial:
https://coreos.com/etcd

#### Kubectl

(nome	curto	de	Kube	Control).	Este	pequeno	binário	nada	mais	é
do	que	um	conjunto	de	chamadas	REST,	pois	toda	a	ferramenta	é
construída	sobre	uma	API	RESTful,	que	é	servida	pelo	master.

Será	 com	 ele	 que	vamos	 enviar	 comandos	 e	alterar	 os	 estados	 do
nosso	 cluster,	 criar	 novos	 ou	 alterar	 workloads	 existentes,	 bem
como	 extrair	 informações	 dos	 mesmos	 e	 também	 executar
comandos	 administrativos.	 Tudo	 isto	 é	 servido	 em	 um	 pequeno
web	server	pelo	master

### SLAVE NODES

O	 oposto	 do	 master	 são	 os	 chamados	 slave	 nodes,	 ou,
simplesmente,	 nodes.	 Eles	 serão	 os	 responsáveis	 por	 executar	 o
trabalho	 que	 está	 sendo	 enviado	 pelo	 master	 como	 se	 fosse	 um
escravo	 dele	 (por	isso,	 eles	 eram	 conhecidos	 como	minions).	 São
eles	que	vão	executar	nossos	contêineres	em	estruturas	chamadas
de	pods,	que	vamos	ver	mais	à	frente.

Um	slave	é	composto	basicamente	por:
1.	 Um	ou	mais	workloads	(nossos	contêineres);
2.	 O	 engine	 do	 Docker,	 responsável	 por	 baixar	 as	 imagens	 e
       iniciar	estes	contêineres;
3.	 Um	kubelet.

### Kubelet

Portanto,	terceirizamos	este	trabalho	para	os	kubelets,	que
vão	 ser	 responsáveis	 por	 receber	 estes	 comandos	 e	 executá-los
individualmente	em	suas	próprias	máquinas.	Todo	nó	criado	pelo
master	 deverá	 obrigatoriamente	 conter	 um	 kubelet	 funcional.

Eles	 são	 o	 que	 chamamos	 de	 primary	 node	 agents,	 ou	 seja,
processos	 que	 rodarão	 dentro	 de	 um	 nó	 worker	 para,	 além	 de
executar	 os	 comandos	 enviados	 pelo	 master,	 também	 garantir
algumas	informações	como:
A	máquina	que	o	nó	está	rodando	está	saudável.
Os	contêineres	rodando	neste	nó	estão	saudáveis.

### K8s

Toda	a	linha	de	comando	é	baseada	na	estrutura	que	também	é
proposta	para	os	CLIs	dos	principais	provedores	cloud.	A	estrutura
basicamente	 segue	 uma	 ordem	 lógica:	 	 kubectl	 <comando> <recurso	 ou	 opções>	 <opções>	 --<flags>	 .	 Então,	 por
exemplo,	 se	 quisermos	 obter	 a	 descrição	 de	 um	 recurso	 rodando
no	 nosso	 cluster,	 vamos	 usar	 um	 comando	 	kubectl	 describe
meurecurso	—namespace	apis.

kubectl	version	,	que	mostra	o	número	de	versão	do	CLI	e	do
cluster.

#### kubectl	cluster-info:	
vai	se	conectar	ao	cluster,	buscar
e	 mostrar	 diversas	 informações	 como	 o	 IP	 externo	 do
cluster	e	a	localização	de	diversos	 recursos	de	 sistema	que
vamos	estudar	mais	tarde.

#### kubectl	 completion	 <bash|zsh>:	 
gera	 um	 script	 de autocomplete	 de	 código	 para	 a	 linha	 de	 comando.	 Para
executá-lo,	 siga	 o	 tutorial	 no	 link:
https://kubernetes.io/docs/tasks/tools/installkubectl/#enabling-shell-autocompletion


#### kubectl	 proxy:	 
este	 é	 um	 dos	 comandos	 mais
interessantes	e	úteis	da	ferramenta.	Ele	gerará	um	link	local
(geralmente	 	 localhost:8001	 )	 para	 que	 você	 possa
acessar	 o	 dashboard	 visual	 do	 seu	 cluster	 direto	 do	 seu
computador.	Ao	executar	o	comando,	você	deverá	ter	uma
saída	como:		Starting	to	serve	on	127.0.0.1:8001	,	a
partir	 daí	 basta	 entrar	 no	 endereço		localhost:8001		 ou
localhost:8001/ui		para	começar	a	ver	o	seu	dashboard


Para	 qualquer	 comando,	 é	 possível	 digitar	 	 kubectl
<comando>	-h		para	exibir	informações	mais	detalhadas	sobre	ele.

//TODO 53.