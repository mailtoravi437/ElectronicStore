package org.electronic.store.ecommercestore.helper;

import org.electronic.store.ecommercestore.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Helper {
    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type){
        List<U> entity = page.getContent();
        List<V> dtoList = entity.stream().map(object-> new ModelMapper().map(object,type)).collect(Collectors.toList());
        PageableResponse<V> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(dtoList);
        pageableResponse.setPageNumber(page.getPageable().getPageNumber());
        pageableResponse.setPageSize(page.getPageable().getPageSize());
        pageableResponse.setTotalElements(page.getTotalElements());
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setLastPage(page.isLast());
        return pageableResponse;
    }
}