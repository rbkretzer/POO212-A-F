package moduloAF;

import java.awt.Image;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import javax.imageio.ImageIO;

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

	private int wins;

	private Env environment;

	private Mediator mediator;
	
	public POOmon(String name, String history, Env env) {
		setName(name);
		setHistory(history);
		setImage(image);
		setEnvironment(env);
		setEnergy(500);
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
        try {
			FileWriter fw = new FileWriter("logs/"+ this.name +"/"+ this.name + "Log.txt");
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("Derrota");
            writer.close();
        } catch (IOException ieo) {
            ieo.printStackTrace();
        }
	}
	
	@Override
	public void winner() {
		addWin();
        try {
			FileWriter fw = new FileWriter("logs/"+ this.name +"/"+ this.name + "Log.txt");
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("Vitória");
            writer.close();
        } catch (IOException ieo) {
            ieo.printStackTrace();
        }
	}

	@Override
	public void attack(POOmonBehavior arg0, Env arg1) {
		int damage = 0;
		int damageboost = 0;
		String typeAttackText = "";
		Random randomint = new Random();
		int typeAttack = randomint.nextInt(3 - 1 + 1) + 1;
		int energyspent = 0;
		if (typeAttack == 3) {
			energyspent = randomint.nextInt(200 - 100 + 1) + 100;
			damage = Math.round(energyspent * 1.50f);
			typeAttackText = "Cruel";
		} else

		if (typeAttack == 2) {
			energyspent = randomint.nextInt(99 - 40 + 1) + 40;
			damage = Math.round(energyspent);
			typeAttackText = "Agressivo";
		} else {
			damage = 30;
			typeAttackText = "Básico";
		}
		if (typeAttack == 3 || typeAttack == 2) {
			this.energy -= damage;
		}
		if (arg1.equals(this.environment)) {
			damageboost = Math.round(damage * 0.20f);
		}
		arg0.takeDamage(damage + damageboost, arg1);

		try {
			FileWriter fw = new FileWriter("logs/"+ this.name +"/"+ this.name + "Log.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Ataque Efetuado: " + typeAttackText + " " + damage + " (" + (damage + damageboost) + ") - "
					+ arg1.toString() + "(-" + energyspent + ")");
			writer.close();
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
		if(!arg0.isAlive()) {
			winner();
			restoreEnergy(50);
			restoreEnergy(500);
		} else {			
			restoreEnergy(50);
		}
		setImage();
	}

	private void setImage() {
		try {
			Image image;
            if (!isAlive()) {
                image = ImageIO.read(getClass().getResource("resources/"+this.name+"Morto.png"));
            } else if (getEnergy() > 350) {
            	image = ImageIO.read(getClass().getResource("resources/"+this.name+"Saudavel.png"));
            } else if (getEnergy() > 151) {
                image = ImageIO.read(getClass().getResource("resources/"+this.name+"Cansado.png"));
            } else {
            	image = ImageIO.read(getClass().getResource("resources/"+this.name+"Exausto.png"));
            }
            this.image = image;
        } catch (IOException io) {
            io.printStackTrace();
        }
	}

	@Override
	public void restoreEnergy(int arg0) {
		setEnergy(this.energy + arg0);
	}

	@Override
	public void setMediator(Mediator arg0) {
		this.mediator = arg0;
	}

	@Override
	public void takeDamage(int arg0, Env arg1) {
		int damage = arg0;
		if (arg1.equals(this.environment)) {
			damage = Math.round(arg0 * 0.90f);
		}
		this.energy -= damage;

		try {
			FileWriter fw = new FileWriter("logs/"+ this.name +"/"+ this.name + "Log.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			writer.write("Ataque Recebido: " + arg0 + " - " + arg1.toString() + "(-" + damage + ")");
			writer.close();
		} catch (IOException ieo) {
			ieo.printStackTrace();
		}
		setImage();
		if(!this.isAlive()) {
			loser();
		}
	}


}
