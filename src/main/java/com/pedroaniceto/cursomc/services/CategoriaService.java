
package com.pedroaniceto.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.pedroaniceto.cursomc.dto.CategoriaDTO;
import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pedroaniceto.cursomc.domain.Categoria;
import com.pedroaniceto.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) throws ObjectNotFoundException {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Objeto n?o encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) throws ObjectNotFoundException {
        Categoria newObj = find(obj.getId());
        udateData(newObj, obj);
        try {
            find(obj.getId());
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        try {
            find(id);
            repo.deleteById(id);
        } catch (DataIntegrityViolationException | ObjectNotFoundException e) {
            throw new DataIntegrityViolationException("N?o ? poss?vel excluir uma categoria que possui produtos");
        }
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = new PageRequest(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO objDto) {
        return new Categoria(objDto.getId(), objDto.getNome());
    }

    public void udateData(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }

}