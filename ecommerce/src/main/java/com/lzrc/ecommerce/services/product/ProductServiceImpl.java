package com.lzrc.ecommerce.services.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lzrc.ecommerce.db.entities.Product;
import com.lzrc.ecommerce.db.repositories.ProductRepository;
import com.lzrc.ecommerce.db.repositories.ProductVersionsRepository;
import com.lzrc.ecommerce.db.repositories.custom.CustomProductRepository;
import com.lzrc.ecommerce.records.response.ProductRecordResponse;
import com.lzrc.ecommerce.services.product.exceptions.InsufficientStockException;
import com.lzrc.ecommerce.services.product.exceptions.ProductAlreadyExistsException;
import com.lzrc.ecommerce.services.product.exceptions.ProductNotFoundException;
import com.lzrc.ecommerce.services.product.image.ProductImageFileService;
import com.lzrc.ecommerce.services.product.image.exceptions.InvalidImageFormatException;
import com.lzrc.ecommerce.services.product.image.exceptions.SaveImageException;
import com.lzrc.ecommerce.services.product.insert.ProductInsertFlow;
import com.lzrc.ecommerce.services.product.update.ProductUpdateFlow;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductImageFileService productImageFileService;
    
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomProductRepository customProductRepository;

    @Autowired
    ProductVersionsRepository productVersionsRepository;

    @Autowired
    ProductUpdateFlow productUpdateFlow;

    @Autowired
    ProductInsertFlow productInsertFlow;

    @Value("${products.held-product-time-limit}")
    private Long expirationTime;

    private void hasSufficientStock(Product product, Long quantity) throws InsufficientStockException{
        Long remainingStock = product.getAvailableStock() - quantity;
        if(remainingStock >= 0){
            product.setAvailableStock(remainingStock);
        } else{throw new InsufficientStockException();}
    }

    private ProductRecordResponse toProductRecordResponse(Product product){
        return new ProductRecordResponse(product.getSku(), product.getName(),
        product.getDescription(), product.getPrice(), product.getAvailableStock());
    }

    @Override
    public void saveProductImage(MultipartFile image, Product product) throws SaveImageException, InvalidImageFormatException {
        productImageFileService.saveImage(image, product.getSku());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insert(Product product) throws ProductAlreadyExistsException {
        boolean productAlreadyExists = productRepository.existsById(product.getSku());
        if(productAlreadyExists){
            throw new ProductAlreadyExistsException();
        } else {
            productInsertFlow.insert(product);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Product product) throws ProductNotFoundException {
        boolean productExists = productRepository.existsById(product.getSku());
        if(productExists){
            productUpdateFlow.update(product);
        } else {throw new ProductNotFoundException();}
    }

    public Optional<ProductRecordResponse> findByIdToUpdate(String sku){
        Optional<Product> optionalProduct = productRepository.findById(sku);
        Optional<ProductRecordResponse> productRecordOptional = Optional.empty();
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            productUpdateFlow.setProductForUpdate(product);
            productRecordOptional = Optional.of(toProductRecordResponse(product));
        }
        return productRecordOptional;
    }

    @Override
    public void reduceStock(String sku, Long quantity) throws ProductNotFoundException, InsufficientStockException {
        Optional<Product> productOptional = customProductRepository.findByIdWithWriteLock(sku);
        if(productOptional.isPresent()){
            Product product = productOptional.get();
            hasSufficientStock(product, quantity);
            productRepository.save(product);
        } else {throw new ProductNotFoundException();}
    }

    @Override
    public void delete(String sku) {
        productRepository.deleteById(sku);
        productImageFileService.deleteImageIfExists(sku);
    }

    @Override
    public Page<ProductRecordResponse> findAllProducts(Pageable pageable) {
        return customProductRepository.findAll(pageable);
    }

    @Override
    public Page<ProductRecordResponse> searchProducts(String name, Pageable pageable) {
        return customProductRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Optional<ProductRecordResponse> findById(String sku) {
        return customProductRepository.findById(sku);
    }

}
