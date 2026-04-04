package com.mitocode.controller;

import com.mitocode.dto.MenuDTO;
import com.mitocode.service.IMenuService;
import com.mitocode.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {
    private final IMenuService service;
    private final MapperUtil mapperUtil;

    @PostMapping("/user")
    public ResponseEntity<List<MenuDTO>> getMenusByUser() {
        List<MenuDTO> menus = mapperUtil.mapList(service.getMenusByUsername(), MenuDTO.class);
        return ResponseEntity.ok(menus);
    }

}
