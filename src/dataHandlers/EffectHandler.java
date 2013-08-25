package dataHandlers;

import tads.listas.*;
import tads.colas.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;

public class EffectHandler {
	private Lista<Effect> listaEfectos; // seguramente cree un TAD para esto

	public EffectHandler() {
		listaEfectos = null;
	}

	public void readFile(String path) throws IOException {
		// un lector que de TXT que reconozca dónde empieza el efecto de una
		// carta, y vaya constuyendo la lista
		FileReader catcher = new FileReader(path);
		BufferedReader reader = new BufferedReader(catcher);
		String temporaryStore = "not null";
		boolean effectLine = false;
		while (temporaryStore != null) {
			temporaryStore = reader.readLine();
			if (temporaryStore == "#") {
				effectLine = true;
			} else if (effectLine) {
				while (temporaryStore != "//") {
					int lines;
					Simple<String> tmp = new Simple<String>();
					tmp.insertar(temporaryStore);
				}
			}
		}
	}

	// TODO

	public void playCard() {
	};

	public void activateCard() {
	};
}
