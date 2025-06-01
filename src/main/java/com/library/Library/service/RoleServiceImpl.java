package com.library.Library.service;

import com.library.Library.entity.RoleEntity;
import com.library.Library.repository.RoleRepository;
import com.library.Library.service.api.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public RoleEntity getUserRole() {
        return roleRepository.findByName("ROLE_USER").get();
    }
}
