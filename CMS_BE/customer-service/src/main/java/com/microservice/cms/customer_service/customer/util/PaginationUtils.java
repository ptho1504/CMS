package com.microservice.cms.customer_service.customer.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationUtils {
    public static Pageable getPageable(Integer page, Integer size, Sort.Direction direction, String sortField) {
        return PageRequest.of(page, size, direction, sortField);
    }
}