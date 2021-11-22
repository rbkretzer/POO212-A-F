package moduloAF;

import java.time.LocalDateTime;

import javax.imageio.ImageIO;

import moduloAGame.Env;
import moduloAGame.Mediator;
import moduloAGame.POOmonBehavior;

public class POOmon implements POOmonBehavior {
	
	private String name;
	
	private int activations;
	
	private int energy;
	
	private String history;
	
	private ImageIO image;
	
	private int highestEnergy;
	
	private LocalDateTime highestEnergyTime;
	
	private boolean lostGame;
	
	private int wins;
	
	private Env environment;
	
	private Mediator mediator;
	
	private int game;
	
	public POOmon(String name, String history, ImageIO image, Env env) {
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

	public void setImage(ImageIO image) {
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
	public ImageIO getImage() {
		return this.image;
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
	
	public boolean getLostGame() {
		return this.lostGame;
	}
	
	public int getGame() {
		return this.game;
	}
	
	@Override
	public void attack(POOmonBehavior arg0, Env arg1) {
		// TODO tirar dúvida com o professor
		int damage = 0;
		if (arg1.equals(this.environment)) {
			damage = Math.round(damage * 1.20f);
		}
		
	}
	
	@Override
	public void restoreEnergy(int arg0) {
		setEnergy(arg0);
	}

	@Override
	public void setMediator(Mediator arg0) {
		// TODO tirar dúvida com o professor
		this.mediator = arg0;
	}

	@Override
	public void takeDamage(int arg0, Env arg1) {
		if(arg1.equals(this.environment)) {
			this.energy -= Math.round(arg0 * 0.90f);
		}
	}

}
