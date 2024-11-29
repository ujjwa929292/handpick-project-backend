package com.handpick.thehandpickapp.service;

import java.util.List;
import java.util.UUID;

import com.handpick.thehandpickapp.models.BlogCreateDTO;
import com.handpick.thehandpickapp.models.BlogDTO;

public interface BlogService {
    BlogDTO createBlog(BlogCreateDTO BlogDTO);
    BlogDTO updateBlog(UUID id, BlogCreateDTO BlogDTO);
    void deleteBlog(UUID id);
    BlogDTO getBlogById(UUID id);
    List<BlogDTO> getAllBlogs();
   
}
