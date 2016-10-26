package view;

import java.io.File;

public class findRun implements Runnable{
	
	private File file;
	private String name;
	
	public findRun(String file){
		this.name=file;
	}
	
	public boolean tryClassPath(String filename){
		String classpath = System.getProperty("java.class.path");
		String[] paths = classpath.split(classpath, File.pathSeparatorChar);
		file = searchDirectories(paths, filename);
		return (file != null);
	}

	private File searchDirectories(String[] paths, String filename) {
		SecurityException ex = null;
		for(int i = 0; i < paths.length; i++){
			try{
				File temp = new File(paths[i], filename);
				if(temp.exists()& temp.isFile()){
					return temp;
				}
			}catch(SecurityException e){
				return null;
			}
		}
		return null;
	}

	@Override
	public void run() {
		while(file == null){
			tryClassPath(name);
		}
	}

}
