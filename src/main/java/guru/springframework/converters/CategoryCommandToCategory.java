package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category>{

    @Synchronized
    @Override
    public Category convert(CategoryCommand categoryCommand) {

        if (categoryCommand == null) {
            return null;
        }

        Category cat = new Category();
        cat.setId(categoryCommand.getId());
        cat.setName(categoryCommand.getName());

        return cat;
    }
}
