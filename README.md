# restapp
Sugestão de desenvolvimento para aplicações Rest utilizando Spring.

O objetivo dessa aplicação é servir de base para o desenvolvimento de muitas outras, mostrando como expor suas APIs de acordo com padrões REST e tudo baseado em Spring.

Como disse acima, a aplicação usa algumas features do Spring que podem ser vistas no pom.xml, pois usa Maven.
O banco de dados configurado é o H2, banco em texto, que fica armazenado na propria aplicação e para mudar basta reconfigurar o datasource do Spring.
Através do comando mvn jetty:run é possível rodar a aplicação no jetty embedded.

# Como acessar?
Para acessar as funções é requerido um "gerador de requisições", como o Postman do Chrome pois algumas chamadas não são possíveis através do browser, que utiliza apenas GET.

Exemplo:
<br/>
http://localhost:8080/restapp/users/1
* Se o endereço for chamado com GET, retornará o resultado de uma consulta;
* Se o endereço for chamado com PUT, o dado com id 1 será atualizado;
* Se o endereço for chamado com DELETE, o dado com id 1 será removido;
