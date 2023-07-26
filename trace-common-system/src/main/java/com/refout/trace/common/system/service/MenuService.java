package com.refout.trace.common.system.service;

import java.util.Set;

public interface MenuService {

    Set<String> getPermissionByUserId(final long userId);

}
