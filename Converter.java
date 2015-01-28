import java.util.HashMap;

public class Converter {

	HashMap<String, String> indianKey;

	public Converter() {
		indianKey = new HashMap<String, String>();
		fillIndianKey();
	}

	public void fillIndianKey() {
		indianKey.put("s", "c");
		indianKey.put("r1", "c#");
		indianKey.put("r2", "d");
		indianKey.put("g1", "d");
		indianKey.put("g2", "d#");
		indianKey.put("r3", "d#");
		indianKey.put("g3", "e");
		indianKey.put("m1", "f");
		indianKey.put("m2", "f#");
		indianKey.put("p", "g");
		indianKey.put("d1", "g#");
		indianKey.put("d2", "a");
		indianKey.put("n1", "a");
		indianKey.put("n2", "a#");
		indianKey.put("d3", "a#");
		indianKey.put("n3", "b");
		indianKey.put("S", "C");
		indianKey.put("R1", "C#");
		indianKey.put("R2", "D");
		indianKey.put("G1", "D");
		indianKey.put("G2", "D#");
		indianKey.put("R3", "D#");
		indianKey.put("G3", "E");
		indianKey.put("M1", "F");
		indianKey.put("M2", "F#");
		indianKey.put("P", "G");
		indianKey.put("D1", "G#");
		indianKey.put("D2", "A");
		indianKey.put("N1", "A");
		indianKey.put("N2", "A#");
		indianKey.put("D3", "A#");
		indianKey.put("N3", "B");
	}

	public String toWestern(String indianNotes, String scale) {
		String givenNotes = "srgmpdnSRGMPDN";
		String givenNumbers = "123";

		String[] scaleArray = scale.split(" ");
		String westernString = "";
		String indianNote = "";
		int index = 0;

		while (index < indianNotes.length()) {
			indianNote = "";
			if (!givenNotes.contains(indianNotes.substring(index, index + 1))
					&& !givenNumbers.contains(indianNotes.substring(index,
							index + 1)))
				westernString += indianNotes.substring(index, index + 1);
			else {
				if (index == indianNotes.length() - 1)
					indianNote = indianNotes.substring(index, index + 1);
				else {
					indianNote = indianNotes.substring(index, index + 1);
					if (givenNumbers.contains(indianNotes.substring(index + 1,
							index + 2))) {
						indianNote += indianNotes.substring(index + 1,
								index + 2);
						index++;
					}
				}
				for (int j = 0; j < scaleArray.length; j++)
					if (scaleArray[j].contains(indianNote.toLowerCase()))
						indianNote = scaleArray[j];
				westernString += indianKey.get(indianNote);
			}
			index++;
		}
		return westernString;
	}
}
