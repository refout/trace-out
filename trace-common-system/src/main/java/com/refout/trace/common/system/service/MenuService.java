package com.refout.trace.common.system.service;

import java.util.List;
import java.util.Set;

public interface MenuService {

    List<String> getPermissionByUserId(final long userId);

}
