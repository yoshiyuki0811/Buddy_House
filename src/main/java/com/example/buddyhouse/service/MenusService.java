package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.MenusDto;
import com.example.buddyhouse.dto.MenusListDto;
import com.example.buddyhouse.entity.MenuEntity;
import com.example.buddyhouse.mapper.MenusMapper;
import com.example.buddyhouse.repository.MenusRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * メニュードメインを扱うサービスクラスです。
 */
@Service
@RequiredArgsConstructor
public class MenusService {

  private final MenusMapper menusMapper;
  private final MenusRepository menusRepository;

  //メニューの登録
  public MenusDto createMenus(MenusDto dto) {
    MenuEntity saved = menusRepository.save(menusMapper.toEntity(dto));// DB保存
    return menusMapper.toDto(saved);
  }

  public List<MenusListDto> getAllMenus() {
    List<MenuEntity> entityList = menusRepository.findByActiveTrueAndDeletedFalse();
    return entityList
        .stream()
        .map(menusMapper::toListDto)
        .toList();
  }

  //メニューテーブルの削除フラグをtrueに変換して登録
  @Transactional
  public void deleteMenusById(Long id) {
    MenuEntity entity = menusRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("メニューがみつかりません。"));
    entity.delete();
    menusRepository.save(entity);



  }
}


