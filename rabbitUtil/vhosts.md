## RPC

O modelo Remote Procedure Call ( RPC) foi um dos muitos casos de uso nos anos 60, quando a computação distribuída era um desafio ( ainda é).
O modelo RPC é considerado um protocolo de solicitação-resposta , onde você tem um cliente que inicia um processo enviando uma mensagem de solicitação a um servidor remoto para executar uma ou várias tarefas.
Em seguida, o servidor remoto envia uma resposta ao cliente para que ele possa continuar com o processo.

@RabbitListener : Você já está familiarizado com esta anotação.
Ele criará um contêiner de ouvinte de mensagens e atenderá todas as mensagens recebidas da fila
apress.amqp.queue (lembre-se de que esta é uma propriedade especificada no arquivo application.properties ).

O Spring AMQP inclui a anotação @SendTo , pela qual você pode enviar sua resposta para uma troca ou para uma fila.
```java
@RabbitListener(queues="${apress.amqp.queue}")
    @SendTo("${apress.amqp.reply-exchange-queue}")
    public Message<String> replyToProcess(String message){
    }
```

Tentativas : Às vezes, você receberá um erro, seja processando a mensagem ou pelo agente, e precisará tentar novamente no nível do consumidor.
Você precisa usar esse recurso para esse cenário:

```java
@Bean
public StatefulRetryOperationsInterceptor interceptor() {
        return RetryInterceptorBuilder.stateful()
                .maxAttempts(3)
                .backOffOptions(1000, 2.0, 10000)
                .build();
}
```
Ou isto:


```java 
@Bean
RetryOperationsInterceptor interceptor(
        RabbitTemplate template,
@Value("${apress.amqp.error-exchange:}")String errorExchange,
@Value("${apress.amqp.error-routing-key}")    String
                                  errorExchangeRoutingKey) {
        return RetryInterceptorBuilder.stateless()
                        .maxAttempts(3)
                        .recoverer(
                new RepublishMessageRecoverer(template, errorExchange,
                errorExchangeRoutingKey))
                        .build();
}`
```
## Multi host rabbit

Como o rabbit trabalha com virtual hosts(seriam os tenants):

![Tux, the Linux mascot](./vhosts.png)

Idéia principal é a seguinte:

![Tux, the Linux mascot](./tenantVHosts.png)

Ou seja dessa forma cada tenant vai ter seu vhost sem interferir um no outro, para fazer isso
podemos contar com essa [lib](https://github.com/freenowtech/spring-multirabbit)

Para configurar com a lib fica muito simples, precisamos fazer algo como:

```yaml
spring:
    rabbitmq:
        host: localhost
        port: 5672
    multirabbitmq:
        enabled: true
        connections:
            connectionNameA:
                defaultConnection: true
                host: localhost/vHost1
                port: 5672
            connectionNameB:
                host: localhost/vHost2
                port: 5672
```

Traduzimos cada conexão como um tenant com um vHost, assim por diante.

