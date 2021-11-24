package moduloAF;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import moduloAGame.Env;
import moduloAGame.Mediator;
import moduloAGame.POOmonBehavior;

public class POOmon implements POOmonBehavior {

	private String name;

	private int activations;

	private int energy;

	private String history;

	private Image image;

	private int highestEnergy;

	private LocalDateTime highestEnergyTime;

	private boolean lostGame;

	private int wins;

	private Env environment;

	private MediatorClass mediator;

	private int game;
	
	public POOmon(String name, String history, Env env) {
		setName(name);
		setHistory(history);
		setImage(image);
		setEnvironment(env);
		setEnergy(500);
	}
	
	public void setGame(int game) {
		this.game = game;
	};

	public void serLostGame(boolean lostGame) {
		this.lostGame = lostGame;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActivations(int activations) {
		this.activations = activations;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setHighestEnergy(int highestEnergy) {
		this.highestEnergy = highestEnergy;
	}

	public void setHighestEnergyTime(LocalDateTime highestEnergyTime) {
		this.highestEnergyTime = highestEnergyTime;
	}

	public void setEnvironment(Env env) {
		this.environment = env;
	}

	public void addWin() {
		this.wins += 1;
	}
	
	public MediatorClass getMediato() {
		return this.mediator;
	}

	@Override
	public int getActivations() {
		return this.activations;
	}

	@Override
	public int getEnergy() {
		return this.energy;
	}

	@Override
	public Env getEnvironment() {
		return this.environment;
	}

	@Override
	public int getHighestEnergy() {
		return this.highestEnergy;
	}

	@Override
	public LocalDateTime getHighestEnergyTime() {
		return this.highestEnergyTime;
	}

	@Override
	public String getHistory() {
		return this.history;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getWins() {
		return this.wins;
	}

	@Override
	public boolean isAlive() {
		if (this.energy > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Image getImage() {
		return this.image;
	}
	
	@Override
	public void loser() {
		
	}
	
	@Override
	public void winner() {
		// TODO Auto-generated method stub
		
	}

	public boolean getLostGame() {
		return this.lostGame;
	}

	public int getGame() {
		return this.game;
	}

	@Override
	public void attack(POOmonBehavior arg0, Env arg1) {
		int damage = 0;
		int damageboost = 0;
		String typeattacktext = "";
		Random randomint = new Random();
		int typeAttack = randomint.nextInt(3 - 1 + 1) + 1;
		int energyspent = 0;
		if (typeAttack == 3) {
			energyspent = randomint.nextInt(200 - 100 + 1) + 100;
			damage = Math.round(energyspent * 1.50f);
			typeattacktext = "Cruel";
		} else

		if (typeAttack == 2) {
			energyspent = randomint.nextInt(99 - 40 + 1) + 40;
			damage = Math.round(energyspent);
			typeattacktext = "Agressivo";
		} else {
			damage = 30;
			typeattacktext = "Básico";
		}
		if (typeAttack == 3 || typeAttack == 2) {
			this.energy -= damage;
		}
		if (arg1.equals(this.environment)) {
			damageboost = Math.round(damage * 0.20f);
		}
		arg0.takeDamage(damage + damageboost, arg1);

		try {
			FileWriter fw = new FileWriter("EntregaDia17112021/"+this.name + "logPOOmon.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Ataque Efetuado: " + typeattacktext + " " + damage + "(" + (damage + damageboost) + ") - "
					+ arg1.toString() + "(-" + energyspent + ")");
			writer.close();
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
		changeImage();
	}

	private void changeImage() {
		if (!isAlive()) {
			
		}
	}

	@Override
	public void restoreEnergy(int arg0) {
		setEnergy(arg0);
	}

	@Override
	public void setMediator(Mediator arg0) {
		this.mediator = (MediatorClass) arg0;
	}

	@Override
	public void takeDamage(int arg0, Env arg1) {
		int damage = arg0;
		if (arg1.equals(this.environment)) {
			damage = Math.round(arg0 * 0.90f);
		}
		this.energy -= damage;

		try {
			FileWriter fw = new FileWriter(this.name + "Log.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Ataque Recebido: " + arg0 + " - " + arg1.toString() + "(-" + damage + ")");
			writer.close();
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
		changeImage();
	}


}
