package com.example.Library.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Library.Model.Libro;
public interface Librodao extends JpaRepository<Libro, Long> {
    Libro findByid(long id);
}
