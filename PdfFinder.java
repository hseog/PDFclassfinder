import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PdfFinder {
    public static void main(String[] args) {
	ArrayList<String> classes = new ArrayList<String>();
	try {
	    URL my_url = new URL("http://registrar.princeton.edu/course-offerings/search_results.xml?term=1152&coursetitle=&instructor=&distr_area=&level=&cat_number=&sort=SYN_PS_PU_ROXEN_SOC_VW.SUBJECT%2C+SYN_PS_PU_ROXEN_SOC_VW.CATALOG_NBR%2CSYN_PS_PU_ROXEN_SOC_VW.CLASS_SECTION%2CSYN_PS_PU_ROXEN_SOC_VW.CLASS_MTG_NBR&submit=Search");
	    BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
	    String strTemp = "";
	    while (null != (strTemp = br.readLine())) {
		if (strTemp.contains("<a href=\"course_details.xml?courseid=")) {
		    String replaceSection = strTemp.replace("<td nowrap=\"\"><a href=\"","http://registrar.princeton.edu/course-offerings/");
		    String replaceSection2 = replaceSection.replace("&#38;term=1144\"><u>","&term=1144");
		    classes.add(replaceSection2);
		    System.out.println(replaceSection2);
		}
	    }

	    br.close();

	    for (String s: classes) {
		URL url = new URL(s);
		BufferedReader b = new BufferedReader(new InputStreamReader(url.openStream()));
		String temp;
		boolean same = false;
		String name = "";
		while(null != (temp = b.readLine())) {
		    if (same) {
			if (name.equals(temp)) break;
			name = temp;
			same = false;
		    } else if(temp.contains("</strong><strong>")) same = true;
		    if (temp.contains("P/D/F Only")) {
			System.out.println(name);
			break;
		    }
		}	   
		b.close();
	    }

	} catch (Exception ex) {
	    ex.printStackTrace();	   
	}	
    }
}
