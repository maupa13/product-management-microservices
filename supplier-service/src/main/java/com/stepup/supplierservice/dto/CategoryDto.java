package com.stepup.supplierservice.dto;

import com.stepup.supplierservice.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private List<Product> products;
}
