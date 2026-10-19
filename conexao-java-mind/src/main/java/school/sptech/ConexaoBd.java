package school.sptech;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class ConexaoBd {
    private DataSource conexao;

    public ConexaoBd() {
        DriverManagerDataSource driver = new DriverManagerDataSource();

        // Informa onde estão as classes que vão informar pro jdbc
        // como ele se conecta com o banco de dados

        driver.setUsername("aluno");
        driver.setPassword("sptech");
        driver.setUrl("jdbc:mysql://127.0.0.1:3306/mind");
        driver.setDriverClassName("com.mysql.cj.jdbc.Driver");

        //Conecta ao banco de dados
        this.conexao = driver;
    }

    public DataSource getConexao(){
        return this.conexao;
    }


}
