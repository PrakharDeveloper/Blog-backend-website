package com.whtsonyourmind.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whtsonyourmind.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
