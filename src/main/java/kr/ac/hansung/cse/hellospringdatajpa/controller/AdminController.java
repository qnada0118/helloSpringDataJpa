package kr.ac.hansung.cse.hellospringdatajpa.controller;

import jakarta.validation.Valid;
import kr.ac.hansung.cse.hellospringdatajpa.entity.Product;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import kr.ac.hansung.cse.hellospringdatajpa.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    private final UserRepository userRepository;
    private final ProductService productService;

    // 관리자 대시보드
    @GetMapping
    public String showAdminDashboard() {
        return "admin"; // templates/admin.html
    }

    // 사용자 관리
    @GetMapping("/users")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin_users"; // templates/admin-users.html
    }

    // 상품 목록 (관리자용)
    @GetMapping("/products")
    public String showProductList(Model model) {
        model.addAttribute("listProducts", productService.listAll());
        return "index"; // 같은 index.html 사용
    }

    // 상품 등록 폼
    @GetMapping("/products/new")
    public String showNewProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "new_product";
    }

    // 상품 수정 폼
    @GetMapping("/products/edit/{id}")
    public String showEditProductPage(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.get(id));
        return "edit_product";
    }

    // 상품 저장
    @PostMapping("/products/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return (product.getId() == null) ? "new_product" : "edit_product";
        }
        productService.save(product);
        return "redirect:/admin/products";
    }

    // 상품 삭제
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/admin/products";
    }
}
