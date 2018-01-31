package net.bndy.wf.test;

import net.bndy.wf.service.AppService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InitDataTest extends _Test {

    @Autowired
    AppService appService;

    @Test
    public void initSeed() {
        this.appService.initBasicData();
    }
}
