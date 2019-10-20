package br.com.parebem.reservas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


// Notice, do not import com.mysql.jdbc.*
// or you will have problems!

public class Dao {
	
	public Dao() {
		super();
	}
	
	private static void importaInicializa() {
		System.out.println("Estou em dao");
		try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("Houve exceção");
            System.out.println(ex.getMessage());
        }
	}

	private static Connection instance = null;
	private static Boolean executado = false;
	
    public static Connection getInstance(){
    	if(!executado) {
    		System.out.println("executando");
    		executado = true;
    		importaInicializa();
    	}
        if(instance == null){
        	String url = "jdbc:mysql://localhost:3306/controle";
        	try {
        		instance = DriverManager.getConnection(url, "root", "");
	        }catch (SQLException ex) {
	    	    // handle any errors
	    	    System.out.println("SQLException: " + ex.getMessage());
	    	    System.out.println("SQLState: " + ex.getSQLState());
	    	    System.out.println("VendorError: " + ex.getErrorCode());
	    	}
        }
        return instance;
    }
    
    public static ArrayList<HashMap<String, String>> executaComando(String sql) {
		Connection conn = getInstance();
		
    	ArrayList<HashMap<String, String>> linhas = new ArrayList<HashMap<String, String>>();
    	try {
    	    PreparedStatement stm =  conn.prepareStatement(sql);
    	    ResultSet rs;
    	    try {
    	    	rs = stm.executeQuery(sql);
    	    	ResultSetMetaData metadata = rs.getMetaData();
    	    	int columnCount = metadata.getColumnCount(); 
        	    while(rs.next()) {
        	    	HashMap<String, String> keyValue = new HashMap<String, String>();
        	    	for(int i = 1 ; i <= columnCount; i++) {
        	    		String nomeColuna = metadata.getColumnName(i);
        	    		String atributo = rs.getString(nomeColuna);
        	    		keyValue.put(nomeColuna, atributo);
        	    		//System.out.println(nomeColuna + " " + atributo);    	    		
        	    	}
        	    	int tamanhoLista = linhas.size();
        	    	linhas.add(tamanhoLista, keyValue);
        	    }
        	    fechaConexao(rs, stm, conn);
    	    }catch (SQLException e) {
    	    	stm.execute();
    	    	fechaConexaoInsert(stm, conn);
			}
    	    
    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    if(ex.getSQLState().compareTo("23000") == 0) { // integrity constraint violation
    	    	System.out.println("Erro de violação de constraint");
    	    	HashMap<String, String> hash = new HashMap<String, String>();
    	    	hash.put("erro", "violacao de constraint");
    	    	int tamanhoLista = linhas.size();
    	    	linhas.add(tamanhoLista, hash);
    	    	System.out.println(linhas);
    	    }
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	}
    	
		return linhas;
	}
    
    public static void fechaConexao(ResultSet rs, PreparedStatement stm, Connection conn) {
    	try {
    		rs.close();
     	    stm.close();
    		conn.close();
    		instance = null;
    	}catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	}
    }
    
    public static void fechaConexaoInsert(PreparedStatement stm, Connection conn) {
    	try {
     	    stm.close();
    		conn.close();
    		instance = null;
    	}catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	}
    }
    
}
