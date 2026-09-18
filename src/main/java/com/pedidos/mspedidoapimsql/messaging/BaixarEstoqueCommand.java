package com.pedidos.mspedidoapimsql.messaging;

import java.io.Serializable;

public class BaixarEstoqueCommand  {

    private Long pedidoId;
    private Long itemId;
    private Long produtoId;
    private Integer quantidade;

    public BaixarEstoqueCommand() {
    }

    public BaixarEstoqueCommand(Long pedidoId, Long itemId,
                                Long produtoId, Integer quantidade) {
        this.pedidoId = pedidoId;
        this.itemId = itemId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(Long pedidoId) {
        this.pedidoId = pedidoId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
