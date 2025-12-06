package com.example.buddyhouse.controller;

import com.example.buddyhouse.dto.MenusDto;
import com.example.buddyhouse.dto.MenusListDto;
import com.example.buddyhouse.enums.ReservationType;
import com.example.buddyhouse.service.MenusService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/menus")
@RequiredArgsConstructor
public class AdminMenusController {

  private final MenusService menusService;

  @PostMapping("/{type}")
  public ResponseEntity<MenusDto> createMenus(
      @PathVariable ReservationType type,
      @RequestBody MenusDto dto
  ) {
    dto.setReservationType(type);
    MenusDto saved = menusService.createMenus(dto);
    return ResponseEntity.ok(saved);
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

