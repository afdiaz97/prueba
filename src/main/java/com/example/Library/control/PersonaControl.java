package com.example.Library.control;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.Library.dao.Personadao;
import com.example.Library.Model.Persona;
import com.example.Library.dao.Librodao;
import com.example.Library.Model.Libro;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import java.util.Optional;

@RestController
public class PersonaControl {
    @Autowired
    private Personadao Personadao;
    @Autowired Librodao Librodao;

    @PostMapping("/registration/user")
    public Long save(@RequestParam String name,@RequestParam String password) {
        Persona person = new Persona (name,password,"Cliente");
        Personadao.save(person);
        return person.getId();
    }
    @PostMapping("/login")
    public String login (@RequestParam long id,@RequestParam String password){
        Persona person=Personadao.findByid(id);
        if(person.getPassword().equals(password)){
            return person.getTipoCuenta();
        }
        return "contraseña incorrecta";
    }
    @GetMapping("/libros")
    public  List <Libro> Getallbooks(){
        return Librodao.findAll();
    }
    @PostMapping("/nuevolibro")
    public Long savebook (@RequestParam String name,@RequestParam double cantidad,@RequestParam double Tarifa){
        Libro libro = new Libro(name,cantidad,Tarifa);
        Librodao.save(libro);
        return libro.getId();
    }
    @DeleteMapping("/deletelibro")
    public void deletebook(@RequestParam Long id){
        Libro libro=Librodao.findByid(id);
        Librodao.delete(libro);
        return;
    }
    @PutMapping("/updatebook")
    public void updatebook(@RequestParam(required = true) long id,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = true) double cantidad,
                           @RequestParam(required = false) String tarifa){
        Libro libro=Librodao.findByid(id);
        if (name != null){
            libro.setName(name);
        }
        if (cantidad >= 0) {
            libro.setCantidad(cantidad);
        }
        if(tarifa != null){
            libro.setTarifa(Double.parseDouble(tarifa));
        }
        Librodao.save(libro);
        return;
    }
    @GetMapping("/preciototal")
    public double calcularprecio(@RequestParam String fecha,
                                 @RequestParam double cantidad,
                                 @RequestParam long id) throws ParseException {
        Date fecha1 = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
        Date hoy = new Date();
        Date dt1 = new Date(hoy.getYear(),hoy.getMonth(),hoy.getDate());

        long diff = fecha1.getTime() - dt1.getTime();

        TimeUnit time = TimeUnit.DAYS;
        long dias = time.convert(diff, TimeUnit.MILLISECONDS);
        Libro libro = Librodao.findByid(id);
        double tarifa=libro.getTarifa();
        return (tarifa*dias)*cantidad;
    }
}
