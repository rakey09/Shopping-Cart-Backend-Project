package com.rakey.EazyShop.service.product;

import com.rakey.EazyShop.dto.ProductDto;
import com.rakey.EazyShop.model.Product;
import com.rakey.EazyShop.request.AddProductRequest;
import com.rakey.EazyShop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String category,String name);
    Long countProductsByBrandAndName(String brand,String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto concertToDto(Product product);
}
