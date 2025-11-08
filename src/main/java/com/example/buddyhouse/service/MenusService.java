package com.example.buddyhouse.service;

import com.example.buddyhouse.dto.MenusDto;
import com.example.buddyhouse.entity.MenusEntity;
import com.example.buddyhouse.mapper.MenusMapper;
import com.example.buddyhouse.repository.MenusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenusService {
  private final MenusMapper menusMapper;
  private final MenusRepository menusRepository;

  public MenusDto createMenus(MenusDto dto){
    MenusEntity entity = menusMapper.toEntity(dto);
    MenusEntity saved = menusRepository.save(entity);// DB保存
    return menusMapper.toDto(saved);
  }
  }


