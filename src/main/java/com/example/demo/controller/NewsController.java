package com.example.demo.controller;

import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public String showNews(Model model) {
        try {
            List<NewsService.NewsItem> newsList = newsService.fetchNews();
            model.addAttribute("newsList", newsList);
        } catch (Exception e) {
            model.addAttribute("error", "Unable to fetch news");
        }
        return "news";
    }
}
