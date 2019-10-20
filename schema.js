var mysql = require('mysql');

const DATABASE = "controle"

var con = mysql.createConnection({
  host: "localhost",
  user: "root",
  password: ""
});

function finalizaConexao(){
  console.log("finalizando conexão de criação de schema");
  con.end();
  console.log("conexão finalizada");
}

function chamaColega(){
  alert("clicado no colega")
}

function usaBancoDeDados(){
  query_usa_tabelas = "use "+ DATABASE +";";
  con.query(query_usa_tabelas, function(err, result){
    if(err) throw err;
      console.log("Result para usar database no schema");
  })
}

function criaTabelaEventos(){
  query_cria_tabela_eventos = "create table if not exists evento(id_evento INT NOT NULL AUTO_INCREMENT primary key, local VARCHAR(50) not null, limite_convidados INT(5) not null, momento DATETIME not null, vencido boolean not null default 0);"
  con.query(query_cria_tabela_eventos, function(err, result){
    if(err){
      console.log('erro para criar tabela de eventos');
      throw err;
    }else{
      console.log("Criação tabela eventos");
    }

  });
}

function criaTabelaUsuarios(){
  query_cria_tabela_usuarios = "create table if not exists usuario(id_usuario INT NOT NULL AUTO_INCREMENT primary key, nome VARCHAR(50) UNIQUE not null, telefone VARCHAR(25) not null, senha VARCHAR(25) not null);"
  con.query(query_cria_tabela_usuarios, function(err, result){
    if(err){
      console.log('erro para criar tabela de usuário'); 
      throw err;
    }else{
      console.log("Result de criação tabela usuários");
    }
      
  });
}

function listarTudoTabela(nomeTabela){
  query_lista_usuarios = "select * from" +  nomeTabela + ""
}

function criaTabelaUsuarioEvento(){
  query_tabela_usuario_evento = 'create table if not exists usuario_evento (id_evento int NOT NULL, id_usuario int NOT NULL , foreign key (id_usuario) references usuario(id_usuario), foreign key (id_evento) references evento(id_evento));';
  con.query(query_tabela_usuario_evento, function(err, result){
    if(err){
	  throw err;
	}
    else{
      console.log("Tabela usuario evento com chaves estrangeiras criadas");
	  console.log(result);
    }

  });
}

con.connect(function(err) {
  if (err) throw err;
  console.log("Connected!");
  con.query("drop database if exists " + DATABASE + ";", function(err,result){
    if(err) throw err;
      console.log("schema dropado.. pronto pra iniciar");
      mostra_databases = "show databases;"                                     
      comando_cria_database = "CREATE DATABASE IF NOT EXISTS " + DATABASE;
      con.query(comando_cria_database, function (err, result) {
        if (err) throw err;
          console.log("criação do banco de dados: " + DATABASE);
          usaBancoDeDados();
          criaTabelaUsuarios();
          criaTabelaEventos();
		  criaTabelaUsuarioEvento();
          finalizaConexao();
       });
    
  }); 
});

