package com.project.financial_management.enums;

public enum Roles {

    ADMIN(1L),
    BASIC(2L);

    final Long roleId;

    Roles(Long roleId) {
        this.roleId = roleId;
    }

    public long getRoleId() {
        return roleId;
    }

}
