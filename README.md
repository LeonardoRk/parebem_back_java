# parebem_back_java
Api do backend para aplicação de controle de usuários e eventos

# Instalação
Antes iniciar a API é necessário instalar o banco de dados. O banco foi escrito em node e o script se chama schema.js
O arquivo schema.js tem a dependência do pacote mysql que pode ser instalado a partir do comando abaixo.
> npm i mysql

Ao instalar o pacote do mysql e tendo uma instância do mysql rodando no computador crie o banco e instale as tabelas com o único comando a seguir.

> node schema.js

O comando acima criará o banco chamado 'controle' com as tabelas de 'usuario', 'evento' e 'usuario_evento'. Assume-se que tem um usuário chamado 'root' com a senha "" no host 'localhost'. Para alterar basta editar no arquivo 'schema.js'.

Após instalar o banco é hora de subir a API do back em Java. É necessário ter o maven instalado no computador para poder instalar as dependências do Jersey, JaxRS, Mysql e rodar a main do projeto.  
  
    
    
O comando que inicia a aplicação na porta 8080 é o seguinte:
> mvn exec:java -D"exec.mainClass"="br.com.parebem.reservas.Servidor"
