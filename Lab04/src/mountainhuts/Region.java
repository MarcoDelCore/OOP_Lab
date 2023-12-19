package mountainhuts;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region{
	
	private static final int PROVINCE = 0;
	private static final int MUNICIPALITY = 1;
	private static final int MUNICIPALITY_ALTITUDE = 2;
	private static final int NAME = 3;
	private static final int ALTITUDE = 4;
	private static final int CATEGORY = 5;
	private static final int BEDS_NUMBER = 6;
	
	String name;
	List<String> altitudes;
	List<Municipality> municipalities;
	List<MountainHut> mountainHuts;

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	public Region(String name) {
		this.name = name;
		altitudes = new ArrayList<String>();
		municipalities = new ArrayList<Municipality>();
		mountainHuts = new ArrayList<>();
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	public void setAltitudeRanges(String... ranges) {
		for (String s : ranges) {
			altitudes.add(s);
		}
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		for (String s : altitudes) {
			int min = Integer.parseInt(s.split("-")[0]);
			int max = Integer.parseInt(s.split("-")[1]);
			if (altitude >= min && altitude <= max) return s;
			
		}
		return "0-INF";
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return municipalities;
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * The returned collection is unmodifiable
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return mountainHuts;
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if (municipalities != null) {
			for (Municipality m : municipalities) {
				if (m.name == name) return m;
			}
		}
		Municipality tmp = new Municipality(name, province, altitude, this);
		municipalities.add(tmp);
		return tmp;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		for (MountainHut m : mountainHuts) {
			if (m.name == name) return m;
		}
		Optional<Integer> n = Optional.empty();
		MountainHut tmp = new MountainHut(name, n, category, bedsNumber, municipality, this);
		mountainHuts.add(tmp);
		return tmp;
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		for (MountainHut m : mountainHuts) {
			if (m.name == name) return m;
		}
		Optional<Integer> n = Optional.of(altitude);
		MountainHut tmp = new MountainHut(name, n, category, bedsNumber, municipality, this);
		mountainHuts.add(tmp);
		return tmp;
	}

	/**
	 * Creates a new region and loads its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		String ProvinceName, MunicipalityName, MunicipalityAltitude, HutName, HutAltitude, HutCategory, HutBedsNumber;
		List<String> lines = readData(file);
		Region tmp = new Region(name);
		Boolean head = true;
		for (String line : lines) {
			if (head) head = false;
			else {
				String[] fields = line.split(";");
				ProvinceName = fields[PROVINCE];
				MunicipalityName = fields[MUNICIPALITY];
				MunicipalityAltitude = fields[MUNICIPALITY_ALTITUDE];
				HutName = fields[NAME];
				HutAltitude = fields[ALTITUDE];
				HutCategory = fields[CATEGORY];
				HutBedsNumber = fields[BEDS_NUMBER];
				Boolean found = false;
				Municipality m=null;
				for (Municipality m1 : tmp.municipalities) {
					if (m1.name.compareTo(MunicipalityName) == 0) {
						found = true;
						m=m1;
					}
				}
				if (!found) {
					m = new Municipality(MunicipalityName, ProvinceName, Integer.parseInt(MunicipalityAltitude), tmp);
					tmp.municipalities.add(m);
				}
				MountainHut h;
				if (HutAltitude == "") h = new MountainHut(HutName, Optional.empty(), HutCategory, Integer.parseInt(HutBedsNumber), m, tmp);
				else h = new MountainHut(HutName, Optional.of(Integer.parseInt(HutAltitude)), HutCategory, Integer.parseInt(HutBedsNumber), m, tmp);
				tmp.mountainHuts.add(h);
			}
		}
		return tmp;
	}


	/**
	 * Reads the lines of a text file.
	 *
	 * @param file path of the file
	 * @return a list with one element per line
	 */
	public static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return new ArrayList<>();
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		Map<String, Long> res = mountainHuts.stream().map(MountainHut::getMunicipality).distinct()
				.collect(groupingBy(Municipality::getProvince, counting()));
		return res;
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		Map<String, Map<String,Long>> res = mountainHuts.stream().map(MountainHut::getMunicipality)
				.collect(groupingBy(Municipality::getProvince, groupingBy(Municipality::getName, counting())));
		return res;
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		Map <String, Long> res = mountainHuts.stream().collect(groupingBy(
				MountainHut::getAltitudeRange, counting()));
		altitudes.stream().forEach(r->res.putIfAbsent(r, 0L));
		return res;
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		Map<String, Integer> res = mountainHuts.stream().collect(groupingBy(
				h -> h.getMunicipality().getProvince(), Collectors.summingInt(MountainHut::getBedsNumber)));
		return res;
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		Map<String, Optional<Integer>> res = mountainHuts.stream().collect(groupingBy(
				MountainHut::getAltitudeRange,
				Collectors.mapping(MountainHut::getBedsNumber, Collectors.maxBy(Comparator.naturalOrder()))));
		altitudes.stream().forEach(r-> {
			Optional<Integer> n = Optional.of(0);
			res.putIfAbsent(r, n);
		});
		return res;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Map<Long, List<String>> res = mountainHuts.stream().map(x -> x.getMunicipality().getName())
				.collect(groupingBy(x -> x, TreeMap::new, counting())).entrySet().stream()
				.collect(groupingBy(Map.Entry::getValue, mapping(Map.Entry::getKey, toList())));
		return res;
	}

}
