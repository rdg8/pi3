/*
 *         alt+shift+f = //f12
 */
package br.senac.tads.pi3b.agenda;

import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author rodrigo.rsantos17
 */
public class Agenda {

    private Connection obterConexao() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/agendabd", "root", "");
        return conn;
    }

    public List<Pessoa> listar() throws ClassNotFoundException, SQLException {

        List<Pessoa> lista = new ArrayList<Pessoa>();
        try (Connection conn = obterConexao();
                PreparedStatement stmt = conn.prepareStatement(
                        "SELECT id, nome, dtnacimento FROM PESSOA");
                ResultSet resultados = stmt.executeQuery();) {
            while (resultados.next()) {
                long id = resultados.getLong("id");
                String nome = resultados.getString("nome");
                Date dtnacimento = resultados.getDate("dtnacimento");
                Pessoa p =new Pessoa();
                p.setId(id);
                p.setNome(nome);
                p.setDtNascimento(dtnacimento);
                lista.add(p);
                //System.out.println(id + ", " + nome + ", " + dtnacimento);
            }
        } finally {
        }
        return lista;
    }

    public void incluir() throws ClassNotFoundException, SQLException {
        try (Connection conn = obterConexao()){
                conn.setAutoCommit(false);
                
                try(PreparedStatement stmt =
                        conn.prepareStatement(
                        "INSERT INTO PESSOA (nome, dtnacimento) VALUES(?, ?)"),
                        Statement.RETURN_GENERATED_KEYS)) 
                    {
            stmt.setString(1, "maria de souza");
            GregorianCalendar cal = new GregorianCalendar(1992, 10, 5);
            stmt.setDate(2, new java.sql.Date(cal.getTimeInMillis()));

            int status = stmt.executeUpdate();
            //recupera o id gerado peolo BD
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if(generatedKeys.next()){
                long idPessoa = generatedKeys.getLong(1);
                
                try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO CONTATO (tipo, valor, idpessoa) values (?, ?, ?)"))
            }
            
            } 
        }finally {

        }
    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        try {
            agenda.incluir();
            //agenda.listar();
            List<Pessoa> p = new ArrayList<Pessoa>();
            for (int i = 0; i < 10; i++) {
                
            }
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
