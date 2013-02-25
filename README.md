Validator4j is a attempt to create a simple and lightweight validation framework for Java.
One of it's main goals in opposite to javax.validation is a possibility to define validation rules
outside the java beans.


Example for a plain Java-Bean validation:
------

Let's assume, we have following Java-Bean, we want to validate:
```
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
```

In this example we will implement following validation rules:
    - Bean instance should be not null
    - "id"-property should be in range between 20 and 100
    - "name"-property should contains only unicode letters
    - "value"-property should be in range between 0 and 100
    - "date"-property should be not null and in the future

This can be achieved defining following validator:

```
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

    ValidationResult result = validator.validate(bean);

    // alternatively we can throw validation exception containing ValidationResult as payload:
    // validator.validate(bean).throwIfNotEmpty();
```

Now we can get validation messages from the validation result:

```
    Collection<IValidationMessage> messages = vr.getMessages();
```

Running this example the validation result will contain following messages:

```
ValidationResult[
	key=bean.id.IS_NOT_IN_RANGE,severity=ERROR,args={20, 100}
	key=bean.name.IS_NOT_ALPHA,severity=ERROR,args={}
	key=bean.date.IS_IN_THE_PAST,severity=ERROR,args={}
]
```


Example for a hierarchical Java-Bean validation:
------

Let's assume, we have following Java-Beans, we want to validate:
```
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
```


And some more complex nested validators:
```
    RootBean bean = new RootBean(1, new ChildBean1("abc"), new ChildBean2(2, null));
    try {
        IValidator<ChildBean1> v1 =
                new HierarchicalValidator<ChildBean1>()
                        .withPreValidator(IS_NULL, PredefinedChecks.<ChildBean1>notNull())
                        .processFieldsIfPreValidationFails()
                        .addFieldValidator("name", IS_NULL, notNull());

        IValidator<ChildBean2> v2 =
                new HierarchicalValidator<ChildBean2>()
                        .withPreValidator(IS_NULL, PredefinedChecks.<ChildBean2>notNull())
                        .processFieldsIfPreValidationFails()
                        .addFieldValidator(
                                "age", //
                                Validator.of(IS_NULL, notNull()),
                                Validator.of(IS_NOT_IN_RANGE.withArgs("12", "60"),
                                        inRange(12, 60)))
                        .addFieldValidator("date", IS_NOT_VALID, notNull(), inThePast());

        HierarchicalValidator<RootBean> v =
                new HierarchicalValidator<RootBean>()
                        .withPrefix("root")
                        .addFieldValidator(
                                "id", //
                                Validator.of(IS_NULL, notNull()),
                                Validator.of(IS_NOT_IN_RANGE.withArgs("10", "100"),
                                        inRange(10, 100)),
                                Validator.of(IS_LTE.withArgs("10"), gt(10)),
                                Validator.of(IS_GTE.withArgs("100"), lt(100)))
                        .addFieldValidator("childBean1", v1)//
                        .addFieldValidator("childBean2", v2);

        v.validate(bean).throwIfNotEmpty();

        fail("ValidationException should be thrown!");

    } catch (ValidationException e) {

        ValidationResult vr = e.getValidationResult();
        System.out.println(vr);
    }
```

Running this example the validation result will contain following messages:

```
ValidationResult[
	key=root.id.IS_NOT_IN_RANGE,severity=ERROR,args={10, 100}
	key=root.childBean2.age.IS_NOT_IN_RANGE,severity=ERROR,args={12, 60}
	key=root.childBean2.date.IS_NOT_VALID,severity=ERROR,args={}
]
```

