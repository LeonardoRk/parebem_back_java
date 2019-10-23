# parebem_back_java
Api do backend para aplicação de controle de usuários e eventos

# Instalação
Assume-se que tem um usuário chamado 'root' com a senha "" no host 'localhost'. Para alterar basta editar no arquivo 'persistence.xml' na pasta 'META-INF'.

Para subir a API do back em Java é necessário ter o maven instalado no computador para poder instalar as dependências do Jersey, JaxRS, Mysql, hibernate e rodar a main do projeto.  
  
    
    
O comando que inicia a aplicação na porta 8080 é o seguinte:
> mvn exec:java -D"exec.mainClass"="br.com.parebem.reservation.Server"
