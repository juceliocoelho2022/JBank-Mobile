package com.farmacia.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Uma venda concluida no PDV, com seus itens, forma de pagamento e desconto.
 */
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private LocalDateTime dataHora;
    private final List<ItemVenda> itens;
    private FormaPagamento formaPagamento;
    private BigDecimal desconto;

    public Venda() {
        this.dataHora = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.formaPagamento = FormaPagamento.DINHEIRO;
        this.desconto = BigDecimal.ZERO;
    }

    public void adicionarItem(ItemVenda item) {
        itens.add(item);
    }

    /** Soma dos subtotais de todos os itens, antes do desconto. */
    public BigDecimal getSubtotal() {
        BigDecimal soma = BigDecimal.ZERO;
        for (ItemVenda item : itens) {
            soma = soma.add(item.getSubtotal());
        }
        return soma;
    }

    /** Valor final da venda: subtotal menos o desconto (nunca negativo). */
    public BigDecimal getTotal() {
        BigDecimal total = getSubtotal().subtract(
                desconto == null ? BigDecimal.ZERO : desconto);
        return total.max(BigDecimal.ZERO);
    }

    /** Quantidade total de unidades vendidas nesta venda. */
    public int getQuantidadeTotalItens() {
        int total = 0;
        for (ItemVenda item : itens) {
            total += item.getQuantidade();
        }
        return total;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public List<ItemVenda> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }
}
