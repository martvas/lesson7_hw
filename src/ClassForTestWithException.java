public class ClassForTestWithException {

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("Метод - BeforeSuite");
    }

    @BeforeSuite
    public void beforeSuite2(){
        System.out.println("Метод - BeforeSuite2");
    }

    @Test
    public void method1(){
        System.out.println("Первый тест");
    }

    @Test
    public void method2(){
        System.out.println("Второй тест");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("Метод - AfterSuite");
    }
}
