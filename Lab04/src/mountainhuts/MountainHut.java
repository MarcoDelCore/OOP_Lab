package mountainhuts;

import java.util.Optional;


/**
 * Represents a mountain hut
 * 
 * It includes a name, optional altitude, category,
 * number of beds and location municipality.
 *  
 *
 */
public class MountainHut {
	Municipality municipality;
	String name;
	Optional<Integer> altitude;
	String category;
	int bedsNumber;
	Region r;
	
	public MountainHut(String name, Optional<Integer> altitude, String category, Integer bedsNumber, Municipality municipality, Region r) {
		this.name = name;
		this.altitude = altitude;
		this.category = category;
		this.bedsNumber = bedsNumber;
		this.municipality = municipality;
		this.r = r;
	}

	public String getName() {
		return name;
	}

	public Optional<Integer> getAltitude() {
		if (altitude.isPresent())
			return altitude;
		else return Optional.empty();
	}

	public String getCategory() {
		return category;
	}

	public Integer getBedsNumber() {
		return bedsNumber;
	}

	public Municipality getMunicipality() {
		return municipality;
	}

	public String getAltitudeRange() {
		if (!altitude.isEmpty()) return r.getAltitudeRange(altitude.get());
		return r.getAltitudeRange(municipality.altitude);
	}
}
