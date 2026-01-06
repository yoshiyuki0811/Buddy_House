package com.example.buddyhouse.controller.menu;

import com.example.buddyhouse.dto.menu.CreateMenuDto;
import com.example.buddyhouse.dto.menu.MenusListDto;
import com.example.buddyhouse.enums.ReservationType;
import com.example.buddyhouse.service.MenusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "10.Admin　-　Menus", description = "メニュー操作(管理者)")
@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
public class AdminMenusController {

  private final MenusService menusService;

  @PostMapping("/{type}")
  public ResponseEntity<Void> createMenus(
      @PathVariable ReservationType type,
      @RequestBody CreateMenuDto dto
  ) {
    menusService.createMenus(type, dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  @GetMapping
  public List<MenusListDto> getAllMenus() {
    return menusService.getAllMenus();

  }

  @PatchMapping("/{id}/deleted")
  public ResponseEntity<Void> deletedMenusById(@PathVariable Long id) {
     menusService.deleteMenusById(id);
    return ResponseEntity.noContent().build();
  }
}

