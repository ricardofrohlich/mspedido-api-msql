package com.pedidos.mspedidoapimsql.service;


import com.pedidos.mspedidoapimsql.client.ProdutoClient;
import com.pedidos.mspedidoapimsql.dto.ProdutoDTO;
import com.pedidos.mspedidoapimsql.messaging.BaixarEstoqueCommand;
import com.pedidos.mspedidoapimsql.messaging.EstoquePublisher;
import com.pedidos.mspedidoapimsql.model.ItemPedido;
import com.pedidos.mspedidoapimsql.repository.ItemPedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoService {
    private final ItemPedidoRepository repository;
    private final ProdutoClient produtoClient;
    private final PedidoService pedidoService;
    private final EstoquePublisher estoquePublisher;

    public ItemPedidoService(ItemPedidoRepository repository, ProdutoClient produtoClient, PedidoService pedidoService, EstoquePublisher estoquePublisher) {
        this.repository = repository;
        this.produtoClient = produtoClient;
        this.pedidoService = pedidoService;
        this.estoquePublisher = estoquePublisher;
    }

    public ItemPedido salvar(ItemPedido item){
        ProdutoDTO produto = produtoClient.buscarPorId(item.getProdutoId());
        if(produto == null){
            throw new RuntimeException("Produto "+item.getProdutoId() + " não foi encontrado");
        }
        if(produto.getEstoque() == null || produto.getEstoque() < item.getQuantidade()){
            throw new RuntimeException("Estoque insuficiente para o produto "+produto.getNome());
        }
        item.setSubtotal(produto.getPreco() * item.getQuantidade());
        //aqui agora eu vou chamar o recalculaValor

        produtoClient.baixarEstoque(item.getProdutoId(), item.getQuantidade());
        ItemPedido itemSalvo = repository.save(item);
        //enviando a mensagem pro RabbitMQ solicitando a baixa do estoque
        estoquePublisher.publicarBaixaEstoque(
                new BaixarEstoqueCommand(itemSalvo.getPedido().getId(),
                        itemSalvo.getId(),
                        itemSalvo.getProdutoId(),
                        itemSalvo.getQuantidade()
                )
        );
        pedidoService.recalcularValor(itemSalvo.getPedido().getId());
        return itemSalvo;
    }

    public List<ItemPedido> listar(){
        return repository.findAll();
    }

    public ItemPedido buscarPorId(Long id){
        return repository.findById(id).orElseThrow(() ->  new RuntimeException("Item não encontrado"));
    }

    public void deletar(Long id){
        ItemPedido item = buscarPorId(id); //chego aqui eu so tenho o id do itemPedido
        Long pedidoId = item.getPedido().getId();//com ele eu busco o pedido e com o id do pedido eu chamo
        repository.deleteById(id);
        pedidoService.recalcularValor(pedidoId); //recalcular
    }
}
