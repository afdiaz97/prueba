package com.example.Library.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Library.Model.Persona;
public interface Personadao extends JpaRepository<Persona, Long> {
  Persona findByid(long id);
}
