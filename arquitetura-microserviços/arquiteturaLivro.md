## Como refatorar um monolítico em microsserviços

A equipe FTGO também tem problemas para dimensionar seu aplicativo. Isso ocorre porque diferentes
módulos de aplicativos têm requisitos de recursos conflitantes. Os dados do restaurante, por exemplo,
são armazenados em um grande banco de dados na memória, idealmente implantado em servidores
com muita memória. Por outro lado, o módulo de processamento de imagem é intensivo em CPU e
melhor implantado em servidores com muita CPU. Como esses módulos fazem parte do mesmo
aplicativo, o FTGO deve comprometer a configuração do servidor.

Outro problema com o aplicativo FTGO é a falta de confiabilidade. Como resultado, há frequentes
interrupções na produção. Uma razão pela qual não é confiável é que testar o aplicativo completamente
é difícil, devido ao seu grande tamanho. Essa falta de testabilidade significa que os bugs entram na
produção.

[Código git](https://github.com/eventuate-tram/eventuate-tram-sagas)

//página 38

1.4.1 Cubo

![Exemplo Domínios](./diagrama_cubo_.png)


O cubo de escala define três maneiras separadas de dimensionar um aplicativo: a
escalabilidade do eixo X equilibra as solicitações em várias instâncias idênticas; As solicitações
de roteamento de escala do eixo Z com base em um atributo da solicitação; O eixo Y decompõe
funcionalmente um aplicativo em serviços.

ESCALA DO EIXO X BALANÇOS DE CARGA SOLICITAÇÕES EM
MÚLTIPLAS INSTÂNCIAS A escala do eixo X é uma maneira comum de escalar um aplicativo monolítico.
A Figura 1.4 mostra como funciona a escala do eixo X. Você executa várias instâncias do aplicativo atrás
de um balanceador de carga. O balanceador de carga distribui as solicitações entre as N instâncias
idênticas do aplicativo. Essa é uma ótima maneira de melhorar a capacidade e a disponibilidade de um
aplicativo.

ESCALA DO EIXO Z ROTA SOLICITAÇÕES BASEADAS EM UM ATRIBUTO
DA SOLICITAÇÃO A escala do eixo Z também executa várias instâncias do aplicativo monolítico, mas
diferentemente da escala do eixo X, cada instância é responsável por apenas um subconjunto dos dados.
A Figura 1.5 mostra como funciona a escala do eixo Z. O roteador na frente das instâncias usa um
atributo de solicitação para roteá-lo para a instância apropriada. Um aplicativo pode, por exemplo, rotear
solicitações usando userId.

![Exemplo Domínios](./monolito_escalando.png)

![Exemplo Domínios](./monolito_escalar_outra_forma.png)

O dimensionamento do eixo Z é uma ótima maneira de dimensionar um aplicativo para lidar com volumes crescentes de transações e dados.


#### O ESCALAMENTO DO EIXO Y DECOMPÕE FUNCIONALMENTE UM APLICATIVO EM SERVIÇOS

![Exemplo Domínios](./escalando-microserviços.png)

Um serviço é um miniaplicativo que implementa funcionalidade com foco restrito, como gerenciamento de
pedidos, gerenciamento de clientes e assim por diante. Um serviço é dimensionado usando a escala do eixo
X, embora alguns serviços também possam usar a escala do eixo Z. Por exemplo, o serviço Order consiste
em um conjunto de instâncias de serviço com balanceamento de carga.
A definição de alto nível da arquitetura de microsserviços (microsserviços) é um estilo de arquitetura
que decompõe funcionalmente um aplicativo em um conjunto de serviços. Note que esta definição não diz
nada sobre tamanho. Em vez disso, o que importa é que cada serviço tenha um conjunto de responsabilidades
focado e coeso.


### 1.4.2 Microsserviços como forma de modularidade

A modularidade é essencial ao desenvolver aplicativos grandes e complexos. Um aplicativo moderno como
o FTGO é muito grande para ser desenvolvido por um indivíduo. Também é muito complexo para ser
entendido por uma única pessoa. Os aplicativos devem ser decompostos em módulos que são desenvolvidos
e compreendidos por diferentes pessoas.

Em um aplicativo monolítico, os módulos são definidos usando
uma combinação de construções de linguagem de programação (como pacotes Java) e artefatos de
construção (como arquivos Java JAR). No entanto, como os desenvolvedores do FTGO descobriram, essa
abordagem tende a não funcionar bem na prática. Aplicações monolíticas de longa duração geralmente
degeneram em grandes teias de aranhas.

A arquitetura de microsserviços usa serviços como unidade de modularidade.

Como resultado, é muito mais fácilpreservar a modularidade do aplicativo ao longo do tempo. Existem outros benefícios de usar serviços como
blocos de construção, incluindo a capacidade de implantá-los e escalá-los de forma independente.

### Regra de OURO: Cada serviço possui seu próprio banco de dados

Uma característica chave da arquitetura de microsserviço é que os serviços são fracamente acoplados e se
comunicam apenas por meio de APIs. Uma maneira de obter acoplamento flexível é cada serviço ter seu
próprio armazenamento de dados. Na loja online, por exemplo, o Serviço de Pedidos possui um banco de
dados que inclui a tabela PEDIDOS , e o Atendimento ao Cliente possui seu banco de dados, que inclui a
tabela CLIENTES . No momento do desenvolvimento, os desenvolvedores podem alterar o esquema de um
serviço sem precisar coordenar com os desenvolvedores que trabalham em outros serviços. No tempo de
execução, os serviços são isolados uns dos outros — por exemplo, um serviço nunca será bloqueado porque
outro serviço mantém um bloqueio de banco de dados.


### A arquitetura de microsserviços exemplo FTGO

![Exemplo Domínios](./diagrama-ftgo.png)

Ponto forte, cada um pode ser desenvolvido, testado, implantado e dimensionado de forma independente.

![Exemplo Domínios](./diagrama_microservicos_ftgo.png)

### A linguagem padrão de arquitetura de microsserviços

Arquitetura e design têm tudo a ver com tomada de decisões. Você precisa decidir se a arquitetura
monolítica ou de microsserviço é a mais adequada para seu aplicativo.

Uma boa maneira de descrever as várias opções de arquitetura e design e melhorar a tomada de
decisões é usar uma linguagem de padrões. Vamos primeiro ver por que precisamos de padrões e
de uma linguagem de padrões e, em seguida, faremos um tour pela linguagem de padrões de
arquitetura de microsserviços.

Um padrão é uma solução reutilizável para um problema que ocorre em um contexto particular.

Deploy a service as a container é uma especialização de Single service per host.

![Exemplo Domínios](./diagrama_contexto_consequencias.png)


### Visão geral da linguagem padrão de arquitetura de microsserviços

A linguagem de padrão de arquitetura de microsserviço é uma coleção de padrões que ajudam você a arquitetar um aplicativo usando a arquitetura de microsserviço

A linguagem padrão primeiro ajuda você a decidir se deve usar a
arquitetura de microsserviço. Ele descreve a arquitetura monolítica e a arquitetura de microsserviços,
juntamente com seus benefícios e desvantagens. Então, se a arquitetura de microsserviço for
adequada para seu aplicativo, a linguagem padrão ajudará você a usá-la de forma eficaz, resolvendo
vários problemas de arquitetura e design

A linguagem padrão consiste em vários grupos de padrões. À esquerda na figura 1.10 está o
grupo de padrões de arquitetura de aplicativos, o padrão de arquitetura monolítica e o padrão de
arquitetura de microsserviços. Esses são os padrões que discutimos

![Exemplo Domínios](./diagrama_complexidade_microservico.png)


#### PADRÕES PARA DECOMPOSIÇÃO DE UM APLICATIVO EM SERVIÇOS

Decidir como decompor um sistema em um conjunto de serviços é uma arte, mas há várias estratégias que
podem ajudar. Os dois padrões de decomposição mostrados na figura 1.11 são estratégias diferentes que você
pode usar para definir a arquitetura de seu aplicativo

#### PADRÕES DE COMUNICAÇÃO

![Exemplo Domínios](./padroes_comunicação.png)

![Exemplo Domínios](./comunicacoes.png)

![Exemplo Domínios](./implementacao_servicos.png)

#### PADRÕES DE OBSERVABILIDADE FORNECEM INSIGHTS SOBRE O COMPORTAMENTO DO APLICATIVO

entender e diagnosticar problemas em uma arquitetura de microsserviço é muito
mais complicado. Uma solicitação pode oscilar entre vários serviços antes que uma resposta seja
finalmente retornada a um cliente. Conseqüentemente, não há um arquivo de log para examinar. Da
mesma forma, problemas com latência são mais difíceis de diagnosticar porque existem vários
suspeitos.

#### PADRÕES PARA O TESTE AUTOMÁTICO DE SERVIÇOS

A arquitetura de microsserviço facilita o teste de serviços individuais porque eles são muito menores do que o aplicativo
monolítico. Ao mesmo tempo, porém, é importante testar se os diferentes serviços funcionam juntos, evitando o uso de
testes de ponta a ponta complexos, lentos e frágeis que testam vários serviços juntos. Aqui estão os padrões para simplificar
o teste testando serviços isoladamente:
* Teste de contrato orientado ao consumidor — Verifica se um serviço atende às expectativas de seu
clientes.
* Teste de contrato do lado do consumidor - Verifique se o cliente de um serviço pode se comunicar
com o serviço.
* Teste de componente de serviço 
* Teste um serviço isoladamente.

Padrão Microservice Chassis -- 

### Além dos microsserviços: processo e organização

Para um aplicativo
grande e complexo, a arquitetura de microsserviço geralmente é a melhor escolha. Mas, além de ter a
arquitetura certa, o desenvolvimento de software de sucesso exige que você também tenha organização
e processos de desenvolvimento e entrega.

![Exemplo Domínios](./processo_entrega.png)

#### Desenvolvimento de software e organização de entrega

O sucesso inevitavelmente significa que a equipe de engenharia crescerá. Por um lado, isso é bom
porque mais desenvolvedores podem fazer mais. O problema com equipes grandes é, como Fred
Brooks escreveu em The Mythical Man-Month, a comunicação aérea de uma equipe de tamanho N é
O(N^2). Se a equipe ficar muito grande, ela se tornará ineficiente devido à sobrecarga de comunicação. 
Imagine, por exemplo, tentar fazer uma diária com 20 pessoas


Para  entregar software de forma eficaz ao usar a arquitetura de microsserviços, você precisa levar em
consideração a lei de Conway (https://en.wikipedia.org/wiki/Conway%27s _law), que afirma o seguinte:
Organizações que projetam sistemas … são obrigadas a produzir projetos que são cópias das
estruturas de comunicação dessas organizações.
Melvin Conway
Em outras palavras, a arquitetura do seu aplicativo reflete a estrutura da organização que o desenvolveu.
É importante, portanto, aplicar a lei de Conway ao contrário (www.thoughtworks.com/radar/techniques/
inverse-conway-maneuver) e projete sua organização para que sua estrutura espelhe sua arquitetura de
microsserviço. Ao fazer isso, você garante que suas equipes de desenvolvimento sejam tão frouxamente
acopladas quanto os serviços.

#### Processo de desenvolvimento e entrega de software

Usar a arquitetura de microsserviços com um processo de desenvolvimento em cascata é como
dirigir uma Ferrari puxada a cavalo — você desperdiça a maior parte do benefício de usar
microsserviços. Se você deseja desenvolver um aplicativo com a arquitetura de microsserviços, é
essencial adotar práticas ágeis de desenvolvimento e implantação, como Scrum ou Kanban.

#### Mova-se rapidamente sem quebrar as coisas

O objetivo da entrega/implantação contínua (e, mais geralmente, DevOps) é entregar
software de maneira rápida, mas confiável. Quatro métricas úteis para avaliar o desenvolvimento
de software são as seguintes:

* Frequência de implantação—Com que frequência o software é implantado na produção ÿ
Lead time—Tempo de um desenvolvedor verificando uma alteração até que a alteração seja
implantado
* Tempo médio de recuperação—Tempo de recuperação de um problema de produção
* Taxa de falha de alteração—Percentual de alterações que resultam em um problema de produção


#### O lado humano da adoção de microsserviços

Adotar a arquitetura de microsserviço muda sua arquitetura, sua organização e seus processos
de desenvolvimento. Em última análise, porém, muda o ambiente de trabalho das pessoas, que
são, como mencionado anteriormente, criaturas emocionais. Se ignoradas, suas emoções podem
tornar a adoção de microsserviços uma jornada acidentada.

1. Terminar, Perder e Deixar Ir — O período de turbulência emocional e resistência quando
   as pessoas se deparam com uma mudança que as força a sair de sua zona de conforto.
   Muitas vezes lamentam a perda da velha maneira de fazer as coisas. Por exemplo,
   quando as pessoas se reorganizam em equipes multifuncionais, elas sentem falta de seus
   ex-companheiros de equipe. Da mesma forma, um grupo de modelagem de dados que
   possui o modelo de dados global será ameaçado pela ideia de cada serviço ter seu próprio
   modelo de dados.

2. A Zona Neutra — O estágio intermediário entre as velhas e as novas maneiras de fazer as coisas,
onde as pessoas geralmente se confundem. Muitas vezes, eles estão lutando para aprender a
nova maneira de fazer as coisas.

3. O novo começo—O estágio final em que as pessoas abraçaram com entusiasmo a nova maneira de
fazer as coisas e estão começando a experimentar os benefícios.


## Estratégias de decomposição

O principal desafio, que é a essência da arquitetura de microsserviços, é a decomposição funcional da aplicação em serviços.
O primeiro e mais importante aspecto da arquitetura é, portanto, a definição dos serviços.
Os serviços são organizados em torno de preocupações comerciais, e não técnicas.

### O que é exatamente a arquitetura de microsserviços?

Tradicionalmente, o objetivo da arquitetura tem sido escalabilidade, confiabilidade e segurança.
Mas hoje é importante que a arquitetura também permita a entrega rápida e segura de software.

#### O que é arquitetura de software e por que isso importa?

A arquitetura de software de um sistema computacional é o conjunto de estruturas necessárias para raciocinar
sobre o sistema, que compreende os elementos de software, as relações entre eles e as propriedades de ambos.

Mas sua essência é que a arquitetura de um aplicativo é sua decomposição em partes (os elementos) e os relacionamentos (as relações) entre essas partes.

* Facilita a divisão do trabalho e do conhecimento. Ele permite que várias pessoas (ou várias
equipes) com conhecimento possivelmente especializado trabalhem produtivamente juntas
em um aplicativo. 
* Define como os elementos de software interagem.

A arquitetura é importante porque permite que um aplicativo satisfaça a segunda categoria de
requisitos: seus requisitos de qualidade de serviço. Estes também são conhecidos como atributos de
qualidade e são os chamados -ilities. Os requisitos de qualidade de serviço definem as qualidades de
tempo de execução, como escalabilidade e confiabilidade.

#### SOBRE O ESTILO DE ARQUITETURA HEXAGONAL

A arquitetura hexagonal é uma alternativa ao estilo arquitetônico em camadas. Como mostra a figura 2.2, o estilo de
arquitetura hexagonal organiza a visão lógica de forma a colocar a lógica de negócios no centro. Em vez da camada
de apresentação, o aplicativo possui um ou mais adaptadores de entrada que manipulam solicitações de fora
invocando a lógica de negócios.

![Exemplo Domínios](./hexazonal.png)

#### Exemplo microserviços

![Exemplo Domínios](./diagrama-microserviços.png)


Uma restrição importante imposta pela arquitetura de microsserviço é que os serviços são
fracamente acoplados. Consequentemente, há restrições sobre como os serviços colaboram.
Para explicar essas restrições, tentarei definir o termo serviço, descrever o que significa ser
fracamente acoplado e explicar por que isso é importante.

#### O QUE É UM SERVIÇO?

Um serviço é um componente de software independente e implementável que implementa algumas funcionalidades úteis.
Um serviço tem uma API que fornece aos seus clientes acesso à sua funcionalidade.
Existem dois tipos de operações: comandos e consultas. A API consiste em comandos, consultas
e eventos. Um comando, como createOrder(), executa ações de formulários e atualiza dados. Uma
consulta, como findOrderById(), recupera dados. Um serviço também publica eventos, como
OrderCreated, que são consumidos por seus clientes

#### O QUE É ACOPLAMENTO SOLTO?

A exigência de que os serviços sejam fracamente acoplados e que colaborem apenas por meio de
APIs proíbe que os serviços se comuniquem por meio de um banco de dados. Você deve tratar os
dados persistentes de um serviço como os campos de uma classe e mantê-los privados. Manter os
dados privados permite que um desenvolvedor altere o esquema de banco de dados de seu serviço sem precisar
gaste tempo coordenando com desenvolvedores que trabalham em outros serviços.

Ele garante, por exemplo,
que um serviço não pode conter bloqueios de banco de dados que bloqueiam outro serviço. Mais tarde,
porém, você aprenderá que **uma desvantagem de não compartilhar bancos de dados é que manter a
consistência dos dados e consultar os serviços é mais complexo.**

#### O PAPEL DAS BIBLIOTECAS COMPARTILHADAS

Imagine, por exemplo, que diversos serviços precisem atualizar o objeto de negócios Order . Uma
abordagem é empacotar essa funcionalidade como uma biblioteca usada por vários serviços. Por um lado,
usar uma biblioteca elimina a duplicação de código. **Por outro lado, considere o que acontece quando os
requisitos são alterados de forma a afetar o objeto de negócios Pedido** . Você precisaria reconstruir e
reimplantar simultaneamente esses serviços. Uma abordagem muito melhor seria implementar a
funcionalidade que provavelmente mudará, como o gerenciamento de pedidos , como um serviço.

#### O TAMANHO DE UM SERVIÇO NÃO É IMPORTANTE

Um problema com o termo microsserviço é que a primeira coisa que você ouve é micro. Isso sugere que
um serviço deve ser muito pequeno. Isso também vale para outros termos baseados em tamanho, como
minisserviço ou nanoserviço. Na realidade, o tamanho não é uma métrica útil.

Em teoria, uma equipe só pode ser responsável por um único serviço, de modo que o
serviço não é micro. Por outro lado, se um serviço requer uma equipe grande ou leva muito tempo para ser
testado, provavelmente faz sentido dividir a equipe e o serviço. Ou se você precisar alterar constantemente
um serviço devido a alterações em outros serviços ou se estiver provocando alterações em outros serviços,
isso é um sinal de que não está frouxamente acoplado. Você pode até ter construído um monólito distribuído.

Para desenvolver uma
arquitetura de microsserviço para seu aplicativo, você precisa identificar os serviços e determinar como
eles colaboram.

#### Definindo a arquitetura de microsserviço de um aplicativo

Como devemos definir uma arquitetura de microsserviço? Como em qualquer esforço de
desenvolvimento de software, os pontos de partida são os requisitos escritos,
esperançosamente especialistas de domínio e talvez um aplicativo existente.

![Exemplo Domínios](./exemplo_diagrama_criar_arquitetura.png)

Um aplicativo existe para lidar com solicitações, portanto, a primeira etapa na definição de sua
arquitetura é destilar os requisitos do aplicativo nas principais solicitações. Mas, em vez de
descrever as solicitações em termos de tecnologias IPC específicas, como REST ou mensagens, eu uso 
a noção mais abstrata de operação do sistema. Uma operação do sistema é uma abstração de uma
solicitação que o aplicativo deve manipular. É um comando que atualiza dados ou uma consulta que
recupera dados. O comportamento de cada comando é definido em termos de um modelo de domínio
abstrato, que também é derivado dos requisitos. As operações do sistema tornam-se os cenários
arquitetônicos que ilustram como os serviços colaboram.

A segunda etapa do processo é determinar a decomposição em serviços.
Existem várias estratégias para escolher. Uma estratégia, que tem suas origens na disciplina de
arquitetura de negócios, é definir serviços correspondentes às capacidades de negócios. Outra
estratégia é organizar os serviços em torno de subdomínios de design orientados por domínio. O
resultado final são serviços organizados em torno de conceitos de negócios, em vez de conceitos
técnicos.

A terceira etapa na definição da arquitetura do aplicativo é determinar a API de cada serviço.
Para fazer isso, você atribui cada operação do sistema identificada na primeira etapa a um serviço.
Um serviço pode implementar uma operação inteiramente por si só. Como alternativa, pode ser
necessário colaborar com outros serviços. Nesse caso, você determina como os serviços colaboram,
o que geralmente requer serviços para dar suporte a operações adicionais.
Você também precisará decidir qual dos mecanismos IPC que descrevo no capítulo 3 implementará
a API de cada serviço.
Existem vários obstáculos à decomposição. A primeira é a latência da rede. Você pode descobrir
que uma decomposição específica seria impraticável devido a muitas viagens de ida e volta entre os
serviços. Outro obstáculo à decomposição é que a comunicação síncrona entre os serviços reduz a
disponibilidade. Você pode precisar usar o conceito de serviços independentes, descrito no capítulo
3. 
O terceiro obstáculo é o requisito para manter a consistência dos dados entre os serviços.
   Normalmente, você precisará usar sagas, discutidas no capítulo 4. O quarto e último obstáculo à
   decomposição são as chamadas classes divinas, que são usadas em todo o aplicativo. Felizmente,
   você pode usar conceitos de design orientado a domínio para eliminar classes divinas

#### Identificando as operações do sistema

A primeira etapa na definição da arquitetura de um aplicativo é definir as operações do sistema:

![Exemplo Domínios](./modelo_operacao.png)

O modelo de domínio é derivado principalmente dos substantivos das histórias do usuário, e as
operações do sistema são derivadas principalmente dos verbos. Você também pode definir o
modelo de domínio usando uma técnica chamada Event Storming

#### CRIANDO UM MODELO DE DOMÍNIO DE ALTO NÍVEL

A primeira etapa no processo de definição das operações do sistema é esboçar um modelo de
domínio de alto nível para o aplicativo. Observe que esse modelo de domínio é muito mais
simples do que o que será implementado no final, **cada serviço tem seu próprio modelo de
domínio.**

Apesar de ser uma simplificação drástica, um modelo de domínio de alto nível é útil
neste estágio porque define o vocabulário para descrever o comportamento das operações do
sistema.

Podemos expandir essa história em vários cenários de usuário,
incluindo este:
Dado um consumidor
E um restaurante
E um endereço/horário de entrega que pode ser atendido por esse restaurante
E um total de pedido que atenda ao pedido mínimo do restaurante
Quando o consumidor faz um pedido para o restaurante
Em seguida, o cartão de crédito do consumidor é autorizado
E um pedido é criado no estado PENDING_ACCEPTANCE
E o pedido está associado ao consumidor
E o pedido está associado ao restaurante


Os substantivos neste cenário de usuário sugerem a existência de várias classes,
incluindo Consumer, Order, Restaurant e CreditCard.

Da mesma forma, a história Aceitar pedido pode ser expandida para um cenário como este:
Dado um pedido que está no estado PENDING_ACCEPTANCE e um mensageiro disponível para
entregar o pedido: 

Quando um restaurante aceita um pedido com a promessa de preparar por um determinado Tempo;

* Em seguida, o estado do pedido é alterado para ACEITO
* E o promiseByTime do pedido é atualizado para o horário prometido
* E o correio é designado para entregar o pedido

O resultado final após algumas iterações de análise será um modelo de domínio que consiste, sem surpresa, nessas classes e outras, como MenuItem e Address

![Exemplo Domínios](./diagrama_dominio_exemplo.png)


As responsabilidades de cada classe são as seguintes:
* Consumidor: Um consumidor que faz pedidos.
* Pedido: 
Um pedido feito por um consumidor. Ele descreve o pedido e rastreia seu status.
OrderLineItem—
* Um item de linha de um pedido. 
DeliveryInfo— 
* A hora e o local para entregar um pedido. 
Restaurante:
* Restaurante que prepara pedidos para entrega aos consumidores. 
* MenuItem—Um item no menu do restaurante.
* Courier—Um mensageiro que entrega pedidos aos consumidores. Ele rastreia a disponibilidade do
* correio e sua localização atual.
* Endereço—O endereço de um consumidor ou restaurante.
* Location—A latitude e longitude de um entregador.

#### DEFININDO AS OPERAÇÕES DO SISTEMA

Depois de definir um modelo de domínio de alto nível, a próxima etapa é identificar as solicitações que o
aplicativo deve manipular. Mas você pode imaginar que, em cada cenário de usuário, a IU fará solicitações à lógica de negócios de back-end para recuperar e atualizar dados.

Um bom ponto de partida para identificar os comandos do sistema é analisar os verbos nas histórias
e cenários do usuário. Considere, por exemplo, a história do Place Order . Sugere claramente que o
sistema deve fornecer uma operação Create Order . Muitas outras histórias são mapeadas individualmente
diretamente para os comandos do sistema.

![Exemplo Domínios](./tabela_de_acoes.png)

Um comando tem uma especificação que define seus parâmetros, valor de retorno e
comportamento em termos das classes de modelo de domínio. A especificação de comportamento
consiste em pré-condições que devem ser verdadeiras quando a operação é invocada e pós-
condições que são verdadeiras depois que a operação é invocada. Aqui, por exemplo, está a
especificação da operação do sistema createOrder()

![Exemplo Domínios](./tabela_create_order.png)

As pré-condições espelham as fornecidas no cenário do usuário Colocar pedido descrito
anteriormente. As pós-condições espelham os então do cenário. Quando uma operação do
sistema é invocada, ela verifica as pré-condições e executa as ações necessárias para tornar as
pós-condições verdadeiras.
Aqui está a especificação da operação do sistema acceptOrder():