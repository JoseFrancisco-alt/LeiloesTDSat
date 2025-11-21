import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    // CADASTRAR PRODUTO
    public void cadastrarProduto(ProdutosDTO produto) {
      String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";

    try {
        conn = new conectaDAO().connectDB();
        prep = conn.prepareStatement(sql);

        prep.setString(1, produto.getNome());
        prep.setInt(2, produto.getValor()); // <-- INT, não Double
        prep.setString(3, produto.getStatus());

        prep.executeUpdate();
        prep.close();

        JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + e.getMessage());
    }
    }

    // LISTAR SOMENTE PRODUTOS VENDIDOS
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {

        String sql = "SELECT * FROM produtos WHERE status = 'Vendido'";
        Connection conn = new conectaDAO().connectDB(); // CORRIGIDO
        ArrayList<ProdutosDTO> lista = new ArrayList<>();

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                ProdutosDTO obj = new ProdutosDTO();
                obj.setId(rs.getInt("id"));
                obj.setNome(rs.getString("nome"));
                obj.setValor(rs.getInt("valor"));
                obj.setStatus(rs.getString("status"));

                lista.add(obj);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar vendidos: " + e.getMessage());
        }
        return lista;
    }
        public ArrayList<ProdutosDTO> listarProdutosAVenda() {

    String sql = "SELECT * FROM produtos WHERE status = 'A venda'";
    Connection conn = new conectaDAO().connectDB();
    ArrayList<ProdutosDTO> lista = new ArrayList<>();

    try {
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();

        while (rs.next()) {
            ProdutosDTO obj = new ProdutosDTO();
            obj.setId(rs.getInt("id"));
            obj.setNome(rs.getString("nome"));
            obj.setValor(rs.getInt("valor"));
            obj.setStatus(rs.getString("status"));

            lista.add(obj);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erro ao listar A venda: " + e.getMessage());
    }

    return lista;
    }

    // VENDER PRODUTO (ATUALIZAR STATUS)
    public void venderProduto(int idProduto) {
        String sql = "UPDATE produtos SET status = 'Vendido' WHERE id = ?";
        Connection conn = new conectaDAO().connectDB(); // CORRIGIDO

        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, idProduto);

            pstm.executeUpdate();
            pstm.close();

            JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produto: " + e.getMessage());
        }
    }
}
