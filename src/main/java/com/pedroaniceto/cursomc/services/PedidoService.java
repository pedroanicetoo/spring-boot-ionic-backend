
package com.pedroaniceto.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.pedroaniceto.cursomc.domain.ItemPedido;
import com.pedroaniceto.cursomc.domain.PagamentoComBoleto;
import com.pedroaniceto.cursomc.domain.enums.EstadoPagamento;
import com.pedroaniceto.cursomc.repositories.ItemPedidoRepository;
import com.pedroaniceto.cursomc.repositories.PagamentoRepository;
import com.pedroaniceto.cursomc.repositories.ProdutoRepository;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedroaniceto.cursomc.domain.Pedido;
import com.pedroaniceto.cursomc.repositories.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private  ClienteService clienteService;

    public Pedido find(Integer id) throws ObjectNotFoundException {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert(Pedido obj) throws ObjectNotFoundException {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(clienteService.find(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(produtoService.find(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        System.out.println(obj);
        return obj;
    }
}