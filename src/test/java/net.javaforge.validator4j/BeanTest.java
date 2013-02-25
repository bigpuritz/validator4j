/*
 * Copyright 2013 [name of copyright owner]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.javaforge.validator4j;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;

import static net.javaforge.validator4j.PredefinedChecks.*;
import static net.javaforge.validator4j.PredefinedMessages.*;

/**
 * @author Maxim Kalina
 * @version $Id$
 */
public class BeanTest extends TestCase {

    private static final Logger log = LoggerFactory.getLogger(BeanTest.class);

    public class Bean {

        public Bean(int id, String name, BigDecimal value, Date date) {
            this.id = id;
            this.name = name;
            this.value = value;
            this.date = date;
        }

        public int id;

        public String name;

        public BigDecimal value;

        public Date date;

    }

    public void testBean() throws Exception {

        try {

            Bean bean = new Bean(1, "test123", BigDecimal.ONE, null);

            HierarchicalValidator<Bean> validator =
                    new HierarchicalValidator<Bean>()
                            .withPrefix("bean")
                            .withPreValidator(IS_REQUIRED, PredefinedChecks.<Bean>notNull())
                            .addFieldValidator("id", IS_NOT_IN_RANGE.withArgs("20", "100"),
                                    gte(20), lte(100))
                            .addFieldValidator("name", IS_NOT_ALPHA, alpha())
                            .addFieldValidator("value", IS_NOT_IN_RANGE,
                                    inRange(BigDecimal.ONE, BigDecimal.TEN))
                            .addFieldValidator("date", IS_IN_THE_PAST, notNull(), inTheFuture());

            validator.validate(bean).throwIfNotEmpty();

            fail("ValidationException should be thrown!");

        } catch (ValidationException e) {
            ValidationResult vr = e.getValidationResult();
            assertTrue(vr.isNotEmpty());
            // everything ok!
            log.info("Expected validation result: {}", e.getValidationResult());
        }

    }

}
