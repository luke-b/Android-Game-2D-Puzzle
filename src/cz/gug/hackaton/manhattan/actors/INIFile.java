package cz.gug.hackaton.manhattan.actors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Vector;

import cz.gug.hackathon.manhattan.R;

import android.content.Context;

public class INIFile {
	
	public final static String LEVEL_FILE = "levels.txt";
	
	Context context;
	
	String lines[];
	
	public INIFile(Context context,String levelFile) {
		this.context = context;
	}

	public void saveFile() {
		try {
		
		      OutputStream outstream = context.openFileOutput(LEVEL_FILE,Context.MODE_WORLD_WRITEABLE);
		      OutputStreamWriter outputwriter = new OutputStreamWriter(outstream);
		      BufferedWriter writer = new BufferedWriter(outputwriter);
		    
		      for (int i = 0; i < lines.length; i++) {
		    	  writer.write(lines[i] + "\n");
		      }
		    
		      writer.flush();
		      writer.close();
		      
		      outstream.close();
		      
		  } catch (java.io.FileNotFoundException e) {
			  System.out.println("cannot write level file");
		  } catch (IOException e) {
			  System.out.println("cannot write file. IO error");
		  }
	}
	
	public String[] getData() {
		return lines;
	}
	
	public void loadFileInternal(Context context, int levelFileId)  {
		
		Vector data = new Vector();
		try {
		      //InputStream instream = context.openFileInput(LEVEL_FILE);
		      
			  InputStream instream = context.getResources().openRawResource(levelFileId);
		      
		      InputStreamReader inputreader = new InputStreamReader(instream);
		      BufferedReader buffreader = new BufferedReader(inputreader);
		      String line;
		 
		      while (( line = buffreader.readLine()) != null) {
		    	  data.add(line);
		      }
		    
		      instream.close();
		  } catch (java.io.FileNotFoundException e) {
			  System.out.println("cannot read level file");
		  } catch (IOException e) {
			  System.out.println("cannot read level file. IO error");
		  }
		
		if (data.size() != 0) {
			
			lines = new String[data.size()];
			
			Iterator it = data.iterator();
			
			int cnt = 0;
			while (it.hasNext()) {
				String line = (String)it.next();
				lines[cnt] = line;
				cnt++;
			}
		}
		
	}
	
	
	public void loadFileExternal(Context context, String levelFile)  {
		
		Vector data = new Vector();
		try {
		      InputStream instream = context.openFileInput(LEVEL_FILE);
		      InputStreamReader inputreader = new InputStreamReader(instream);
		      BufferedReader buffreader = new BufferedReader(inputreader);
		      String line;
		 
		      while (( line = buffreader.readLine()) != null) {
		    	  data.add(line);
		      }
		    
		      instream.close();
		  } catch (java.io.FileNotFoundException e) {
			  System.out.println("cannot read level file");
		  } catch (IOException e) {
			  System.out.println("cannot read level file. IO error");
		  }
		
		if (data.size() != 0) {
			
			System.out.println("line count = " + data.size() );
			lines = new String[data.size()];
			
			Iterator it = data.iterator();
			
			int cnt = 0;
			while (it.hasNext()) {
				String line = (String)it.next();
				lines[cnt] = line;
				cnt++;
			}
		}
		
	}

	public boolean getKeyBoolean(String key) {
		
		int index = getKeyIndex(key);
		
		if (index != -1) {
			
			String value = getValue(index);
			
			if (value != null) {
				
				if (value.equals("true")) {
					return true;
				}
			}
		}
		
		return false;
	}



	public String getKeyString(String key) {
		System.out.println("getKeyString = '" + key + "'");
		int index = getKeyIndex(key);
		return getValue(index);
		
	}

	public int getKeyInt(String key) {
	
		int index = getKeyIndex(key);
		return Integer.parseInt(getValue(index));
	}

	public void setString(String key, String value) {
	
		int index = getKeyIndex(key);
		
		if (index != -1) {
			
			if (lines != null) {
		
				lines[index] = key + "=" + value;
				
			}
			
		}
		
	}

	
	private int getKeyIndex(String key) {
		
		
		if (lines != null) {
			for (int i = 0; i < lines.length; i++) {
				
				String t = lines[i].trim();
				
				if (!t.startsWith("#")) {
		
					if (t.startsWith(key)) {
						return i;
					}
					
				}
			}
		}
		
		return -1;
	}
	

	private String getValue(int index) {
		if (lines != null) {
			
			String parts[] = lines[index].split("=");
			
			return parts[1].trim();
			
		}
		return null;
	}
	
}
