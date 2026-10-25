package school.sptech;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntriesFilter;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Set;

public class ConexaoBd {
    private DataSource conexao;

    public ConexaoBd() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        Dotenv dotenv = Dotenv.load();

        // Informa onde estão as classes que vão informar pro jdbc
        // como ele se conecta com o banco de dados

        String user = dotenv.get("USER_DB");
        String password = dotenv.get("PASSWORD_BD");
        String url = "jdbc:mysql://" + dotenv.get("IP_INSTANCE") + ":3306/mind";

        driver.setUsername(user);
        driver.setPassword(password);
        driver.setUrl(url);
        driver.setDriverClassName("com.mysql.cj.jdbc.Driver");

        //Conecta ao banco de dados
        this.conexao = driver;

        System.out.println("conexão com o banco criada");
    }

    public DataSource getConexao(){
        return this.conexao;
    }


}
