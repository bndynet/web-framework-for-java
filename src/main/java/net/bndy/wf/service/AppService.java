package net.bndy.wf.service;

import org.aspectj.util.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class AppService {

    public List<String> allModules() throws IOException {
        List<String> modules = new ArrayList<>();

        File rootModule = new ClassPathResource("/static/apps/admin/modules").getFile();

        for (File f : FileUtil.listFiles(rootModule, (pathname -> pathname.getName().toLowerCase().endsWith(".html")))) {
            String moduleName = f.getPath()
                .replace(".html", "")
                .replace(rootModule.getAbsolutePath(), "")
                .replaceAll("[\\\\/]+", "-")
                .replaceAll("^-", "");

            modules.add(moduleName);
        }

        return modules;
    }
}
