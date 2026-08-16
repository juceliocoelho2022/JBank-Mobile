package com.farmacia.ui;

import com.farmacia.ContextoAplicacao;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import java.awt.Dimension;

/**
 * Janela principal do sistema, organizada em abas: Inicio, PDV, Estoque e
 * Vendas. Cada aba se atualiza quando uma venda e concluida.
 */
public class JanelaPrincipal extends JFrame {

    private final PainelDashboard painelDashboard;
    private final PainelEstoque painelEstoque;
    private final PainelVendas painelVendas;

    public JanelaPrincipal(ContextoAplicacao contexto) {
        super("Sistema Farmacia");

        this.painelDashboard =
                new PainelDashboard(contexto.getDashboardService());
        this.painelEstoque =
                new PainelEstoque(this, contexto.getEstoqueService());
        this.painelVendas =
                new PainelVendas(contexto.getVendaService());

        // Ao concluir uma venda, atualiza dashboard, estoque e historico.
        PainelPdv painelPdv = new PainelPdv(
                contexto.getEstoqueService(),
                contexto.getVendaService(),
                this::atualizarTudo);

        JTabbedPane abas = new JTabbedPane();
        abas.setFont(Estilo.FONTE_SUBTITULO);
        abas.setBackground(Estilo.FUNDO);
        abas.addTab("  Inicio  ", painelDashboard);
        abas.addTab("  PDV  ", painelPdv);
        abas.addTab("  Estoque  ", painelEstoque);
        abas.addTab("  Vendas  ", painelVendas);
        abas.setBorder(BorderFactory.createEmptyBorder());

        // Reforca a atualizacao ao navegar entre as abas.
        abas.addChangeListener(e -> atualizarTudo());

        setContentPane(abas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 640));
        setSize(1100, 700);
        setLocationRelativeTo(null);
    }

    private void atualizarTudo() {
        painelDashboard.recarregar();
        painelEstoque.recarregar();
        painelVendas.recarregar();
    }

    /** Ajustes globais de aparencia aplicados antes de criar a janela. */
    public static void aplicarTema() {
        try {
            UIManager.setLookAndFeel(
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("control", Estilo.FUNDO);
            UIManager.put("nimbusBase", Estilo.VERDE_ESCURO);
            UIManager.put("nimbusFocus", Estilo.VERDE);
            UIManager.put("nimbusSelectionBackground", Estilo.VERDE);
            UIManager.put("Table.alternateRowColor", Estilo.VERDE_CLARO);
        } catch (Exception e) {
            // Se o Nimbus nao estiver disponivel, mantem o tema padrao.
        }
    }
}
