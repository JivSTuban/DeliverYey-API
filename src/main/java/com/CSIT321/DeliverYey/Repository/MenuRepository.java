package com.CSIT321.DeliverYey.Repository;

import com.CSIT321.DeliverYey.Entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity, Integer> {
    MenuEntity saveAndFlush(MenuEntity entity);
}
