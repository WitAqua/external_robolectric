package org.robolectric;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner.Parameter;
import org.robolectric.ParameterizedRobolectricTestRunner.Parameters;

/**
 * Tests for the single parameter test with {@link Iterable} as return type.
 *
 * <p>See https://github.com/junit-team/junit4/wiki/parameterized-tests#tests-with-single-parameter.
 */
@RunWith(ParameterizedRobolectricTestRunner.class)
public class ParameterizedRobolectricTestRunnerIterableSingleParameterTest {
  @Parameter public int intValue;

  @Test
  public void parameters_shouldHaveValues() {
    assertThat(intValue).isNotEqualTo(0);
  }

  @Parameters
  public static Iterable<?> parameters() {
    return Arrays.asList(1, 2, 3, 4, 5);
  }
}
