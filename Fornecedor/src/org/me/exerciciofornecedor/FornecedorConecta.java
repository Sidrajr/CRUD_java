package org.me.exerciciofornecedor;

import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FornecedorConecta {
    private static final String URL = "jdbc:derby://localhost:1527/FORNECEDORES";
    private static final String USERNAME = "FORNECEDORES";
    private static final String PASSWORD = "fornecedores";
    
    private Connection connection = null;
    private PreparedStatement insertFornecedor = null;
    private PreparedStatement selecionaFornecedor = null;
    private PreparedStatement alteraFornecedor = null;
    private PreparedStatement excluiFornecedor = null;
    public FornecedorConecta () throws ClassNotFoundException{
    try
    {
    Class.forName("org.apache.derby.jdbc.ClientDriver");
    connection = DriverManager.getConnection (URL, USERNAME, PASSWORD);
    insertFornecedor = connection.prepareStatement("INSERT INTO FORNECEDORES.TABFORNECEDOR " + "(NOME, CNPJ, EMAIL, ENDERECO, CIDADE, ESTADO, TELEFONE)" + "VALUES (?,?,?,?,?,?,?)");
    selecionaFornecedor = connection.prepareStatement("SELECT * FROM FORNECEDORES.TABFORNECEDOR WHERE NOME = ?");
    alteraFornecedor = connection.prepareStatement("UPDATE FORNECEDORES.TABFORNECEDOR SET NOME = ?, CNPJ = ?, EMAIL = ?, ENDERECO = ?, CIDADE = ?, ESTADO = ?, TELEFONE = ?" + "WHERE ID = ?");
    excluiFornecedor = connection.prepareStatement("DELETE FROM FORNECEDORES.TABFORNECEDOR " + "WHERE ID = ?");
    }
    catch (SQLException sqlException)
    {
    sqlException.printStackTrace();
    System.exit(1);
    }
    }
    public int adicionaFornecedor (String fnome, String fcnpj, String femail, String fendereco, String fcidade, String festado, String ftelefone)
    {
        int result = 0;
        try
        {
            insertFornecedor.setString(1, fnome);
            insertFornecedor.setString(2, fcnpj);
            insertFornecedor.setString(3, femail);
            insertFornecedor.setString(4, fendereco);
            insertFornecedor.setString(5, fcidade);
            insertFornecedor.setString(6, festado);
            insertFornecedor.setString(7, ftelefone);
            
            result = insertFornecedor.executeUpdate();
        }
        catch (SQLException sqlException)
        {
         sqlException.printStackTrace();
         close();
        }
         return result;
        }
    
    public List <Fornecedor>getNome(String nome)
    {
        List <Fornecedor>resultados = null;
        ResultSet resultSet = null;
        try {
            selecionaFornecedor.setString(1, nome);
            resultSet = selecionaFornecedor.executeQuery();
            resultados = new ArrayList<Fornecedor>();
            while (resultSet.next()) {
                resultados.add(new Fornecedor(
                        resultSet.getInt("ID"),
                        resultSet.getString("Nome"),
                        resultSet.getString("CNPJ"),
                        resultSet.getString("Email"),
                        resultSet.getString("Endereco"),
                        resultSet.getString("Cidade"),
                        resultSet.getString("Estado"),
                        resultSet.getString("Telefone")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            close();
        }
        return resultados;
    }
    
    public int atualizaFornecedor(String fnome, String fcnpj, String femail, String fendereco, String fcidade, String festado, String ftelefone, int fcodigo) {
        int result = 0;
        try {
            alteraFornecedor.setString(1, fnome);
            alteraFornecedor.setString(2, fcnpj);
            alteraFornecedor.setString(3, femail);
            alteraFornecedor.setString(4, fendereco);
            alteraFornecedor.setString(5, fcidade);
            alteraFornecedor.setString(6, festado);
            alteraFornecedor.setString(7, ftelefone);
            alteraFornecedor.setInt(8, fcodigo);
            result = alteraFornecedor.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            close();
        }
        return result;
    }
    
    public boolean deletaFornecedor(int id) {
        boolean exclui = false;
        try {
            excluiFornecedor.clearParameters();
            excluiFornecedor.setInt(1, id);
            excluiFornecedor.executeUpdate();
            exclui = true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return exclui;
    }
    
    public void close (){
        try
        {
        connection.close();
        }
        catch (SQLException sqlException)
       {
        sqlException.printStackTrace();
       }
    
    }
}
