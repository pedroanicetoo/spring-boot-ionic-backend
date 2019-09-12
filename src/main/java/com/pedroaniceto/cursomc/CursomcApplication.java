package com.pedroaniceto.cursomc;

import com.pedroaniceto.cursomc.domain.*;
import com.pedroaniceto.cursomc.domain.enums.EstadoPagamento;
import com.pedroaniceto.cursomc.domain.enums.TipoCliente;
import com.pedroaniceto.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}