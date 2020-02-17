package testmockito.testmockito;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TestService {

    private final TestDependency testDependency;

    public void testMethod() {
        final var data = new TestData();
        data.setData("test_1");
        this.testDependency.doNothing(data);

        data.setData("test_2");
        this.testDependency.doNothing(data);
    }

}
