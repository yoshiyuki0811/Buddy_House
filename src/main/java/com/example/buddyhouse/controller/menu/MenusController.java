package com.example.buddyhouse.controller.menu;

import com.example.buddyhouse.dto.menu.MenusListDto;
import com.example.buddyhouse.service.MenusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "10.Menus", description = "メニュー参照(会員)")
@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenusController {
  private final MenusService menusService;
  @GetMapping
  public List<MenusListDto> getAllMenus() {
    return menusService.getAllMenus();

  }

}
