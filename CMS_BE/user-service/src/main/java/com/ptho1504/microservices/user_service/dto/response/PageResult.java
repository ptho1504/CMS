package com.ptho1504.microservices.user_service.dto.response;

import java.util.Collection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageResult<T> {

    private Collection<T> content;
    private Integer totalPages;
    private long totalElements;
    private Integer size;
    private Integer page;
    private boolean empty;

    public PageResult(Collection<T> content, Integer totalPages, long totalElements, Integer size, Integer page,
            boolean empty) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.page = page + 1;
        this.empty = empty;
    }
}