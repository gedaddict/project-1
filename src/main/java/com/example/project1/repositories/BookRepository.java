package com.example.project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.project1.models.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer>, BookRepositoryCustom{

}
