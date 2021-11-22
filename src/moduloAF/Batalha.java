package moduloAF;

import java.util.HashMap;

import moduloAGame.Env;

public class Batalha {
	private HashMap<String, POOmon> pooMons = new HashMap<>();
	
	private POOmon winner;
	
	private Env environment;
	
	private int actualGame;
	
	public void setEnvironment(Env env) {
		this.environment = env;
	}
	
	private void sort() {
		//TODO verificar como pode ser feita o sorteio dos poomons dessa batalha e o chaveamento das batalhas
	}
	
	private void startGame() {
		
	}
}
