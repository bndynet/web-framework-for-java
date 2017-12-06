/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.bndy.wf.modules.app.models.User;

import java.util.*;

public class ApplicationContext {

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && (auth.getPrincipal() instanceof User)) {
            return (User) auth.getPrincipal();
        }

        return null;
    }

    public static ResourceBundle i18n() {
        return ResourceBundle.getBundle("i18n.messages", LocaleContextHolder.getLocale());
    }
}
