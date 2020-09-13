# donus-code-challenge

Para facilitar a execução foi utilizado o banco de dados em memória H2. Ao executar
a aplicação é possível acessar o partal admin pela url:
    `http://localhost:8090/h2-console` `user: sa | password: sa`

Foi feito o commit da collection do postman caso queiram utilizar para fazer requisições
está na pasta `Collection Postman`.

Na pasta `target` está o jar da aplicação, caso queriam rodar pela linha de comando:
`java -jar -Xms512m -Xmx512m [***PATH ONDE FOI CLONADO O PROJETO***]/target/donus-code-challenge-0.0.1-SNAPSHOT.jar --spring.config.location=file:'[***PATH ONDE FOI CLONADO O PROJETO***]/src/main/resources/application.yml' &`

Parâmetros adicionais para o GC, para evitar efeitos 'colaterais', do FullGC após a inicialização da aplicação:
`-Xms512m -Xmx512m -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses -XX:+UseConcMarkSweepGC` 