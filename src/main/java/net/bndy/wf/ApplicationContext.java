/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf;

import net.bndy.ftsi.IndexService;
import net.bndy.lib.HttpHelper;
import net.bndy.wf.config.ApplicationConfig;
import net.bndy.wf.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import net.bndy.wf.modules.core.models.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.Paths;
import java.util.*;

@Component
public class ApplicationContext {

    public static ApplicationConfig applicationConfig;

    private static HttpServletRequest request;
    private static MessageSource messageSource;
    private static IndexService indexService;

    @Autowired
    private HttpServletRequest _request;
    @Autowired
    private MessageSource _messageSource;
    @Autowired
    private ApplicationConfig _applicationConfig;
    @Autowired
    private IndexService _indexService;

    @PostConstruct
    private void init() {
        request = _request;
        messageSource = _messageSource;
        applicationConfig = _applicationConfig;
        indexService = _indexService;
    }

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && (auth.getPrincipal() instanceof User)) {
            User u = (User) auth.getPrincipal();
            if (u.getAvatar() != null && !u.getAvatar().isEmpty()) {
                if (request != null && !u.getAvatar().toLowerCase().startsWith("http") && u.getAvatar().indexOf("/") < 0) {
                    u.setAvatar(getFileUrl(request, u.getAvatar()));
                }
            } else {
                u.setAvatar(getFileUrl(request, "defaultAvatar"));
            }
            return u;
        }

        return null;
    }

    public static String getFileUrl(HttpServletRequest request, String fileId) {
        return HttpHelper.getRootUrl(request) + "/files/" + fileId;
    }

    public static String getFileFullPath(String pathInDb) {
        return Paths.get(applicationConfig.getUploadPath(), pathInDb).toAbsolutePath().toString();
    }

    public static boolean isUserInRole(String roleName) {
        for (GrantedAuthority ga : getCurrentUser().getAuthorities()) {
            if (ga.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean userNoRoles() throws UnauthorizedException {
        User user = getCurrentUser();
        if (user != null) {
            return  user.getRoles() == null || user.getRoles().size() == 0;
        }

        throw new UnauthorizedException();
    }

    public static User updateCurrentUserAvatar(String fileUUID) {
        User u = getCurrentUser();
        if (u != null) {
            u.setAvatar(fileUUID);
        }
        return u;
    }

    public static ResourceBundle i18n() {
        Locale locale = LocaleContextHolder.getLocaleContext().getLocale();
        if (locale == null) {
            locale = Locale.forLanguageTag(applicationConfig.getDefaultLang());
        }
        return ResourceBundle.getBundle("i18n.messages", locale);
    }

    public static String language(String key, Object[] args, String defaultValue) {
        return messageSource.getMessage(key, args, defaultValue, LocaleContextHolder.getLocale());
    }

    public static String language(String key, Object... args) {
        return language(key, args, null);
    }

    public static IndexService getIndexService() {
        return indexService;
    }
}
