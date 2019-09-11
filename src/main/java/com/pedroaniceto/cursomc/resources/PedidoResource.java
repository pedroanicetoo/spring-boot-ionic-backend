package com.pedroaniceto.cursomc.resources;

import com.pedroaniceto.cursomc.domain.Pedido;
import com.pedroaniceto.cursomc.services.PedidoService;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Pedido> find(@PathVariable Integer id) throws ObjectNotFoundException {
        Pedido obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }
    @Transactional
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) throws ObjectNotFoundException {
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}