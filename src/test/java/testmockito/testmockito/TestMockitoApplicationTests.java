package testmockito.testmockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.exceptions.verification.opentest4j.ArgumentsAreDifferent;

import java.util.Objects;

import static org.mockito.Mockito.*;

class TestMockitoApplicationTests {

    private final TestDependency testDependency = mock(TestDependency.class);

    private final TestService testService = new TestService(this.testDependency);

    @Test
    void test_using_argument_matchers() {
        when(this.testDependency.doNothing(any())).thenCallRealMethod();

        this.testService.testMethod();

        // Fails because Mockito keep a reference of the argument we mutate
        // and perform the argument verification at the end the test, once every mutations have been done
        Assertions.assertThrows(ArgumentsAreDifferent.class, () -> {
            verify(this.testDependency).doNothing(argThat(testData -> Objects.equals(testData.getData(), "test_1")));
            verify(this.testDependency).doNothing(argThat(testData -> Objects.equals(testData.getData(), "test_2")));
        });

    }

    @Test
    void test_using_answers() {
        when(this.testDependency.doNothing(any()))
                .thenAnswer(invocationOnMock -> {
                    final var arg = (TestData) invocationOnMock.getArgument(0);
                    Assertions.assertEquals("test_1", arg.getData());
                    return arg.getData();
                })
                .thenAnswer(invocationOnMock -> {
                    final var arg = (TestData) invocationOnMock.getArgument(0);
                    Assertions.assertEquals("test_2", arg.getData());
                    return arg.getData();
                });

        this.testService.testMethod();

        verify(this.testDependency, times(2)).doNothing(any());
    }
}
