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
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenusController {

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
  public ResponseEntity<List<MenusListDto>> getAllMenus() {
    List<MenusListDto> menus = menusService.getAllMenus();
    return ResponseEntity.ok(menus);

  }

  @PatchMapping("/{id}/deleted")
  public ResponseEntity<MenusDto> deletedMenusById(@PathVariable Long id) {
    MenusDto menusDeleted = menusService.deleteMenusById(id);
    return ResponseEntity.ok(menusDeleted);
  }
}

