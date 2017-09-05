import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RunTest {
    private int key;
    private String methodName;

    private RunTest(int key, String methodName) {
        this.key = key;
        this.methodName = methodName;
    }

    public static void main(String[] args) throws Exception {
        ClassForTest classForTest = new ClassForTest();
        ClassForTestWithException csException = new ClassForTestWithException();

        //csException - класс в котором больше чем 1 @AfterSuite или @BeforeSuite
//        start(csException);

        start(classForTest);

    }

    public static void start(Object object) throws Exception {
        CheckThatSuiteOnlyOne(object);

        runBeforeSuite(object);
        runTestMethods(object);
        runAfterSuite(object);

    }

    //Проверка, что в Классе только один @BeforeSuite и @AfterSuite
    public static void CheckThatSuiteOnlyOne(Object object) {
        Method[] methods = object.getClass().getDeclaredMethods();
        int countForBeforeSuite = 0;
        int countForAfterSuite = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                countForBeforeSuite++;
            }
            if (method.isAnnotationPresent(AfterSuite.class)) {
                countForAfterSuite++;
            }
        }
        if (countForBeforeSuite > 1 || countForAfterSuite > 1) throw new RuntimeException();
    }

    //Запуск метода c @BeforeSuite
    public static void runBeforeSuite(Object object) throws Exception {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                method.invoke(object);
            }
        }
    }

    //Запуск методов @Test по приоритету
    public static void runTestMethods(Object object) throws Exception {
        Method[] methods = object.getClass().getDeclaredMethods();

        //Создаем Лист который хранит приоритет и название метода
        List<RunTest> methodList = new ArrayList<>();

        //Добавляем в Лист все методы с аннотацией Test
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class)) {
                methodList.add(new RunTest(method.getAnnotation(Test.class).priority(), method.getName()));
            }
        }

        //Сортируем коллекцию по возрастанию
        Collections.sort(methodList, new Comparator<RunTest>() {
            @Override
            public int compare(RunTest o1, RunTest o2) {
                int o1Key = o1.key;
                int o2Key = o2.key;
                return Integer.compare(o1Key, o2Key);
            }
        });

        //Удаляем методы из Листа, у которых приоритет меньше 0 или больше чем 10
        for (int i = 0; i < methodList.size(); i++) {
            if (methodList.get(i).key > 10 || methodList.get(i).key < 1) {
                System.out.println(methodList.get(i).methodName + "- не будет выполнен. Указан неверный приоритет метода");
                methodList.remove(i);
            }
        }

        //Вызываем отсортированные методы
        for (RunTest methodInList : methodList) {
            String methodName = methodInList.methodName;
            Method method = object.getClass().getDeclaredMethod(methodName);
            method.invoke(object);
        }

    }

    //Запуск метода c @AfterSuite
    public static void runAfterSuite(Object object) throws Exception {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(AfterSuite.class)) {
                method.invoke(object);
            }
        }
    }

}
