package learn.venus.domain;

import learn.venus.data.DataAccessException;
import learn.venus.data.OrbiterRepository;
import learn.venus.models.Orbiter;
import learn.venus.models.OrbiterType;

import java.util.List;

public class OrbiterService {

    private final OrbiterRepository repository;

    public OrbiterService(OrbiterRepository repository) {
        this.repository = repository;
    }

    //add
    //can't be null
    //name required
    //module == 4 astro
    //module with dock == 2 astro, 1 shuttle

    //need to validate what is an orbiter (input validation and domain
    // validation)
    public OrbiterResult add(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = validateInputs(orbiter);//checks null or not null and that name is filled in
        if (!result.isSuccess()) {//if fail we stop
            return result;
        }


        //check if we have enough room for astronauts and enough room for shuttles
        result = validateDomain(orbiter);
        if (!result.isSuccess()) {//if fail we stop
            return result;
        }

        Orbiter o = repository.add(orbiter);//if successful- add to repository, get orbiter back
        result.setPayload(o);//set payload - so we can interact with UI

        return result;
    }

    private OrbiterResult validateInputs(Orbiter orbiter) {
        OrbiterResult result = new OrbiterResult();

        if (orbiter == null) {
            result.addErrorMessage("orbiter cannot be null");
            return result;
        }

        if (orbiter.getName() == null || orbiter.getName().trim().length() == 0) {
            result.addErrorMessage("name is required");
        }
        return result;
    }

    /*DataAccessException- permission issues- file can't be written, or it's already open;service can't do anything
    about it- user interface can do something- notify the user and shut down gracefully*/

    private OrbiterResult validateDomain(Orbiter orbiter) throws DataAccessException {
        OrbiterResult result = new OrbiterResult();
        List<Orbiter> allOrbiters = repository.findAll();//grabbing all the orbiters
        if (orbiter.getType() == OrbiterType.ASTRONAUT
                || orbiter.getType() == OrbiterType.SHUTTLE) {

            //count resources
            int astroCount = 0;
            int shuttleCount = 0;
            int modCount = 0;
            int dockCount = 0;

            for (Orbiter o: allOrbiters) {
                switch (o.getType()) {
                    case MODULE:
                        modCount++;
                        break;
                    case MODULE_WITH_DOCK:
                        dockCount++;
                        break;
                    case ASTRONAUT:
                        astroCount++;
                        break;
                    case SHUTTLE:
                        shuttleCount++;
                        break;
                }
            }

            if (orbiter.getType() == OrbiterType.ASTRONAUT) {
                if (astroCount + 1 > modCount * 4 + dockCount * 2) {
                    result.addErrorMessage("no room for an astronaut.");
                }
            }
            if (orbiter.getType() == OrbiterType.SHUTTLE) {
                if (shuttleCount + 1 > dockCount) {
                    result.addErrorMessage("no room for an shuttle.");

                }
            }

        }
        return result;
    }

}



