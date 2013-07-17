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

import java.util.Date;


/**
 * @author Maxim Kalina
 * @version $Id$
 */

public class HierarchicalValidatorTest extends TestCase {

    private static final Logger log = LoggerFactory.getLogger(HierarchicalValidatorTest.class);

    public class RootBean {
        public RootBean(int id, ChildBean1 childBean1, ChildBean2 childBean2) {
            this.id = id;
            this.childBean1 = childBean1;
            this.childBean2 = childBean2;
        }

        public int id;

        public ChildBean1 childBean1;

        public ChildBean2 childBean2;

    }

    public class ChildBean1 {
        public ChildBean1(String name) {
            this.name = name;
        }

        public String name;
    }

    public class ChildBean2 {
        public ChildBean2(int age, Date date) {
            this.age = age;
            this.date = date;
        }

        public int age;

        public Date date;
    }

    public void testHierarchicalValidation() throws Exception {

        RootBean bean = new RootBean(1, new ChildBean1("abc"), new ChildBean2(2, null));
        try {

            IValidator<ChildBean1> v1 =
                    new HierarchicalValidator<ChildBean1>()
                            .withPreValidator(PredefinedMessages.IS_NULL, PredefinedChecks.<ChildBean1>notNull())
                            .processFieldsIfPreValidationFails()
                            .addFieldValidator("name", PredefinedMessages.IS_NULL, PredefinedChecks.notNull());

            IValidator<ChildBean2> v2 =
                    new HierarchicalValidator<ChildBean2>()
                            .withPreValidator(PredefinedMessages.IS_NULL, PredefinedChecks.<ChildBean2>notNull())
                            .processFieldsIfPreValidationFails()
                            .addFieldValidator(
                                    "age", //
                                    Validator.of(PredefinedMessages.IS_NULL, PredefinedChecks.notNull()),
                                    Validator.of(PredefinedMessages.IS_NOT_IN_RANGE.withArgs("12", "60"),
                                            PredefinedChecks.inRange(12, 60)))
                            .addFieldValidator("date", PredefinedMessages.IS_NOT_VALID, PredefinedChecks.notNull(), PredefinedChecks.inThePast());

            HierarchicalValidator<RootBean> v =
                    new HierarchicalValidator<RootBean>()
                            .withPrefix("root")
                            .addFieldValidator(
                                    "id", //
                                    Validator.of(PredefinedMessages.IS_NULL, PredefinedChecks.notNull()),
                                    Validator.of(PredefinedMessages.IS_NOT_IN_RANGE.withArgs("10", "100"),
                                            PredefinedChecks.inRange(10, 100)),
                                    Validator.of(PredefinedMessages.IS_LTE.withArgs("10"), PredefinedChecks.gt(10)),
                                    Validator.of(PredefinedMessages.IS_GTE.withArgs("100"), PredefinedChecks.lt(100)))
                            .addFieldValidator("childBean1", v1)//
                            .addFieldValidator("childBean2", v2);

            v.validate(bean).throwIfNotEmpty();

            fail("ValidationException should be thrown!");

        } catch (ValidationException e) {
            ValidationResult vr = e.getValidationResult();
            assertTrue(vr.isNotEmpty());
            assertTrue(vr.size() == 3);
            log.info("Expected validation result: {}", vr);
        }

    }
}
