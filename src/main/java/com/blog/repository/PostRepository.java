package com.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blog.entity.Category;
import com.blog.entity.Post;
import com.blog.entity.User;

public interface PostRepository extends JpaRepository<Post, Integer>{

//	if u want to get all post by user then by using findBy Method u can get as written below
	
	List<Post> findByUser(User user);
	
//	if u want to get all post by category then by using findBy Method u can get as written below
	
	List<Post> findByCategory(Category category);
	
//	get data by using containing for ex like oprtaor
	
	List<Post> findByTitleContaining(String keyword);
	
//	get data from title by using @Query method 
	
	@Query("select p from Post p where p.title like :key ") // here between : and key u can't put space
	List<Post> getPostByTitleUsingQueryMethod(@Param("key") String title);
}
