package com.ems.algasensors.device.management;

import io.hypersistence.tsid.TSID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

class TSIDTest {

    @Test
    void testShouldGenerateTSID() {
        //TSID.fast() method must be used only for tests. In production use IdGenerator.generateTSID() created below.
        TSID fastTsid = TSID.fast();
        System.out.println(fastTsid);
        System.out.println(fastTsid.toLong());
        System.out.println(fastTsid.getInstant());

        //Use this implementation in production.
        TSID tsid = IdGenerator.generateTSID();
        System.out.println();
        System.out.println(tsid);
        System.out.println(tsid.toLong());
        System.out.println(tsid.getInstant());

        //You can run test using this environment variable: tsid.node=7;tsid.node.count=64
        Assertions.assertThat(tsid.getInstant())
                .isCloseTo(Instant.now(), Assertions.within(1, ChronoUnit.MINUTES));
    }

}
