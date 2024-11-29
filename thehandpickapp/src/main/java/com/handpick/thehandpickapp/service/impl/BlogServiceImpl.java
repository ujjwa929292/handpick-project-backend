package com.handpick.thehandpickapp.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.handpick.thehandpickapp.accessors.BlogRepository;
import com.handpick.thehandpickapp.accessors.models.Blog;
import com.handpick.thehandpickapp.models.BlogCreateDTO;
import com.handpick.thehandpickapp.models.BlogDTO;
import com.handpick.thehandpickapp.service.BlogService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BlogDTO createBlog(BlogCreateDTO BlogDTO) {
        Blog Blog = modelMapper.map(BlogDTO, Blog.class);
        return modelMapper.map(Blog, BlogDTO.class);
    }

    @Override
    public BlogDTO updateBlog(UUID id, BlogCreateDTO BlogDTO) {
        if (!blogRepository.existsById(id)) {
            throw new IllegalArgumentException("Blog not found");
        }
        Blog Blog = modelMapper.map(BlogDTO, Blog.class);
        blogRepository.save(Blog);

        return modelMapper.map(Blog, BlogDTO.class);
    }

    @Override
    public void deleteBlog(UUID id) {
        blogRepository.deleteById(id);
    }

    @Override
    public BlogDTO getBlogById(UUID id) {
        Blog blog = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Blog not found"));
        return modelMapper.map(blog, BlogDTO.class);
    }

    @Override
    public List<BlogDTO> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream().map(blog -> modelMapper.map(blog, BlogDTO.class)).collect(Collectors.toList());
    }
}
