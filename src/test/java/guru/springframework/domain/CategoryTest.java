package guru.springframework.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryTest {

    Category category;

    @Before
    public void setup() {

        category = new Category();
    }

    @Test
    public void getId() {
        Long val = 4L;

        category.setId(val);

        assertEquals(val, category.getId());
    }

    @Test
    public void getName() {
    }

    @Test
    public void getRecipes() {
    }
}