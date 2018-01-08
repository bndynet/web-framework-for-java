/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf;

import net.bndy.wf.lib.HttpHelper;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.RequestContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import net.bndy.wf.modules.core.models.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContext;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class ApplicationContext {

    private static MessageSource messageSource;
    private static HttpServletRequest request;

    @Autowired
    private MessageSource _messageSource;
    @Autowired
    private HttpServletRequest _request;

    @PostConstruct
    private void init() {
        messageSource = _messageSource;
        request = _request;
    }

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && (auth.getPrincipal() instanceof User)) {
            User u = (User) auth.getPrincipal();
            if (request != null && u.getAvatar() != null && !u.getAvatar().toLowerCase().startsWith("http")) {
                u.setAvatar(HttpHelper.getFileUrl(request, u.getAvatar()));
            }
            return u;
        }

        return null;
    }

    public static User updateCurrentUserAvatar(String fileUUID) {
        User u = getCurrentUser();
        if (u != null) {
            u.setAvatar(fileUUID);
        }
        return u;
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
