package stop.covid.challenge.cafein.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stop.covid.challenge.cafein.domain.model.Product;
import stop.covid.challenge.cafein.dto.ProductDto;
import stop.covid.challenge.cafein.repository.ProductRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    @ApiOperation(value = "모든 제품 가져오기", notes = "모든 제품 가져오기")
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProduct() {
        List<Product> products = productRepository.findAll();
        if (products.toArray().length > 0) {
            Map<String, Object> result = new HashMap<>();
            result.put("product", products);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.notFound().build();
    }

    @ApiOperation(value = "제품 등록하기", notes = "제품 등록하기")
    @PostMapping("/create")
    public ResponseEntity createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(201).body(productRepository.save(productDto.toEntity()));
    }
}
