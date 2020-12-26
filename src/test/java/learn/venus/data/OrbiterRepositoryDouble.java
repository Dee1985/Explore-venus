package learn.venus.data;

import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import java.util.ArrayList;
import java.util.List;

public class OrbiterRepositoryDouble implements OrbiterRepository{

    private ArrayList<Orbiter> orbiters = new ArrayList<>();

    public OrbiterRepositoryDouble() {
        Orbiter moduleWithDock = new Orbiter();
        moduleWithDock.setOrbiterId(1);
        moduleWithDock.setName("Mod with Dock");
        moduleWithDock.setType(OrbiterType.MODULE_WITH_DOCK);
        orbiters.add(moduleWithDock);
        orbiters.add(new Orbiter(2,"Astro 1", OrbiterType.ASTRONAUT, null));
        orbiters.add(new Orbiter(3,"Astro 2", OrbiterType.ASTRONAUT, null));
        orbiters.add(new Orbiter(4,"Shuttle #1", OrbiterType.SHUTTLE, null));
    }

    @Override
    public List<Orbiter> findAll() throws DataAccessException {
        return new ArrayList<>(orbiters);
    }

    @Override
    public Orbiter findById(int orbiterId) throws DataAccessException {
        return null;
    }

    @Override
    public List<Orbiter> findByType(OrbiterType type) throws DataAccessException {
        return null;
    }

    @Override
    public Orbiter add(Orbiter orbiter) throws DataAccessException {
        orbiters.add(orbiter);//in arrayList .add() returns a boolean
        return orbiter;

    }

    @Override
    public boolean update(Orbiter orbiter) throws DataAccessException {
        return false;
    }

    @Override
    public boolean deleteById(int orbiterId) throws DataAccessException {
        return false;
    }
}
