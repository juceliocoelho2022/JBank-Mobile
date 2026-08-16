package com.farmacia.ui;

import com.farmacia.model.FormaPagamento;
import com.farmacia.model.ItemVenda;
import com.farmacia.model.Medicamento;
import com.farmacia.model.Venda;
import com.farmacia.service.EstoqueService;
import com.farmacia.service.VendaService;
import com.farmacia.ui.tabela.ItemVendaTableModel;
import com.farmacia.util.Moeda;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.util.List;

/**
 * Ponto de venda (PDV): busca produtos, monta o carrinho e finaliza a venda,
 * dando baixa no estoque.
 */
public class PainelPdv extends JPanel {

    private final EstoqueService estoqueService;
    private final VendaService vendaService;
    private final Runnable aoConcluirVenda;

    private final DefaultListModel<Medicamento> modeloResultados =
            new DefaultListModel<>();
    private final JList<Medicamento> listaResultados =
            new JList<>(modeloResultados);
    private final JTextField campoBusca = new JTextField();
    private final JSpinner spinnerQtd =
            new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));

    private final ItemVendaTableModel carrinho = new ItemVendaTableModel();
    private final JTable tabelaCarrinho = new JTable(carrinho);
    private final JTextField campoDesconto = new JTextField("0,00", 8);
    private final JComboBox<FormaPagamento> comboPagamento =
            new JComboBox<>(FormaPagamento.values());
    private final JLabel labelTotal = new JLabel();

    public PainelPdv(EstoqueService estoqueService,
                     VendaService vendaService,
                     Runnable aoConcluirVenda) {
        this.estoqueService = estoqueService;
        this.vendaService = vendaService;
        this.aoConcluirVenda = aoConcluirVenda;

        setLayout(new BorderLayout(16, 12));
        setBackground(Estilo.FUNDO);
        setBorder(Estilo.espacamento(20));

        add(Estilo.titulo("PDV - Ponto de Venda"), BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridLayout(1, 2, 16, 0));
        centro.setBackground(Estilo.FUNDO);
        centro.add(construirBuscaProdutos());
        centro.add(construirCarrinho());
        add(centro, BorderLayout.CENTER);

        atualizarResultados();
        atualizarTotal();
    }

    private JPanel construirBuscaProdutos() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        Estilo.comoCartao(painel);

        JLabel titulo = new JLabel("Produtos");
        titulo.setFont(Estilo.FONTE_SUBTITULO);

        campoBusca.setFont(Estilo.FONTE_NORMAL);
        campoBusca.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilo.CINZA_CLARO, 1, true),
                Estilo.espacamento(8)));
        campoBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarResultados();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarResultados();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizarResultados();
            }
        });

        JPanel cabecalho = new JPanel(new BorderLayout(0, 8));
        cabecalho.setBackground(Estilo.BRANCO);
        cabecalho.add(titulo, BorderLayout.NORTH);
        cabecalho.add(campoBusca, BorderLayout.SOUTH);

        listaResultados.setFont(Estilo.FONTE_NORMAL);
        listaResultados.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        listaResultados.setFixedCellHeight(30);
        listaResultados.setCellRenderer(new RenderizadorProduto());
        JScrollPane scroll = new JScrollPane(listaResultados);
        scroll.setBorder(BorderFactory.createLineBorder(
                Estilo.CINZA_CLARO, 1, true));

        JButton adicionar = Estilo.botaoPrimario("Adicionar ao carrinho");
        adicionar.addActionListener(e -> adicionarAoCarrinho());

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        rodape.setBackground(Estilo.BRANCO);
        JLabel lblQtd = new JLabel("Qtd:");
        lblQtd.setFont(Estilo.FONTE_NORMAL);
        spinnerQtd.setPreferredSize(new Dimension(70, 32));
        rodape.add(lblQtd);
        rodape.add(spinnerQtd);
        rodape.add(adicionar);

        painel.add(cabecalho, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(rodape, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel construirCarrinho() {
        JPanel painel = new JPanel(new BorderLayout(0, 10));
        Estilo.comoCartao(painel);

        JLabel titulo = new JLabel("Carrinho");
        titulo.setFont(Estilo.FONTE_SUBTITULO);

        tabelaCarrinho.setFont(Estilo.FONTE_NORMAL);
        tabelaCarrinho.setRowHeight(26);
        tabelaCarrinho.getTableHeader().setFont(Estilo.FONTE_PEQUENA);
        tabelaCarrinho.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tabelaCarrinho);
        scroll.setBorder(BorderFactory.createLineBorder(
                Estilo.CINZA_CLARO, 1, true));

        painel.add(titulo, BorderLayout.NORTH);
        painel.add(scroll, BorderLayout.CENTER);
        painel.add(construirFinalizacao(), BorderLayout.SOUTH);
        return painel;
    }

    private JPanel construirFinalizacao() {
        JPanel painel = new JPanel(new BorderLayout(0, 8));
        painel.setBackground(Estilo.BRANCO);

        JButton remover = Estilo.botaoSecundario("Remover item");
        remover.addActionListener(e -> removerItem());

        JPanel linhaConfig = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        linhaConfig.setBackground(Estilo.BRANCO);
        JLabel lblPag = new JLabel("Pagamento:");
        lblPag.setFont(Estilo.FONTE_NORMAL);
        comboPagamento.setFont(Estilo.FONTE_NORMAL);
        JLabel lblDesc = new JLabel("Desconto R$:");
        lblDesc.setFont(Estilo.FONTE_NORMAL);
        campoDesconto.setFont(Estilo.FONTE_NORMAL);
        campoDesconto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                atualizarTotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                atualizarTotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                atualizarTotal();
            }
        });
        linhaConfig.add(lblPag);
        linhaConfig.add(comboPagamento);
        linhaConfig.add(lblDesc);
        linhaConfig.add(campoDesconto);
        linhaConfig.add(remover);

        labelTotal.setFont(Estilo.FONTE_TITULO);
        labelTotal.setForeground(Estilo.VERDE_ESCURO);

        JButton finalizar = Estilo.botaoPrimario("Finalizar venda");
        finalizar.setFont(Estilo.FONTE_SUBTITULO);
        finalizar.addActionListener(e -> finalizarVenda());

        JButton cancelar = Estilo.botaoPerigo("Cancelar");
        cancelar.addActionListener(e -> limparCarrinho());

        JPanel linhaTotal = new JPanel(new BorderLayout());
        linhaTotal.setBackground(Estilo.BRANCO);
        linhaTotal.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        linhaTotal.add(labelTotal, BorderLayout.WEST);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.setBackground(Estilo.BRANCO);
        botoes.add(cancelar);
        botoes.add(finalizar);
        linhaTotal.add(botoes, BorderLayout.EAST);

        painel.add(linhaConfig, BorderLayout.NORTH);
        painel.add(linhaTotal, BorderLayout.SOUTH);
        return painel;
    }

    private void atualizarResultados() {
        List<Medicamento> encontrados =
                estoqueService.buscar(campoBusca.getText());
        modeloResultados.clear();
        for (Medicamento m : encontrados) {
            modeloResultados.addElement(m);
        }
    }

    private void adicionarAoCarrinho() {
        Medicamento m = listaResultados.getSelectedValue();
        if (m == null) {
            JOptionPane.showMessageDialog(this,
                    "Selecione um produto na lista.",
                    "Nenhum produto selecionado",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int qtd = (Integer) spinnerQtd.getValue();
        int jaNoCarrinho = quantidadeNoCarrinho(m.getId());
        if (jaNoCarrinho + qtd > m.getQuantidadeEstoque()) {
            JOptionPane.showMessageDialog(this,
                    "Estoque insuficiente para '" + m.getNome()
                            + "'.\nDisponivel: " + m.getQuantidadeEstoque()
                            + " | Ja no carrinho: " + jaNoCarrinho,
                    "Estoque insuficiente",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        carrinho.adicionar(new ItemVenda(
                m.getId(), m.getNome(), m.getPrecoVenda(), qtd));
        atualizarTotal();
    }

    private int quantidadeNoCarrinho(long medicamentoId) {
        int total = 0;
        for (ItemVenda item : carrinho.getItens()) {
            if (item.getMedicamentoId() == medicamentoId) {
                total += item.getQuantidade();
            }
        }
        return total;
    }

    private void removerItem() {
        int linha = tabelaCarrinho.getSelectedRow();
        if (linha < 0) {
            return;
        }
        carrinho.remover(linha);
        atualizarTotal();
    }

    private void limparCarrinho() {
        carrinho.limpar();
        campoDesconto.setText("0,00");
        atualizarTotal();
    }

    private BigDecimal lerDesconto() {
        try {
            return Moeda.converter(campoDesconto.getText());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    private void atualizarTotal() {
        BigDecimal subtotal = carrinho.getTotal();
        BigDecimal total = subtotal.subtract(lerDesconto())
                .max(BigDecimal.ZERO);
        labelTotal.setText("Total: " + Moeda.formatar(total));
    }

    private void finalizarVenda() {
        if (carrinho.isVazio()) {
            JOptionPane.showMessageDialog(this,
                    "Adicione ao menos um produto ao carrinho.",
                    "Carrinho vazio",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        try {
            Venda venda = new Venda();
            venda.setFormaPagamento(
                    (FormaPagamento) comboPagamento.getSelectedItem());
            venda.setDesconto(lerDesconto());
            for (ItemVenda item : carrinho.getItens()) {
                venda.adicionarItem(item);
            }

            Venda registrada = vendaService.registrarVenda(venda);

            JOptionPane.showMessageDialog(this,
                    "Venda #" + registrada.getId() + " concluida!\n"
                            + "Total: " + Moeda.formatar(registrada.getTotal())
                            + "\nPagamento: "
                            + registrada.getFormaPagamento().getRotulo(),
                    "Venda finalizada",
                    JOptionPane.INFORMATION_MESSAGE);

            limparCarrinho();
            atualizarResultados();
            if (aoConcluirVenda != null) {
                aoConcluirVenda.run();
            }
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Nao foi possivel concluir a venda",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Desenha cada produto da lista com nome, preco e estoque. */
    private static class RenderizadorProduto
            extends javax.swing.DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(
                JList<?> lista, Object valor, int indice,
                boolean selecionado, boolean foco) {
            super.getListCellRendererComponent(
                    lista, valor, indice, selecionado, foco);
            if (valor instanceof Medicamento m) {
                setText(m.getNome() + "   " + Moeda.formatar(m.getPrecoVenda())
                        + "   (estoque: " + m.getQuantidadeEstoque() + ")");
                if (!selecionado && m.getQuantidadeEstoque() <= 0) {
                    setForeground(Estilo.VERMELHO);
                }
            }
            setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            return this;
        }
    }
}
