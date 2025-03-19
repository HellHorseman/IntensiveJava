package ru.y_lab.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.y_lab.model.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DisplayName("Тесты для CategoryRepository")
public class CategoryRepositoryTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() throws SQLException {
        Connection connection = DriverManager.getConnection(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        categoryRepository = new CategoryRepository(connection);
    }

    @Test
    @DisplayName("Сохранение категории")
    void testSave() {
        Category category = new Category();
        category.setName("Еда");

        categoryRepository.save(category);

        List<Category> categories = categoryRepository.findAll();
        assertFalse(categories.isEmpty());
        assertEquals("Еда", categories.get(0).getName());
    }

    @Test
    @DisplayName("Получение всех категорий")
    void testFindAll() {
        Category category1 = new Category();
        category1.setName("Еда");

        Category category2 = new Category();
        category2.setName("Транспорт");

        categoryRepository.save(category1);
        categoryRepository.save(category2);

        List<Category> categories = categoryRepository.findAll();
        assertEquals(2, categories.size());
    }

    @Test
    @DisplayName("Обновление категории")
    void testUpdate() {
        Category category = new Category();
        category.setName("Еда");

        categoryRepository.save(category);

        List<Category> categories = categoryRepository.findAll();
        Category savedCategory = categories.get(0);

        savedCategory.setName("Продукты");
        categoryRepository.update(savedCategory);

        List<Category> updatedCategories = categoryRepository.findAll();
        assertEquals("Продукты", updatedCategories.get(0).getName());
    }

    @Test
    @DisplayName("Удаление категории")
    void testDelete() {
        Category category = new Category();
        category.setName("Еда");

        categoryRepository.save(category);

        List<Category> categories = categoryRepository.findAll();
        Category savedCategory = categories.get(0);

        categoryRepository.delete(savedCategory.getId());

        List<Category> remainingCategories = categoryRepository.findAll();
        assertTrue(remainingCategories.isEmpty());
    }
}
