package learn.venus.data;

import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrbiterFileRepositoryTest {
    private static final String SEED_PATH = "./data/orbiters-seed.csv";
    private static final String TEST_PATH = "./data/orbiters-test.csv";

    private OrbiterFileRepository repository = new OrbiterFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Files.copy(Paths.get(SEED_PATH),
                Paths.get(TEST_PATH),
                StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void shouldFindFiveOrbiters() throws DataAccessException {
        List<Orbiter> actual = repository.findAll();

        assertNotNull(actual);
        assertEquals(5, actual.size());

    }

    @Test
    void shouldFindExistingOrbiter() throws DataAccessException {
        Orbiter celestyn = repository.findById(4);
        assertNotNull(celestyn);
        assertEquals("Celestyn Kamien", celestyn.getName());
    }

    @Test
    void shouldNotFindMissingOrbiter() throws DataAccessException {
        Orbiter nope = repository.findById(10000);
        assertNull(nope);
    }

    @Test
    void shouldFindOneOfEachType() throws DataAccessException {
        List<Orbiter> modules = repository.findByType(OrbiterType.MODULE);
        assertNotNull(modules);
        assertEquals(1,modules.size());

        List<Orbiter> modulesWithDocks = repository.findByType(OrbiterType.MODULE_WITH_DOCK);
        assertNotNull(modulesWithDocks);
        assertEquals(1,modulesWithDocks.size());

        List<Orbiter> shuttles = repository.findByType(OrbiterType.SHUTTLE);
        assertNotNull(shuttles);
        assertEquals(1, shuttles.size());

        List<Orbiter> astronauts = repository.findByType(OrbiterType.ASTRONAUT);
        assertNotNull(astronauts);
        assertEquals(1, astronauts.size());

        List<Orbiter> venusians = repository.findByType(OrbiterType.VENUSIAN);
        assertNotNull(venusians);
        assertEquals(1, venusians.size());
    }

    @Test
    void shouldAddOrbiter() throws DataAccessException {
        Orbiter orbiter = new Orbiter();
        orbiter.setType(OrbiterType.MODULE);
        orbiter.setName("Test Module");
        orbiter.setSponsor("Test Sponsor");

        Orbiter actual = repository.add(orbiter);

        assertNotNull(actual);
        assertEquals(6, actual.getOrbiterId());
    }

    void shouldUpdateExisting() throws DataAccessException {
        Orbiter orbiter = new Orbiter();
        orbiter.setOrbiterId(3);
        orbiter.setName("Test Shuttle");
        orbiter.setType(OrbiterType.SHUTTLE);
        orbiter.setSponsor("Test sponsor");

        boolean success = repository.update(orbiter);
        assertTrue(success);

        Orbiter actual = repository.findById(3);
        assertNotNull(actual);
        assertEquals("Test Shuttle", actual.getName());
        assertEquals("Test Sponsor", actual.getSponsor());
    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException {
        Orbiter orbiter = new Orbiter();
        orbiter.setOrbiterId(100000);

        boolean actual = repository.update(orbiter);
        assertFalse(actual);
    }

    @Test
    void shouldDeleteExisting() throws DataAccessException {
        boolean actual = repository.deleteById(2);
        assertTrue(actual);

    }

    @Test
    void shouldNotDeleteMissing() throws DataAccessException {
        boolean actual = repository.deleteById(1000000);
        assertFalse(actual);

    }

}