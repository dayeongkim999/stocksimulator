package com.example.demo.service;

import com.example.demo.dto.NewsResponse;
import com.example.demo.entity.News;
import com.example.demo.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    // 뉴스 하나만 상세 조회하기
    @Transactional(readOnly = true)
    public NewsResponse getNewsById(Long newsId) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new RuntimeException("해당 뉴스를 찾을 수 없습니다."));

        return convertToDto(news);
    }

    // 전체 뉴스 목록 조회하기
    @Transactional(readOnly = true)
    public List<NewsResponse> getAllNews() {
        return newsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private NewsResponse convertToDto(News news) {
        return NewsResponse.builder()
                .id(news.getId())
                .title(news.getTitle())
                .content(news.getContent())
                .category(news.getCategory())
                .changefactor(news.getChangefactor())
                .build();
    }
}