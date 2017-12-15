/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.bndy.wf.modules.app.models.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ApplicationContext {

    private static MessageSource messageSource;

    @Autowired
    private MessageSource _messageSource;

    @PostConstruct
    private void init() {
        messageSource = _messageSource;
    }

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

    public static String language(String key, Object[] args, String defaultValue) {
        return messageSource.getMessage(key, args, defaultValue, LocaleContextHolder.getLocale());
    }

    public static String language(String key, Object... args) {
        return language(key, args, null);
    }
}
