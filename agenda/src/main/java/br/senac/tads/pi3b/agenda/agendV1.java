/*
 *         alt+shift+f = //f12
 */
package br.senac.tads.pi3b.agenda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author rodrigo.rsantos17
 */
public class agendV1 {

    public void listar() throws ClassNotFoundException, SQLException {

        //declaracao das variaveis de conexao
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultados = null;

        try {
            //1-abrir conexao com o banco
            //declarar drive jdbc de acordo com o banco de dados usado (MYSQL)
            Class.forName("com.mysql.jdbc.Driver");

            //abrir a conexao usando a String de conexao
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/agendabd",
                    "root",
                    "");

            //2-executar as açoes no BD
            stmt = conn.prepareStatement(
                    "SELECT id, nome, dtnacimento FROM PESSOA");

            resultados = stmt.executeQuery();

            while (resultados.next()) {
                long id = resultados.getLong("id");
                String nome = resultados.getString("nome");
                Date dtnacimento = resultados.getDate("dtnacimento");

                System.out.println(id + ", " + nome + ", " + dtnacimento);
            }

        } finally {
            //3-fecha conexa do BD
            if (resultados != null) {
                resultados.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }

    public static void main(String[] args) {
        Agenda agenda = new Agenda();

        try {
            agenda.listar();
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
