public class ClassForTest {

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("Метод - BeforeSuite");
    }

    @Test (priority = 7)
    public void method1(){
        System.out.println("Выполнен метод1. Приотритет - 7.");
    }

    @Test
    public void method2(){
        System.out.println("Выполнен метод2. Приотритет стандариный - 5");
    }

    @Test (priority = 3)
    public void method3(){
        System.out.println("Выполнен метод3. Приотритет - 3.");
    }

    @Test (priority = 3)
    public void method4(){
        System.out.println("Выполнен метод4. Приотритет - 3.");
    }

    @Test (priority = 1)
    public void method5(){
        System.out.println("Выполнен метод3. Приотритет - 1.");
    }

    @Test (priority = 15)
    public void method6(){
        System.out.println("Выполнен метод6. Приотритет - 15.");
    }

    @Test (priority = -5)
    public void method7(){
        System.out.println("Выполнен метод7. Приотритет - -5.");
    }

    @Test (priority = 10)
    public void method10(){
        System.out.println("Выполнен метод10. Приотритет - 10.");
    }


    @AfterSuite
    public void afterSuite(){
        System.out.println("Метод - AfterSuite");
    }
}
