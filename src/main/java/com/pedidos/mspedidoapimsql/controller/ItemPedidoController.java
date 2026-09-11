package com.pedidos.mspedidoapimsql.controller;

import com.pedidos.mspedidoapimsql.model.ItemPedido;
import com.pedidos.mspedidoapimsql.service.ItemPedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens")
public class ItemPedidoController {
    private final ItemPedidoService service;

    public ItemPedidoController(ItemPedidoService service) {
        this.service = service;
    }

    @PostMapping
    public ItemPedido salvar (@RequestBody ItemPedido item){
        return service.salvar(item);
    }

    @GetMapping
    public List<ItemPedido> listar(){
        return service.listar();
    }

    @GetMapping("/{id}")
    public ItemPedido buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }
}
