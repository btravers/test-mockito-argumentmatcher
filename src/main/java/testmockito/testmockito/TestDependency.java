package testmockito.testmockito;

public class TestDependency {

    public String doNothing(final TestData testData) {
        return testData.getData();
    }

}
