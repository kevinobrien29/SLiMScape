package vis.tools;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import vis.opencsv.CSVReader;


public class BinarySearchParser {
	public static final int NOT_FOUND = -1;

	// Test program
	public static void main(String[] args) {
		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(
					"C:/Users/Kevin/Desktop/other/HumSF09_MainData/input.csv"));
			List<String[]> all = csvReader.readAll();

			BinarySearchParser bs = new BinarySearchParser();
			ArrayList<String[]> run = bs.parseInteractionDataset(all, 0,
					"Q6PJT7");

			int i = 0;
			for (String[] line : run) {
				i++;
				System.out.println(line[0] + line[1] + "  " + i);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Performs the standard binary search using two comparisons per level.
	 * 
	 * @return index where item is found, or NOT_FOUND.
	 */
	public int binarySearch(List<String[]> a, String x, int col) {
		int low = 0;
		int high = a.size() - 1;
		int mid;

		while (low <= high) {
			mid = (low + high) / 2;

			if (a.get(mid)[col].compareTo(x) < 0)
				low = mid + 1;
			else if (a.get(mid)[col].compareTo(x) > 0)
				high = mid - 1;
			else
				return mid;
		}

		return NOT_FOUND; // NOT_FOUND = -1
	}

	public ArrayList<String[]> getInteractors(List<String[]> all, int A,
			int middle, String root) {
		ArrayList<String[]> few = new ArrayList<String[]>();
		few.add(all.get(middle));
		int up = middle - 1;
		while (all.get(up)[A].equals(root)) {
			few.add(all.get(up));
			up--;
		}
		int low = middle + 1;
		while (all.get(low)[A].equals(root)) {
			few.add(all.get(low));
			low++;
		}
		return few;
	}
	
	public ArrayList<String[]> parseInfo(List<String[]> all,
			int idCol, String root) {
		int mm = binarySearch(all, root, idCol);
		ArrayList<String[]> result = new ArrayList<String[]>();
		if (mm != -1) {
			result = getInteractors(all, idCol, mm, root);
		}

		return result;
	}
	
	public ArrayList<String[]> parseInteractionDataset(List<String[]> all,
			int A, String root) {
		int mm = binarySearch(all, root, A);
		ArrayList<String[]> result = new ArrayList<String[]>();
		if (mm != -1) {
			result = getInteractors(all, A, mm, root);
		}

		return result;
	}

	public ArrayList<String[]> parseSlimDataset(List<String[]> all,
			int A, String root) {
		int mm = binarySearch(all, root, A);
		ArrayList<String[]> result = new ArrayList<String[]>();
		if (mm != -1) {
			result = getInteractors(all, A, mm, root);
		}

		return result;
	}
}