import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class AbcBarTest {

    private AbcBar abcBar;

    @Before
    public void setUp() {
        abcBar = new AbcBar();
    }

    @Test
    public void getAbcBarWord_NumberIsMultipleOfThree_ShouldReturnFizz() {
        assertThat(abcBar.getAbcBarWord(3), is("Abc"));
    }

    @Test
    public void getAbcBarWord_NumberIsMultipleOfFive_ShouldReturnBuzz() {
        assertThat(abcBar.getAbcBarWord(5), is("Bar"));
    }

    @Test
    public void getAbcBarWord_NumberIsMultipleOfThreeAndFive_ShouldReturnAbcBar() {
        assertThat(abcBar.getAbcBarWord(15), is("AbcBar"));
    }

    @Test
    public void getAbcBarWord_NumberIsNotMultipleOfThreeOrFive_ShouldReturnNull() {
        assertNull(abcBar.getAbcBarWord(4));
    }
}
