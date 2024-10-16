package com.rakey.EazyShop.controller;

import com.rakey.EazyShop.dto.ProductDto;
import com.rakey.EazyShop.exceptions.ResourceNotFoundException;
import com.rakey.EazyShop.model.Product;
import com.rakey.EazyShop.request.AddProductRequest;
import com.rakey.EazyShop.request.ProductUpdateRequest;
import com.rakey.EazyShop.response.ApiResponse;
import com.rakey.EazyShop.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productService.getAllProducts();
        List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success",convertedProducts));
    }

    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try {
            Product product = productService.getProductById(productId);
            ProductDto productDto = productService.concertToDto(product);
            return ResponseEntity.ok(new ApiResponse("success",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product theProduct = productService.addProduct(product);
            ProductDto productDto = productService.concertToDto(theProduct);
            return ResponseEntity.ok(new ApiResponse("Add Product Success",productDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), INTERNAL_SERVER_ERROR));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request,@PathVariable Long productId) {
        try {
            Product product = productService.updateProduct(request, productId);
            ProductDto productDto = productService.concertToDto(product);
            return ResponseEntity.ok(new ApiResponse("Updated",productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Deleted",productId));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }
    }
    @GetMapping("/products/by/brand-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brandName, @RequestParam String productName){
        try {
            List<Product>  products = productService.getProductsByBrandAndName(brandName, productName);

            if (products.isEmpty()){
                ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/products/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam  String brand){
        try {
            List<Product>  products = productService.getProductsByCategoryAndBrand(category, brand);
            if (products.isEmpty()){
                ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/products/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name){
        try {
            List<Product>  products = productService.getProductByName(name);
            if (products.isEmpty()){
                ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),INTERNAL_SERVER_ERROR));
        }
    }

    @GetMapping("/products/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brand){
        try {
            List<Product>  products = productService.getProductsByBrand(brand);
            if (products.isEmpty()){
                ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/product/{category}/all/products")
    public ResponseEntity<ApiResponse> findProductByCategory(@PathVariable String category){
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if (products.isEmpty()){
                ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found",null));
            }
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Success",convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/product/count/by-brand/and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try {
            var productCount = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Product Count!",productCount));
        } catch (Exception e) {
           return ResponseEntity.ok(new ApiResponse(e.getMessage(), null));
        }
    }

    



}
