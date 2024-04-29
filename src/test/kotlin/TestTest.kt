import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import test.joke

class TestTest {
	var haha = joke()

	@Test
	fun test() {
		assertEquals(9, haha.return9())
	}
}